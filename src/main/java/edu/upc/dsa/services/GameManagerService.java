package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.EmailAddressNotValidException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.to.Coins;
import edu.upc.dsa.domain.entity.to.UserRegister;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.Dice;
import edu.upc.dsa.domain.entity.ObjectType;
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

@Api(value = "/gameManager", description = "Endpoint to GameManager Service")
@Path("/gameManager")
public class GameManagerService {

    private GameManager gameManager;

    public GameManagerService() throws EmailAddressNotValidException, UserAlreadyExistsException {
        this.gameManager = GameManagerImpl.getInstance();
        if (gameManager.size() == 0) {
            this.gameManager.registerUser("Óscar", "Boullosa Dapena", "08/03/2001", "oscar.boullosa@estudiantat.upc.edu", "myPassword1");
            this.gameManager.registerUser("Itziar", "Mensa Minguito", "24/11/2001", "itziar.mensa@estudiantat.upc.edu", "myPassword2");

            ObjectType t1 = new ObjectType("1","xxxx");
            gameManager.addTypeObject(t1);
            ObjectType t2 = new ObjectType("2","xxxx");
            gameManager.addTypeObject(t2);
            ObjectType t3 = new ObjectType("3","xxxx");
            gameManager.addTypeObject(t3);

            MyObjects o1 = new MyObjects("11","Espada", "Espada con poderes", 3.1,"1");
            gameManager.addObject(o1);
            MyObjects o2 = new MyObjects("22","Anillo", "Anillo teletransportador", 2.7,"2");
            gameManager.addObject(o2);
            MyObjects o3 = new MyObjects("33","Traje", "Traje invisible", 4.5, "3");
            gameManager.addObject(o3);
            MyObjects o4 = new MyObjects("44","Gafas", "Gafas visión del futuro", 5.25,"2");
            gameManager.addObject(o4);
            MyObjects o5 = new MyObjects("55","Pistola", "Pistola laser", 1.35, "2");
            gameManager.addObject(o5);
            MyObjects o6 = new MyObjects("66","Capa", "Capa voladora", 5, "1");
            gameManager.addObject(o6);


            Characters c1 = new Characters("c1","Mario","hombre","d1",100);
            gameManager.addCharacter(c1);
            Characters c2 = new Characters("c2","Donkey Kong","mono","d2",50);
            gameManager.addCharacter(c2);
            Characters c3 = new Characters("c3","Diddy Kong","mono","d3",40);
            gameManager.addCharacter(c3);
            Characters c4 = new Characters("c4","Yoshi","cocodrilo","d4",80);
            gameManager.addCharacter(c4);
            Characters c5 = new Characters("c5","Pum pum","tortuga","d5",20);
            gameManager.addCharacter(c5);
            Characters c6 = new Characters("c6","Huesitos","fantasma","d1",60);
            gameManager.addCharacter(c6);
            Characters c7 = new Characters("c7","Pum pum","tortuga","d5",20);
            gameManager.addCharacter(c7);


            Dice d1 = new Dice("d1","6-6-6-6");
            gameManager.addDice(d1);
            Dice d2 = new Dice("d2","0-0-0-10-10");
            gameManager.addDice(d2);
            Dice d3 = new Dice("d3","0-0-7-7-7");
            gameManager.addDice(d3);
            Dice d4 = new Dice("d4","0-1-3-3-5-7");
            gameManager.addDice(d4);
            Dice d5 = new Dice("d5","0-3-3-3-3-8");
            gameManager.addDice(d5);
        }
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

    @GET
    @ApiOperation(value = "get a List of Dice from the type", notes = "Gets a list of objects of a certain type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Characters.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/dice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListDice() {
        List<Dice> myDice = this.gameManager.getAllDice();
        GenericEntity<List<Dice>> entity = new GenericEntity<List<Dice>>(myDice) {
        };
        return Response.status(200).entity(entity).build();
    }
}