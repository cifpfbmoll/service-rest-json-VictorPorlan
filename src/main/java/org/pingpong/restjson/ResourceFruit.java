package org.pingpong.restjson;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/fruits")
public class ResourceFruit {

    @Inject
    ServiceFruit service;
    
    public ResourceFruit() {
        // CDI
    }

    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Colmados Farmer Rick";
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Fruit> list() {
        return service.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Fruit> add(@Valid Fruit fruit) {
        service.add(fruit);
        return this.list();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Fruit> delete(@Valid Fruit fruit) {
        service.remove(fruit.getName());
        return list();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name) {
        Optional<Fruit> fruit = service.getFruit(name);
        return fruit.isPresent()? 
            Response.status(Response.Status.OK).entity(fruit.get()).build() : 
            Response.status(Response.Status.NOT_FOUND).build();
    }
}