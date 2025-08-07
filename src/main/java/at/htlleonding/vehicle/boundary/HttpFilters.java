package at.htlleonding.vehicle.boundary;

import io.quarkus.logging.Log;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

public class HttpFilters {

    @RouteFilter
    void restLoggingFilter(RoutingContext rc) {

        String logEntry = "REQUEST\n"
                + rc.request().method().toString()
                + " "
                + rc.request().absoluteURI()
                + "\n"
                + rc.request().headers().toString();
                //+ "\n"
                //+ (rc.request().body().result() == null ? "BODY IS EMPTY" : rc.request().body().result().toString());

        Log.info(logEntry);
        rc.next();

    }

}
