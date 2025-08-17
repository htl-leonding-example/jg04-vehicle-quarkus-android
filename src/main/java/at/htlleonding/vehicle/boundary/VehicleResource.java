package at.htlleonding.vehicle.boundary;

import at.htlleonding.vehicle.control.ImageRepository;
import at.htlleonding.vehicle.control.InitBean;
import at.htlleonding.vehicle.control.VehicleRepository;
import at.htlleonding.vehicle.entity.Vehicle;
import at.htlleonding.vehicle.entity.VehicleMapper;
import at.htlleonding.vehicle.entity.dto.VehicleDto;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
public class VehicleResource {

    @Inject
    VehicleRepository vehicleRepository;

    @Inject
    ImageRepository imageRepository;

    @Inject
    VehicleMapper vehicleMapper;

    @Inject
    InitBean initBean;

    @GET
    public Response findAll() {
        return Response.ok(
                vehicleRepository.listAll(Sort.by("brand", "model"))
        ).build();
    }

    @Path("img")
    @GET
    public Response findAllWithPics() {
        List<Vehicle> vehicles = vehicleRepository.listAll(Sort.by("brand", "model"));
        List<VehicleDto> dtos = vehicles
                .stream()
                .map(v -> vehicleMapper.toResource(v))
                .toList();




        return Response.ok(dtos).build();
    }

    @Transactional
    @POST
    public Response save(@Context UriInfo uriInfo, Vehicle vehicle) {
        Log.info("save -> " + vehicle);

        // is the car existing?
        if (vehicle.getId() != null) {
            // checking, if a car with the given id exists
            var existingVehicle = vehicleRepository.findById(vehicle.getId());
            if (existingVehicle != null) {
                Log.info("save: vehicle is null");
                return Response
                        .notModified()
                        .header("reason", "this id already exists - use PATCH for updating")
                        .build();
            }
        }


//        //throw new WebApplicationException("Car with this id is not existing", Response.Status.CONFLICT);
//        Log.info("save: car with this id is not existing");
//        return Response
//                .notModified()
//                .header("reason", "car with this id is not existing")
//                .build();



        var v = vehicleRepository.getEntityManager().merge(vehicle);
        Log.info(v.toString());
        //vehicleRepository.persist(vehicle);
        //Log.info(vehicle.getId());
        UriBuilder uriBuilder = uriInfo
                .getAbsolutePathBuilder()
                .path(v.getId().toString());
        return Response.created(uriBuilder.build()).build();


    }

    @Path("{id}")
    @GET
    public Response findById(@PathParam("id") long id) {
        var v = vehicleRepository.findById(id);
        if (v == null) {
            return Response
                    .noContent()
                    .header("reason", "car with this id is not existing")
                    .build();
        } else {
            return Response.ok(v).build();
        }
    }

    @Transactional
    @PATCH
    public Response update(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            return Response
                    .notModified()
                    .header("reason", "id is missing")
                    .build();
        }

        var vehicleInDb = vehicleRepository.findById(vehicle.getId());
        if (vehicleInDb == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .header("reason", "car with this id is not existing")
                    .build();
        } else {

            // avoid updating of nullified fields

            if (vehicle.getBrand() == null) {
                vehicle.setBrand(vehicleInDb.getBrand());
            }

            if (vehicle.getModel() == null) {
                vehicle.setModel(vehicleInDb.getModel());
            }

            if (vehicle.getYear() == 0) {
                vehicle.setYear(vehicleInDb.getYear());
            }

            var v = vehicleRepository.getEntityManager().merge(vehicle);
            return Response.ok(v).build();
        }
    }

    @Transactional
    @PUT
    public Response updateObject(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            return Response
                    .notModified()
                    .header("reason", "id is missing")
                    .build();
        }

        var vehicleInDb = vehicleRepository.findById(vehicle.getId());
        if (vehicleInDb == null) {
            return Response
                    .noContent()
                    .header("reason", "car with this id is not existing")
                    .build();
        } else {

            var v = vehicleRepository.getEntityManager().merge(vehicle);
            return Response.ok(v).build();
        }
    }

    @Transactional
    @Path("{id}")
    @DELETE
    public Response delete(@PathParam("id") long id, @Context HttpHeaders httpHeaders) {
        Log.info("Content-Type: " + httpHeaders.getHeaderString("Content-Type"));

        if (id == 0) {
            return Response
                    .notModified()
                    .header("reason", "id is missing")
                    .build();
        }

        var vehicleInDb = vehicleRepository.findById(id);
        if (vehicleInDb == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .header("reason", "car with this id is not existing")
                    .build();
        } else {
            imageRepository.delete("vehicle.id", vehicleInDb.getId());
            vehicleRepository.delete(vehicleInDb);
            return Response.accepted().build();
        }

    }

    @Transactional
    @GET
    @Path("initdata")
    @Produces(MediaType.TEXT_PLAIN)
    public Response initData() {
        vehicleRepository.initializeDatabase();
        return Response.ok("demo-data restored").build();
    }

    /**
     * Upload an image for a vehicle.
     */
//    @POST
//    @Path("{id}/image")
//    public Response upload(@PathParam("id") Long id,
//                           @FormParam("file") FileUpload upload) {
//        if (upload == null) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Datei fehlt").build();
//        }
//        try (var in = upload.openStream()) {
//            String stored = vehicleRepository.uploadImage(id, upload.fileName(), in);
//            return Response.ok().entity("{\"imagePath\":\"" + stored + "\"}").build();
//        } catch (IOException e) {
//            return Response.serverError().entity("Upload fehlgeschlagen").build();
//        }
//    }

}
