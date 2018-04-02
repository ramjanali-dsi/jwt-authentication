package com.aliashik.resource;

import com.aliashik.annotation.SecureAPI;
import com.aliashik.filter.Role;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.aliashik.filter.Role.ROLE_ADMIN;


@Path("/echo")

public class EchoEndpoint {

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response echo(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }

    @GET
    @Path("jwt")
    @SecureAPI({ROLE_ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response echoWithJWTToken(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }
}
