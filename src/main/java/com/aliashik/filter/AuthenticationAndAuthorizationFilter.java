package com.aliashik.filter;

import com.aliashik.annotation.SecureAPI;
import com.aliashik.model.ErrorMessage;
import com.aliashik.service.JWTService;
import io.jsonwebtoken.Claims;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Provider
@SecureAPI
@Priority(Priorities.AUTHENTICATION)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationAndAuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = null;
        try {
            claims = JWTService.parseToken(token);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(
                    "Authentication Error",
                    401,
                    "aliashiks.blogspot.com");
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(errorMessage)
                            .build());
        }


        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<String> classRoles = extractRoles(resourceClass);
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<String> methodRoles = extractRoles(resourceMethod);

        try {
            if (methodRoles.isEmpty()) {
                checkPermissions(classRoles, claims);
            } else {
                checkPermissions(methodRoles, claims);
            }
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(
                    "Access Denied",
                    403,
                    "aliashiks.blogspot.com");
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity(errorMessage)
                            .build());
        }
    }

    private List<String> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<String>();
        } else {
            SecureAPI secured = annotatedElement.getAnnotation(SecureAPI.class);
            if (secured == null) {
                return new ArrayList<String>();
            } else {
                String[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }
    private void checkPermissions(List<String> allowedRoles, Claims claims) throws Exception {
        if( null == claims )
            throw new Exception("No role found in the claims");
        List<Role> userRoles = (List<Role>) claims.get("Roles");
        allowedRoles.retainAll(userRoles);

        if(allowedRoles.size() == 0) {
            throw new Exception("Forbidden");
        }

    }

    public Key generateKey() {
        String keyString = "aliashik";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}