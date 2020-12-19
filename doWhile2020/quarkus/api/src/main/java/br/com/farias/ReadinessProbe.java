package br.com.farias;

import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Readiness
public class ReadinessProbe implements HealthCheck {

    @Inject
    @RestClient
    TimeService timeService;

    @Override
    public HealthCheckResponse call() {

        if (timeService.getTime() == null) {
            return HealthCheckResponse.up("não estou pronto");
    
        } else {
            return HealthCheckResponse.up("estou pronto");
    
        }
    }
    
}
