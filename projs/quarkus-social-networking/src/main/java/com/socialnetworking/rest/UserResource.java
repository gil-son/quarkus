package com.socialnetworking.rest;

import com.socialnetworking.dto.CreateUserRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    public Response createUser( CreateUserRequest createUserRequest){
        return Response.ok().build();
    }

}
