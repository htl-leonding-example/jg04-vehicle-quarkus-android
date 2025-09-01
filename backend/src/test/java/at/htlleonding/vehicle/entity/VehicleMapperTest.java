package at.htlleonding.vehicle.entity;

import at.htlleonding.vehicle.control.VehicleRepository;
import at.htlleonding.vehicle.entity.dto.VehicleDto;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VehicleMapper.
 * https://learn.microsoft.com/en-us/dotnet/core/testing/unit-testing-best-practices#follow-test-naming-standards
 */
@QuarkusTest
class VehicleMapperTest {

    @Inject
    VehicleMapper mapper;

    @Inject
    VehicleRepository vehicleRepository;

    @Test
    void toResource_simpleVehicle_vehicleInJsonFormat() {
        // Arrange
        QuarkusTransaction.begin();
        vehicleRepository.initializeDatabase();
        QuarkusTransaction.commit();
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Opel");
        vehicle.setModel("GT");
        vehicle.setYear(1970);

        // Act
        VehicleDto dto = mapper.toResource(vehicle);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.brand()).isEqualTo("Opel");
        assertThat(dto.model()).isEqualTo("GT");
        assertThat(dto.year()).isEqualTo(1970);
        assertThat(dto.imageFileNames().get(0)).isEqualTo("opel-gt-1970.jpeg");
        assertThat(dto.imageData()).isNullOrEmpty();
    }

    @Test
    void fromResource_vehicleInJsonFormat_vehicleAndImageObjects() {

    }
}