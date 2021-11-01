package com.baloise.codecamp.wildfly;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/hello")
@ApplicationScoped
public class HelloWorldEndpoint {

    @PersistenceContext(unitName = "quickstarterDS")
    private EntityManager em;

    @GET
    @Produces("text/plain")
    @Metered(name = "helloMeter", tags = {"spec=JAX-RS", "level=REST"})
    @Timed(name = "helloElapsed",
            absolute = true,
            description = "Time needed to process the hello-api")
    @Counted(name = "helloCnt",
            absolute = true,
            description = "Number of times the hello api is requested")
    public Response doGet() {
        if (em == null) {
            return Response.ok("Hello from WildFly bootable jar! The persistence-unit coud not be bound!").build();
        } else {
            return Response.ok("Hello from WildFly bootable jar!").build();
        }
    }

    @Gauge(unit = MetricUnits.PERCENT,
            name = "happyness",
            absolute = true,
            description = "Current grade of happyness")
    public int currentHappyness() {
        return new Random().nextInt(100);
    }
}