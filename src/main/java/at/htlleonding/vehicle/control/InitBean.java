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

    void init(@Observes StartupEvent ev) {
        insertData();
    }

    @Transactional
    public void insertData() {
        Vehicle v = new Vehicle("Alfa Romeo", "Berlina 2000", 1971);
        // vehicleRepository.persist(v);
        Image i = new Image(v, "alfa-romeo-2000-berlina-1971.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Buick", "Century Riviera", 1955);
        // vehicleRepository.persist(v);
        i = new Image(v, "buick-century-riviera-1955.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Buick", "GSX", 1970);
        // vehicleRepository.persist(v);
        i = new Image(v, "buick-gsx-1970.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Chevrolet", "Impala", 1960);
        // vehicleRepository.persist(v);
        i = new Image(v, "chevrolet-impala-1960.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Chevrolet", "Nova", 1967);
        // vehicleRepository.persist(v);
        i = new Image(v, "chevrolet-nova-1967.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Fiat", "Multipla", 1962);
        // vehicleRepository.persist(v);
        i = new Image(v, "fiat-multipla-1962.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Ford", "Escort Ghia 1.6", 1982);
        // vehicleRepository.persist(v);
        i = new Image(v, "ford-escort-ghia-1.6-1982.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Ford", "Mustang Fastback", 1968);
        // vehicleRepository.persist(v);
        i = new Image(v, "ford-mustang-fastback-1968.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Ford", "Torino", 1969);
        // vehicleRepository.persist(v);
        i = new Image(v, "ford-torino-1969.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("GMC", "100 pickup", 1954);
        // vehicleRepository.persist(v);
        i = new Image(v, "gmc-100-pickup-1954.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Opel", "1200", 1960);
        // vehicleRepository.persist(v);
        i = new Image(v, "opel-1200-1960.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Opel", "Kadett B", 1970);
        // vehicleRepository.persist(v);
        i = new Image(v, "opel-kadett-b-1970.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Opel", "Rekord 1700 P1", 1957);
        // vehicleRepository.persist(v);
        i = new Image(v, "opel-rekord-1700p1-1957.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Opel", "GT", 1970);
        // vehicleRepository.persist(v);
        i = new Image(v, "opel-gt-1970.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Opel", "Kapitän", 1956);
        // vehicleRepository.persist(v);
        i = new Image(v, "opel-kapitaen-1956.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Pontiac", "GTO", 1967);
        // vehicleRepository.persist(v);
        i = new Image(v, "pontiac-gto-1967.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Opel", "Rekord C", 1970);
        // vehicleRepository.persist(v);
        i = new Image(v, "opel-rekord-c-1970.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Pontiac", "Streamliner", 1949);
        // vehicleRepository.persist(v);
        i = new Image(v, "pontiac-streamliner-1949.jpeg");
        imageRepository.persist(i);
        
        v = new Vehicle("Renault", "Grand Scenic", 1918);
        // vehicleRepository.persist(v);
        i = new Image(v, "renault-grand-scenic-2018.png");
        imageRepository.persist(i);
        
        v = new Vehicle("Volkswagen", "Käfer", 1975);
        // vehicleRepository.persist(v);
        i = new Image(v, "volkswagen-beetle-1975.jpeg");
        imageRepository.persist(i);
        i = new Image(v, "volkswagen-beetle-1975-2.jpeg");
        imageRepository.persist(i);

    }

}
