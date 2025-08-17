package at.htlleonding.vehicle.control;

import at.htlleonding.vehicle.entity.Image;
import at.htlleonding.vehicle.entity.Vehicle;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class InitBean {

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    ImageRepository imageRepository;

    @Transactional
    void init(@Observes StartupEvent ev) {
        vehicleRepository.insertDataIntoVehicleAndImage();
    }



}
