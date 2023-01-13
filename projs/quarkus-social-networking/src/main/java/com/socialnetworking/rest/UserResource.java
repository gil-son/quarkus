package com.socialnetworking.rest;

import com.socialnetworking.domain.model.User;
import com.socialnetworking.domain.repository.UserRepository;
import com.socialnetworking.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Validator;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserRepository repository;
    private Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser( CreateUserRequest createUserRequest){

        Set<ConstraintViolation<CreateUserRequest>> violations =
                validator.validate(createUserRequest);

        if(!violations.isEmpty()){

            ConstraintViolation<CreateUserRequest> error = violations.stream().findAny().get();
            String errorMessage = error.getMessage();

            return Response.status(400).entity(errorMessage).build();
        }

        User user = new User();
        user.setName(createUserRequest.getName());
        user.setAge(createUserRequest.getAge());

        repository.persist(user); // save entity in database

        return Response.ok(user).build();
    }

    @GET
    public Response listAll(){
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser( @PathParam("id") Long id){
        User user = repository.findById(id);

        if (user != null){
            repository.delete(user);
            return  Response.ok().build();
        }

        return  Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser( @PathParam("id") Long id, CreateUserRequest userData ){
        User user = repository.findById(id);

        if (user != null){
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            return  Response.ok().build();
        }

        return  Response.status(Response.Status.NOT_FOUND).build();
    }


}
