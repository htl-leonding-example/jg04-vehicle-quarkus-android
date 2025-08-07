package at.htlleonding.vehicle.entity;

public interface Mapper<E, T> {
    T toResource(E entity);
    E fromResource(T dto);
}

