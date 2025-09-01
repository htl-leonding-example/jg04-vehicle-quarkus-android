package at.htlleonding.vehicle.control;

import at.htlleonding.vehicle.entity.Vehicle;
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.AssertDbConnectionFactory;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;

@QuarkusTest
class VehicleRepositoryTest {

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    AgroalDataSource ds;

    /**
     * Testet das Hochladen eines Bildes in die Datenbank.
     * Die Datei wird im Verzeichnis imageBasePath erwartet.
     * <p>
     *
     * @throws Exception
     * @Transactional wird nicht verwendet, da die Tabellen erst am Ende der
     * Methode physisch in die Tabelle geschrieben werden (commit). Der Zugriff auf die Datenbank
     * über assertj-db würde daher nicht funktionieren.
     */
    @Test
    void uploadImageIntoDirectory_persistsImageMetadata() throws Exception {

        // Arrange

        QuarkusTransaction.begin();
        vehicleRepository.initializeDatabase();
        QuarkusTransaction.commit();

        String originalName = "opel-rekord-1700p1-1957.jpeg";

        // AssertJ-DB Connection (kein try-with-resources möglich)
        AssertDbConnection conn = AssertDbConnectionFactory.of(ds).create();

        // Vorher-Snapshot
        Log.info("**************************************************************************************");
        Log.info("Before uploading image, vehicle table snapshot:");
        Log.info("**************************************************************************************");
        Table vehicleTableBefore = conn.table("ve_vehicle").build();
        int beforeCountVehicle = vehicleTableBefore.getRowsList().size();
        org.assertj.core.api.Assertions.assertThat(beforeCountVehicle).isEqualTo(20);
        output(vehicleTableBefore).toConsole();
        Table imageTableBefore = conn.table("ve_image").build();
        int beforeCountImage = imageTableBefore.getRowsList().size();
        output(imageTableBefore).toConsole();
        org.assertj.core.api.Assertions.assertThat(beforeCountImage).isEqualTo(21);

        // Act
        // Vehicle anlegen
        Vehicle vehicle = new Vehicle();
        // TODO: Pflichtfelder setzen, z.B. vehicle.setName("Test");
        vehicle.setBrand("Test Opel");
        vehicle.setModel("Test Rekord 1700p1");
        vehicle.setYear(1957);

        QuarkusTransaction.begin();
        vehicleRepository.persist(vehicle);
        QuarkusTransaction.commit();

        Log.info("Vehicle created with ID: " + vehicle.getId());
        Log.info("Vehicle: " + vehicle);

        long imageId;

        QuarkusTransaction.begin();
        imageId = vehicleRepository.uploadImage(vehicle.getId(), originalName);
        QuarkusTransaction.commit();


        // Nachher-Snapshot
        Log.info("**************************************************************************************");
        Log.info("After uploading image, vehicle table snapshot:");
        Log.info("**************************************************************************************");
        Table vehicleTableAfter = conn.table("ve_vehicle").build();
        int afterCountVehicle = vehicleTableAfter.getRowsList().size();
        output(vehicleTableAfter).toConsole();

        Table imageTableAfter = conn.table("ve_image").build();
        int afterCountImage = imageTableAfter.getRowsList().size();
        output(imageTableAfter).toConsole();
        assertThat(imageTableAfter).hasNumberOfRows(beforeCountImage + 1);

        int lastIndex = vehicleTableAfter.getRowsList().size() - 1;

        assertThat(vehicleTableAfter)
                .row(lastIndex)
                .value("v_id").isEqualTo(vehicle.getId())
                .value("v_brand").isEqualTo("Test Opel")
                .value("v_model").isEqualTo("Test Rekord 1700p1")
                .value("v_year").isEqualTo(1957);


        var request = conn
                .request("select * from ve_vehicle order by v_id")
                .build();
        int lastIdxReq = request.getRowsList().size() - 1;
        assertThat(request)
                .row(lastIdxReq)
                .value("v_brand").isEqualTo("Test Opel");


        var requestTestImages = conn
                .request("select * from ve_image where i_size_bytes > 0 order by i_id")
                .build();

        assertThat(requestTestImages)
                .hasNumberOfRows(1)
                .row()
                .value("i_id").isEqualTo(imageId)
                .value("i_size_bytes").isEqualTo(288204)
                .value("i_v_id").isEqualTo(vehicle.getId());
    }

}