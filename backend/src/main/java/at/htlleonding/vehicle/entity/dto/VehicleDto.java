package at.htlleonding.vehicle.entity.dto;

import at.htlleonding.vehicle.entity.Image;

import java.util.List;

public record VehicleDto(
        String brand,
        String model,
        int year,
        List<String> imageFileNames,
        List<byte[]> imageData
) { }
