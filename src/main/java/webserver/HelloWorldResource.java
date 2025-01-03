package webserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;

    public HelloWorldResource(String template) {
        this.template = template;
    }

    @GET
    public String sayHello() {
        return template.replace("{name}", "world");
    }
}