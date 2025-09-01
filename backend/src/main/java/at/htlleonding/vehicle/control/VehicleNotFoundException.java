package at.htlleonding.vehicle.control;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Vehicle nicht gefunden: " + id);
    }
}
