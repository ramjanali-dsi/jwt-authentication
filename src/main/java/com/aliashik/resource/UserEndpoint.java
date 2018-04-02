package com.aliashik.resource;

import com.aliashik.filter.Role;
import com.aliashik.model.User;
import com.aliashik.service.JWTService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)

public class UserEndpoint {

    @Context
    private UriInfo uriInfo;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user) {

        try {
            authenticate(user.getUsername(), user.getPassword());
            String token = JWTService.createJWT(user.getUsername(), uriInfo.getAbsolutePath().toString(),"Subject", 1000000);
            System.out.println("Token: " + token);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        User user = new User(username, password);
        if (user == null)
            throw new SecurityException("Invalid user/password");
    }
}
