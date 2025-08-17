package at.htlleonding.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(
        name = "VE_VEHICLE",
        uniqueConstraints = @UniqueConstraint(name = "UQ_VEHICLE_BRAND_MODEL", columnNames = {"V_BRAND", "V_MODEL"}))
@SequenceGenerator(name = "vehicleSeq", sequenceName = "VE_VEHICLE_SEQ", allocationSize = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicleSeq")
    @Column(name = "V_ID")
    private Long id;

    @Column(name = "V_BRAND")
    private String brand;

    @Column(name = "V_MODEL")
    private String model;

    @Column(name = "V_YEAR")
    private int year;

    public Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return "Vehicle{" +
               "id=" + id +
               ", brand='" + brand + '\'' +
               ", model='" + model + '\'' +
               ", year=" + year +
               '}';
    }
}
