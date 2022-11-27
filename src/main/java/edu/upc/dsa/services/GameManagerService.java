package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.to.Coins;
import edu.upc.dsa.domain.entity.to.ObjectReg;
import edu.upc.dsa.domain.entity.to.TypeReg;
import edu.upc.dsa.domain.entity.vo.TypeObject;
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


            ObjectReg o1 = new ObjectReg("11","Espada", "Espada con poderes","1", 3.1);
            gameManager.addObject(o1);
            ObjectReg o2 = new ObjectReg("22","Anillo", "Anillo teletransportador","2", 2.7);
            gameManager.addObject(o2);
            ObjectReg o3 = new ObjectReg("33","Traje", "Traje invisible", "3",4.5);
            gameManager.addObject(o3);
            ObjectReg o4 = new ObjectReg("44","Gafas", "Gafas visión del futuro","2", 5.25);
            gameManager.addObject(o4);
            ObjectReg o5 = new ObjectReg("55","Pistola", "Pistola laser", "2",1.35);
            gameManager.addObject(o5);
            ObjectReg o6 = new ObjectReg("66","Capa", "Capa voladora", "1",5);
            gameManager.addObject(o6);
        }
    }

    @POST
    @ApiOperation(value = "register a new user", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesful", response = User.class),
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

    @POST
    @ApiOperation(value = "login of a User", notes = "Login of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "Error")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(Credentials credentials) {
        if (credentials.getEmail().getEmail() == null || credentials.getPassword() == null) {
            return Response.status(500).build();
        }
        if (!this.gameManager.login(credentials)) {
            return Response.status(501).build();
        }
        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "add a new Object at the store", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesful", response = ObjectReg.class),
            @ApiResponse(code = 404, message = "User already exists")
    })
    @Path("/MyObjects")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addObject(ObjectReg o) throws UserAlreadyExistsException {
        try {
            gameManager.addObject(o);
        } catch (Exception e) {
            return Response.status(404).entity(o).build();
        }
        return Response.status(201).entity(o).build();
    }

    @GET
    @ApiOperation(value = "get all Objects", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = MyObjects.class, responseContainer = "List"),
    })
    @Path("/MyObjects")
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

    @DELETE
    @ApiOperation(value = "delete an Object", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObjects/{idObject}")
    public Response deleteObject(@PathParam("idObject") String idObject) {
        try{
            this.gameManager.deleteObject(idObject);
        } catch (Exception e) {
            return Response.status(404).entity(idObject).build();
        }
        return Response.status(201).entity(idObject).build();
    }

    @GET
    @ApiOperation(value = "get a List of Object from the type", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = MyObjects.class ,responseContainer = "List"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObjects/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListObject(@PathParam("type") String type) {
        List<MyObjects> myList = this.gameManager.getListObject(type);
        GenericEntity<List<MyObjects>> entity = new GenericEntity<List<MyObjects>>(myList) {
        };
        return Response.status(201).entity(entity).build();
    }


    @DELETE
    @ApiOperation(value = "delete all Object with a certain type", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObjects/{type}/delete")
    public Response deleteListObject(@PathParam("type") String type) {
        try{
            this.gameManager.deleteListObject(type);
        } catch (Exception e) {
            return Response.status(404).entity(type).build();
        }
        return Response.status(201).entity(type).build();
    }

    @POST
    @ApiOperation(value = "add a new type of Object", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesful", response = TypeObject.class),
            @ApiResponse(code = 404, message = "User already exists")
    })
    @Path("/MyObjects/type")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addTypeObject(TypeReg typeReg) {
        TypeObject myTypeObject = new TypeObject(typeReg.getIdTypeReg(),typeReg.getDescriptionReg());
        try {
            this.gameManager.addTypeObject(myTypeObject);
        } catch (Exception e) {
            return Response.status(404).entity(myTypeObject).build();
        }
        return Response.status(201).entity(myTypeObject).build();
    }

    @GET
    @ApiOperation(value = "get the coins of an Object", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Double.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObject/{name}/coins")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoinsObject(@PathParam("name") String name) {
        double coins = this.gameManager.getCoinsObject(name);
        Coins c = new Coins(coins);
        GenericEntity <Coins> entity = new GenericEntity<Coins>(c) {  };
        if (c==null)return Response.status(404).entity(entity).build();
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get description of an Object", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = String.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/MyObject/{name}/description")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescriptionObject(@PathParam("name") String name) {
        String des = this.gameManager.getDescriptionObject(name);
        if(des == "")return Response.status(404).entity(des).build();
        return Response.status(201).entity(des).build();
    }

}