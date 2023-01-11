package com.socialnetworking.rest;

import com.socialnetworking.domain.model.User;
import com.socialnetworking.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
