package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Object;

import edu.upc.dsa.domain.entity.exceptions.EmailAddressNotValidException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.to.UserRegister;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.EmailAddress;
import edu.upc.dsa.infraestructure.GameManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/User", description = "Endpoint to User Service")
@Path("/User")
public class GameManagerService {

    private GameManager gameManager;


    public GameManagerService() throws EmailAddressNotValidException, UserAlreadyExistsException {
        this.gameManager = GameManagerImpl.getInstance();
        if (gameManager.size() == 0) {
            Credentials credentials1 = new Credentials(new EmailAddress("oscar.boullosa@estudiantat.upc.edu"), "myPassword1");
            this.gameManager.registerUser("Óscar", "Boullosa Dapena", "08/03/2001", credentials1);
            Credentials credentials2 = new Credentials(new EmailAddress("itziar.mensa@estudiantat.upc.edu"), "myPassword2");
            this.gameManager.registerUser("Itziar", "Mensa Minguito", "24/11/2001", credentials2);


            Object o1 = new Object("11", "Espada", "Espada con poderes", 3.1);
            gameManager.addObject(o1);
            Object o2 = new Object("22", "Anillo", "Anillo teletransportador", 2.7);
            gameManager.addObject(o2);
            Object o3 = new Object("33", "Traje", "Traje invisible", 4.5);
            gameManager.addObject(o3);
            Object o4 = new Object("44", "Gafas", "Gafas visión del futuro", 5.25);
            gameManager.addObject(o4);
            Object o5 = new Object("55", "Pistola", "Pistola laser", 1.35);
            gameManager.addObject(o5);
            Object o6 = new Object("66", "Capa", "Capa voladora", 5);
            gameManager.addObject(o6);
        }
    }


    @GET
    @ApiOperation(value = "get all Objects", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Object.class, responseContainer = "List"),
    })
    @Path("/Object")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects() {

        List<Object> objects = this.gameManager.getTienda();
        GenericEntity<List<Object>> entity = new GenericEntity<List<Object>>(objects) {
        };
        return Response.status(201).entity(entity).build();

    }


    @GET
    @ApiOperation(value = "get an Object", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Object.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObject/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObject(@PathParam("nombre") String nombre) {
        Object o = this.gameManager.getObject(nombre);
        if (o == null) return Response.status(404).build();
        else return Response.status(201).entity(o).build();
    }

    @POST
    @ApiOperation(value = "register a new user", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesful", response = UserRegister.class),
            @ApiResponse(code = 409, message = "User already exists")
    })
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerUser(UserRegister user) throws UserAlreadyExistsException {
        try {
            this.gameManager.registerUser(user.getUserName(), user.getUserSurname(), user.getBirthDate(), user.getCredentials());
        } catch (UserAlreadyExistsException e) {
            return Response.status(409).entity(user).build();
        }
        return Response.status(201).entity(user).build();
    }
}