package com.baloise.codecamp.wildfly;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Readiness
@ApplicationScoped
public class ReadinessEndpoint implements HealthCheck {

   @PersistenceContext(unitName = "quickstarterDS")
   private EntityManager em;

   @Override
   public HealthCheckResponse call() {
      HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
         .named("Repository up");
      if (em != null && !em.createNativeQuery("SELECT 1").getResultList().isEmpty())
         responseBuilder.up();
      else
         responseBuilder.down();
      return responseBuilder.build();
   }
}