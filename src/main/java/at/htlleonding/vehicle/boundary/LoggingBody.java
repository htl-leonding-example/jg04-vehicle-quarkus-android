package at.htlleonding.vehicle.boundary;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * https://medium.com/@vijayakumarpsg587/fiddling-with-httpresponses-in-java-2a269cd5a474
 * https://solocoding.dev/blog/eng_quarkus_intercept_requests
 * https://access.redhat.com/documentation/en-us/red_hat_fuse/7.3/html/apache_cxf_development_guide/jaxrs20filters
 */
@Provider
public class LoggingBody implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        byte[] requestEntity = context.getEntityStream().readAllBytes();

        if (requestEntity.length == 0) {
            Log.info("BODY IS EMPTY");
        } else {
            Log.info("\n" + new String(requestEntity));
        }

        // so the next method can access the body
        context.setEntityStream(new ByteArrayInputStream(requestEntity));
    }
}
