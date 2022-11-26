package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.MyObjects;

import edu.upc.dsa.domain.entity.TypeObject;
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


            TypeObject t1 = new TypeObject("1","xxxx");
            gameManager.addTypeObject(t1);
            TypeObject t2 = new TypeObject("2","xxxx");
            gameManager.addTypeObject(t2);
            TypeObject t3 = new TypeObject("3","xxxx");
            gameManager.addTypeObject(t3);


            MyObjects o1 = new MyObjects("11","Espada", "Espada con poderes","1", 3.1);
            gameManager.addObject(o1);
            MyObjects o2 = new MyObjects("22","Anillo", "Anillo teletransportador","2", 2.7);
            gameManager.addObject(o2);
            MyObjects o3 = new MyObjects("33","Traje", "Traje invisible", "3",4.5);
            gameManager.addObject(o3);
            MyObjects o4 = new MyObjects("44","Gafas", "Gafas visión del futuro","2", 5.25);
            gameManager.addObject(o4);
            MyObjects o5 = new MyObjects("55","Pistola", "Pistola laser", "2",1.35);
            gameManager.addObject(o5);
            MyObjects o6 = new MyObjects("66","Capa", "Capa voladora", "1",5);
            gameManager.addObject(o6);
        }
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

    @GET
    @ApiOperation(value = "get all Objects", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = MyObjects.class, responseContainer = "List"),
    })
    @Path("/Object")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects() {

        List<MyObjects> objects = this.gameManager.getTienda();
        GenericEntity<List<MyObjects>> entity = new GenericEntity<List<MyObjects>>(objects) {
        };
        return Response.status(201).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "get an Object", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = MyObjects.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObject/{idObject}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObject(@PathParam("idObject") String idObject) {
        MyObjects o = this.gameManager.getObject(idObject);
        if (o == null) return Response.status(404).build();
        else return Response.status(201).entity(o).build();
    }


}