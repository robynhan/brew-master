package org.robynhan.com.exampleservice.hello.resource;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.robynhan.com.exampleservice.hello.Greeting;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Component
@Path("/greetings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingController {
    private List<Greeting> greetings = Lists.newArrayList();

    public GreetingController(){
        greetings.add(new Greeting("0", "default"));
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") final String id) {
        return Response.ok(greetings.stream()
                .filter(greeting -> Objects.equals(greeting.getId(), id))
                .findFirst()
                .orElse(new Greeting())).build();
    }

    @POST
    @Path("/")
    public Response post(@Valid @NotNull final Greeting greeting) {
        if (!Strings.isNullOrEmpty(greeting.getId())) {
            greetings.add(greeting);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        greetings.removeIf(greeting -> greeting.getId().equals(id));
        return Response.ok().build();
    }
}
