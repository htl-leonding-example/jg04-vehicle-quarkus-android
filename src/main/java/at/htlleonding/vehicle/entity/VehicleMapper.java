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

        List<Image> images = null;
        List<String> fileNames = null;
        List<byte[]> imageData = null;

        Vehicle v = vehicleRepository.findByBrandAndModel(vehicle.getBrand(), vehicle.getModel());
        if (v != null) {
            images = imageRepository.list("vehicle", v);
            fileNames = images
                    .stream()
                    .map(it -> it.getFileName())
                    .toList();
            imageData = images
                    .stream()
                    .filter(it -> it.getImageData() != null)
                    .map(it -> it.getImageData())
                    .toList();
        }
        return new VehicleDto(
                v.getBrand(),
                v.getModel(),
                v.getYear(),
                fileNames,
                imageData
        );
    }

    public Vehicle fromResource(VehicleDto vehicleDto) {
        // TODO: implement Dto fromResource
        return null;
    }

}
