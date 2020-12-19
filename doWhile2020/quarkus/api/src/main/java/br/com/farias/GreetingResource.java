package br.com.farias;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api")
public class GreetingResource {

    @Inject
    @RestClient
    private TimeService service;

    @Counted(name = "time.count")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timeout(500)
    @Fallback(fallbackMethod = "fallback")
    @CircuitBreaker(
        requestVolumeThreshold=4,
        failureRatio=0.5,
        delay = 2000,
        successThreshold=4
    )
    public String hello() throws InterruptedException {
        Thread.sleep(400);
        return "api \\o/ -> "+getTime();
    }

    @Timed (name = "time.timed")
    private String getTime() {
        return service.getTime();
    }

    private String fallback() {
        return "corram para as colinas, caiu no fallback \n";
    }
}