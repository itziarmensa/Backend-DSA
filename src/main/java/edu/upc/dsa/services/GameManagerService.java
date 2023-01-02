package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
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
    @ApiOperation(value = "register a new User", notes = "Register User")
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

    @GET
    @ApiOperation(value = "get all Users", notes = "Gets all the users that are registered")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUsers() {
        List<User> users = this.gameManager.getUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {
        };
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get the coins of a User", notes = "Gets the coins of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Coins.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/user/{email}/coins")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoinsUser(@PathParam("email") String email) {
        double coins = this.gameManager.getUserCoins(email);
        Coins newCoins = new Coins(coins);
        GenericEntity <Coins> entity = new GenericEntity<Coins>(newCoins) {  };
        if (newCoins == null) return Response.status(404).entity(entity).build();
        return Response.status(200).entity(entity).build();
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
    @Path("/myObjects/{idObject}/coins")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoinsObject(@PathParam("idObject") String idObject) {
        double coins = this.gameManager.getCoinsObject(idObject);
        Coins newCoins = new Coins(coins);
        GenericEntity <Coins> entity = new GenericEntity<Coins>(newCoins) {  };
        if (newCoins == null) return Response.status(404).entity(entity).build();
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "buy an Object", notes = "Buys an object for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Error Buying")
    })
    @Path("/user/buyObject/{email}/{objectId}")
    public Response buyObject(@PathParam("email") String email, @PathParam("objectId") String objectId) {
        try {
            this.gameManager.buyObject(email, objectId);
        }
        catch (NotEnoughCoinsException e) {
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }

    @PUT
    @ApiOperation(value = "buy a Character", notes = "Buys a character for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Error Buying")
    })
    @Path("/user/buyCharacter/{email}/{characterId}")
    public Response buyCharacter(@PathParam("email") String email, @PathParam("characterId") String characterId) {
        try {
            this.gameManager.buyCharacter(email, characterId);
        }
        catch (NotEnoughCoinsException e) {
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "add a new Character", notes = "Adds a new character to the store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Characters.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/characters")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addCharacter(Characters character) {
        if (character.getCharacterId() == null || character.getCharacterName() == null || character.getCharacterDescription() == null || character.getCharacterCoins() == 0.0) {
            return Response.status(500).build();
        }
        gameManager.addCharacter(character);
        return Response.status(200).entity(character).build();
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
    @ApiOperation(value = "get all Characters from a User", notes = "Gets all the characters that a user has bought")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Characters.class, responseContainer = "List"),
    })
    @Path("/user/{email}/characters")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharactersByUser(@PathParam("email") String email) {
        List<Characters> characters = this.gameManager.getCharactersByUser(email);
        GenericEntity<List<Characters>> entity = new GenericEntity<List<Characters>>(characters) {
        };
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Characters", notes = "Gets all the characters from the store")
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

    @GET
    @ApiOperation(value = "get a Character", notes = "Gets a character from the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Characters.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/characters/{idCharacter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacter(@PathParam("idCharacter") String idCharacter) {
        Characters character = this.gameManager.getCharacter(idCharacter);
        if (character == null) return Response.status(404).build();
        else return Response.status(200).entity(character).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Character", notes = "Deletes a character with the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
    })
    @Path("/characters/{idCharacter}")
    public Response deleteCharacter(@PathParam("idCharacter") String idCharacter) {
        this.gameManager.deleteCharacter(idCharacter);
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get the coins of a Character", notes = "Gets the coins of a character")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Coins.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/characters/{idCharacter}/coins")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoinsCharacter(@PathParam("idCharacter") String idCharacter) {
        double coins = this.gameManager.getCoinsCharacter(idCharacter);
        Coins newCoins = new Coins(coins);
        GenericEntity <Coins> entity = new GenericEntity<Coins>(newCoins) {  };
        if (newCoins == null) return Response.status(404).entity(entity).build();
        return Response.status(200).entity(entity).build();
    }
}