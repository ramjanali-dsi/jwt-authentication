package com.aliashik.resource;

import com.aliashik.model.Message;
import com.aliashik.model.User;
import com.aliashik.service.JWTService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)

public class LoginResource {

    @Context
    private UriInfo uriInfo;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user) {
        try {
            authenticate(user.getUsername(), user.getPassword());
            String token = JWTService.createJWT(user.getUsername(), uriInfo.getAbsolutePath().toString(),"Subject", 1000000);
            return Response.ok()
                    .entity(new Message("SUCCESS", "Login Successful"))
                    .header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED)
                    .entity(new Message("FAILED", "Login Unsuccessful")).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        //TODO authenticate the user with username and password if authentication fails throw exception
        User user = new User(username, password);
        if (user == null)
            throw new SecurityException("Invalid user/password");
        //TODO =========================================================================================
    }
}
