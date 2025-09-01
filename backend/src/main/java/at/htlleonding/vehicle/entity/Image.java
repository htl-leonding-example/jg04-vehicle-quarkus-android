package at.htlleonding.vehicle.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "VE_IMAGE")
@SequenceGenerator(name = "imageSeq", sequenceName = "VE_IMAGE_SEQ", allocationSize = 1,initialValue = 1000)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageSeq")
    @Column(name = "I_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "I_V_ID")
    private Vehicle vehicle;

    @Column(name = "I_FILE_NAME")
    private String fileName;

    @Lob
    @Column(name = "I_IMAGE_DATA", columnDefinition = "BLOB")
    private byte[] imageData;

    @Column(name = "I_SIZE_BYTES")
    private int sizeInBytes;

    public Image(Vehicle vehicle, String fileName) {
        this.vehicle = vehicle;
        this.fileName = fileName;
    }

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(int sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }
}
