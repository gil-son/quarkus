package com.socialnetworking.rest;

import com.socialnetworking.domain.model.User;
import com.socialnetworking.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional
    public Response createUser( CreateUserRequest createUserRequest){

        User user = new User();
        user.setName(createUserRequest.getName());
        user.setAge(createUserRequest.getAge());

        user.persist(); // save entity in database

//        user.count();
//        user.delete("delete from User where age < 18");

        return Response.ok(user).build();
    }

    @GET
    public Response listAll(){
        PanacheQuery<User> query = User.findAll(); // User extends PanacheEntityBase
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser( @PathParam("id") Long id){
        User user = User.findById(id); // User extends PanacheEntityBase

        if (user != null){
            user.delete();
            return  Response.ok().build();
        }

        return  Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser( @PathParam("id") Long id, CreateUserRequest userData ){
        User user = User.findById(id); // User extends PanacheEntityBase

        if (user != null){
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            return  Response.ok().build();
        }

        return  Response.status(Response.Status.NOT_FOUND).build();
    }


}
