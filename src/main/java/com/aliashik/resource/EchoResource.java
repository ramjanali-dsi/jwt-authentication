package com.aliashik.resource;

import com.aliashik.annotation.SecureAPI;
import com.aliashik.filter.Role;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/echo")

public class EchoResource {

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response echo(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }

    @GET
    @Path("jwt")
    @SecureAPI({Role.ROLE_ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response echoWithJWTToken(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }
}
