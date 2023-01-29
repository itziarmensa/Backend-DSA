package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.*;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.to.Coins;
import edu.upc.dsa.domain.entity.to.PartidaCreate;
import edu.upc.dsa.domain.entity.to.UserRegister;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.Information;
import edu.upc.dsa.infraestructure.GameManagerDBImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;

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

    @POST
    @ApiOperation(value = "add a new Partida", notes = "Adds a new partida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Partida.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/partida")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPartida(PartidaCreate partidaCreate) {
        if (partidaCreate.getEmail() == null || partidaCreate.getObjectId() == null || partidaCreate.getCharacterId() == null) {
            return Response.status(500).build();
        }
        Partida partida = new Partida(partidaCreate.getEmail(), partidaCreate.getObjectId(), partidaCreate.getCharacterId());
        gameManager.createPartida(partida);
        return Response.status(200).entity(partida).build();
    }

    @GET
    @ApiOperation(value = "get all Partidas from a User", notes = "Gets all the partidas that a user has played")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Partida.class, responseContainer = "List"),
    })
    @Path("/user/{email}/partidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidasByUser(@PathParam("email") String email) {
        List<Partida> partidas = this.gameManager.getPartidasByUser(email);
        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {
        };
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Partidas", notes = "Gets all the partidas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Partida.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/partidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListPartidas() {
        List<Partida> partidas = this.gameManager.getAllPartidas();
        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {
        };
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "update a Partida", notes = "updates a Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @Path("/partida")
    public Response updatePartida(Partida partida) {
        this.gameManager.updatePartida(partida);
        return Response.status(200).build();
    }

    @PUT
    @ApiOperation(value = "update a User", notes = "updates a User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @Path("/user")
    public Response updateUser(User user) {
        this.gameManager.updateUser(user);
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get Users by points", notes = "Gets all the users ordered by points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/users/ranking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByPoints() {
        List<User> users = this.gameManager.getUsersByPoints();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {
        };
        return Response.status(200).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "add a FAQ", notes = "Adds a new FAQ and the answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Faqs.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/FAQs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFaqs(Faqs faqs) {
        if (faqs.getQuestion() == null || faqs.getAnswer() == null) {
            return Response.status(500).build();
        }
        this.gameManager.addFaqs(faqs);
        return Response.status(200).entity(faqs).build();
    }

    @GET
    @ApiOperation(value = "get all FAQs", notes = "Gets all the FAQs that are created")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Faqs.class, responseContainer = "List"),
    })
    @Path("/FAQs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFaqs() {
        List<Faqs> faqs = this.gameManager.getFaqs();
        GenericEntity<List<Faqs>> entity = new GenericEntity<List<Faqs>>(faqs) {
        };
        return Response.status(200).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "send an issue", notes = "Send Issue")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Issue.class)
    })
    @Path("/issue")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response sendIssue(Issue issue) {
        this.gameManager.addIssue(issue);
        return Response.status(200).entity(issue).build();
    }

    @GET
    @ApiOperation(value = "get all issues", notes = "Get Issues")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Issue.class, responseContainer = "List")
    })
    @Path("/issue")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getIssues() {
        List<Issue> list = this.gameManager.getListIssues();
        GenericEntity<List<Issue>> entity = new GenericEntity<List<Issue>>(list) {
        };
        return Response.status(200).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "add Information", notes = "Adds new information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Information.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/information")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInformation(Information information) {
        if (information.getDate() == null || information.getTitle() == null || information.getMessage() == null || information.getSender() == null) {
            return Response.status(500).build();
        }
        this.gameManager.addInformation(information);
        return Response.status(200).entity(information).build();
    }

    @GET
    @ApiOperation(value = "get all information", notes = "Gets all the information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Information.class, responseContainer = "List"),
    })
    @Path("/information")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformation() {
        List<Information> informationList = this.gameManager.getInformation();
        GenericEntity<List<Information>> entity = new GenericEntity<List<Information>>(informationList) {
        };
        return Response.status(200).entity(entity).build();
    }
}