package at.htlleonding.vehicle.entity;

import at.htlleonding.vehicle.control.ImageRepository;
import at.htlleonding.vehicle.control.VehicleRepository;
import at.htlleonding.vehicle.entity.dto.VehicleDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class VehicleMapper implements Mapper<Vehicle, VehicleDto> {

    @Inject
    ImageRepository imageRepository;

    @Inject
    VehicleRepository vehicleRepository;

    public VehicleDto toResource(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            throw new RuntimeException("vehicle id is missing when converting to VehicleDto");
        }

        Vehicle v = vehicleRepository.findById(vehicle.getId());
        List<Image> images = imageRepository.list("vehicle", v);
        List<String> fileNames = images
                .stream()
                .map(it -> it.getFileName())
                .toList();
        return new VehicleDto(
                v.getBrand(),
                v.getModel(),
                v.getYear(),
                fileNames
        );
    }

    public Vehicle fromResource(VehicleDto vehicleDto) {
        // TODO: implement Dto fromResource
        return null;
    }

}
