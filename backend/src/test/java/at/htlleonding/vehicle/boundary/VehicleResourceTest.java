package at.htlleonding.vehicle.boundary;

import at.htlleonding.vehicle.control.ImageRepository;
import at.htlleonding.vehicle.control.VehicleRepository;
import at.htlleonding.vehicle.entity.Vehicle;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * https://learn.microsoft.com/en-us/dotnet/core/testing/unit-testing-best-practices#follow-test-naming-standards
 *
 * The name of your test should consist of three parts:
 *
 *     Name of the method being tested
 *     Scenario under which the method is being tested
 *     Expected behavior when the scenario is invoked
 *
 */
@QuarkusTest
class VehicleResourceTest {

    final String BASE_URL = "http://localhost:8081";

    @ConfigProperty(name = "media.image.base-path")
    String imageBasePath;

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    ImageRepository imageRepository;

    @Test
    void findAll_vehiclesExistsInDatabase_returnsJsonArraySortedByBrandAndModel() {
        Response response = given()
                .accept(APPLICATION_JSON)
                .get(BASE_URL + "/vehicle");

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().asString()).isNotEmpty();
        List<Map<String, Object>> vehicles = response.jsonPath().getList("$");

        // Extrahiere die Marken und Modelle
        List<String> brandsAndModels = vehicles.stream()
                .map(v -> v.get("brand") + " " + v.get("model"))
                .toList();
        // Überprüfe, ob die Liste sortiert ist
        List<String> sortedBrandsAndModels = brandsAndModels.stream().sorted().toList();
        assertThat(brandsAndModels).isEqualTo(sortedBrandsAndModels);


    }

    /**
     * delete all images and vehicles in the db
     * create a restassure query to get a list of vehicles with images
     * assert that the response is a json array with assertj-core
     */
    @Transactional
    @Test
    void findAllWithPics_imagesWithBinaryData_jsonArray() {
        // Arrange
        purge();
        Vehicle v = new Vehicle("Alfa Romeo", "Berlina 2000", 1971);
        vehicleRepository.persist(v);
        vehicleRepository.uploadImageFromFile(v.getId(), "alfa-romeo-2000-berlina-1971.jpeg");

        // Act
        Response response = given()
                .accept(APPLICATION_JSON)
                .when()
                .get("/vehicles?withImages=true")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert
        List<Map<String, Object>> root = response.jsonPath().getList("$");
        assertThat(root)
                .as("Antwort soll ein JSON-Array sein")
                .isNotNull()
                .isInstanceOf(List.class);

        // Beispiel zusätzliche Assertions (optional):
        // assertThat(root).allSatisfy(entry -> assertThat(entry).containsKeys("id","images"));

    }

    @Transactional
    void purge() {
        imageRepository.deleteAll();
        vehicleRepository.deleteAll();
    }
}