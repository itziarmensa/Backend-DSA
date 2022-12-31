package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.exceptions.UserNotExistsException;
import edu.upc.dsa.domain.entity.to.Coins;
import edu.upc.dsa.domain.entity.to.UserRegister;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.ObjectType;
import edu.upc.dsa.infraestructure.GameManagerDBImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/gameManager", description = "Endpoint to GameManager Service")
@Path("/gameManager")
public class GameManagerService {

    private GameManager gameManager;

    public GameManagerService() {
        this.gameManager = GameManagerDBImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "register a new user", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 406, message = "User already exists"),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerUser(UserRegister user) throws UserAlreadyExistsException {
        if (user.getUserName() == null || user.getUserSurname() == null || user.getUserBirth() == null || user.getEmail() == null || user.getPassword() == null) {
            return Response.status(500).build();
        }
        try {
            this.gameManager.registerUser(user.getUserName(), user.getUserSurname(), user.getUserBirth(), user.getEmail(), user.getPassword());
        } catch (UserAlreadyExistsException e) {
            return Response.status(406).build();
        }
        return Response.status(200).entity(user).build();
    }

    @POST
    @ApiOperation(value = "login of a User", notes = "Login of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(Credentials credentials) {
        if (credentials.getEmail() == null || credentials.getPassword() == null) {
            return Response.status(500).build();
        }
        if (!this.gameManager.login(credentials)) {
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "add a new Object", notes = "Adds a new object to the store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObjects.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/myObjects")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addObject(MyObjects myObject) {
        if (myObject.getObjectId() == null || myObject.getObjectName() == null || myObject.getObjectDescription() == null || myObject.getObjectCoins() == 0.0 || myObject.getObjectTypeId() == null) {
            return Response.status(500).build();
        }
        gameManager.addObject(myObject);
        return Response.status(200).entity(myObject).build();
    }

    @GET
    @ApiOperation(value = "get all Objects", notes = "Gets all the objects from the store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObjects.class, responseContainer = "List"),
    })
    @Path("/myObjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects() {
        List<MyObjects> objects = this.gameManager.getTienda();
        GenericEntity<List<MyObjects>> entity = new GenericEntity<List<MyObjects>>(objects) {
        };
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get an Object", notes = "Gets an object from the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObjects.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/myObjects/{idObject}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObject(@PathParam("idObject") String idObject) {
        MyObjects object = this.gameManager.getObject(idObject);
        if (object == null) return Response.status(404).build();
        else return Response.status(200).entity(object).build();
    }

    @DELETE
    @ApiOperation(value = "delete an Object", notes = "Deletes an object with the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
    })
    @Path("/myObjects/{idObject}")
    public Response deleteObject(@PathParam("idObject") String idObject) {
        this.gameManager.deleteObject(idObject);
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get a List of Object from the type", notes = "Gets a list of objects of a certain type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObjects.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/myObjects/{type}/myObjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListObject(@PathParam("type") String type) {
        List<MyObjects> myList = this.gameManager.getListObject(type);
        GenericEntity<List<MyObjects>> entity = new GenericEntity<List<MyObjects>>(myList) {
        };
        return Response.status(200).entity(entity).build();
    }

    @DELETE
    @ApiOperation(value = "delete all Object with a certain type", notes = "Deletes an object from a certain type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
    })
    @Path("/myObjects/{type}/myObjects")
    public Response deleteListObject(@PathParam("type") String type) {
        this.gameManager.deleteListObject(type);
        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "add a new type of Object", notes = "Adds a new type of object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ObjectType.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/myObjects/type")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addTypeObject(ObjectType objectType) {
        if (objectType.getObjectTypeId() == null || objectType.getObjectTypeDescription() == null)
        {
            return Response.status(500).build();
        }
        ObjectType myObjectType = new ObjectType(objectType.getObjectTypeId(), objectType.getObjectTypeDescription());
        this.gameManager.addTypeObject(myObjectType);
        return Response.status(200).entity(myObjectType).build();
    }

    @GET
    @ApiOperation(value = "get the coins of an Object", notes = "Gets the coins of an object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Coins.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/myObjects/{name}/coins")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoinsObject(@PathParam("name") String name) {
        double coins = this.gameManager.getCoinsObject(name);
        Coins newCoins = new Coins(coins);
        GenericEntity <Coins> entity = new GenericEntity<Coins>(newCoins) {  };
        if (newCoins == null) return Response.status(404).entity(entity).build();
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get description of an Object", notes = "Gets the description of an object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = String.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/myObjects/{name}/description")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescriptionObject(@PathParam("name") String name) {
        String des = this.gameManager.getDescriptionObject(name);
        if(des == "") return Response.status(404).entity(des).build();
        return Response.status(200).entity(des).build();
    }

    @PUT
    @ApiOperation(value = "buy an Object", notes = "Buys an object for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Error Buying")
    })
    @Path("/user/{email}/{objectId}")
    public Response buyObject(@PathParam("email") String email, @PathParam("objectId") String objectId) throws UserNotExistsException {
        try {
            this.gameManager.buyObject(email, objectId);
        }
        catch (UserNotExistsException | NotEnoughCoinsException e) {
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get all Objects from a User", notes = "Gets all the objects that a user has bought")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObjects.class, responseContainer = "List"),
    })
    @Path("/user/{email}/myObjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectsByUser(@PathParam("email") String email) {
        List<MyObjects> objects = this.gameManager.getObjectsByUser(email);
        GenericEntity<List<MyObjects>> entity = new GenericEntity<List<MyObjects>>(objects) {
        };
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a List of Characters from the type", notes = "Gets a list of objects of a certain type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Characters.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/characters")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListCharacters() {
        List<Characters> myCharacters = this.gameManager.getAllCharacters();
        GenericEntity<List<Characters>> entity = new GenericEntity<List<Characters>>(myCharacters) {
        };
        return Response.status(200).entity(entity).build();
    }
}