package at.htlleonding.vehicle.entity.dto;

public record ImageDto(
        String fileName,
        byte[] imageData,
        int sizeInBytes
) {}
