package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.to.Coins;
import edu.upc.dsa.domain.entity.to.ObjectReg;
import edu.upc.dsa.domain.entity.to.TypeReg;
import edu.upc.dsa.domain.entity.vo.TypeObject;
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

@Api(value = "/myObjects", description = "Endpoint to MyObjects Service")
@Path("/myObjects")
public class MyObjectsService {

    private GameManager gameManager;

    public MyObjectsService() {
        this.gameManager = GameManagerImpl.getInstance();
        if (gameManager.size() == 0) {
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
    @ApiOperation(value = "add a new Object", notes = "Adds a new object to the store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ObjectReg.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addObject(ObjectReg objectReg) {
        if (objectReg.getIdObjectReg() == null || objectReg.getNameReg() == null ||objectReg.getDescriptionObjectReg() == null || objectReg.getCoinsReg() == 0.0 || objectReg.getIdTypeObjectReg() == null) {
            return Response.status(500).build();
        }
        gameManager.addObject(objectReg);
        return Response.status(200).entity(objectReg).build();
    }

    @GET
    @ApiOperation(value = "get all Objects", notes = "Gets all the objects from the store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObjects.class, responseContainer = "List"),
    })
    @Path("/")
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
    @Path("/{idObject}")
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
    @Path("/{idObject}")
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
    @Path("/{type}/myObjects")
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
    @Path("/{type}/myObjects")
    public Response deleteListObject(@PathParam("type") String type) {
        this.gameManager.deleteListObject(type);
        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "add a new type of Object", notes = "Adds a new type of object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = TypeObject.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/type")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addTypeObject(TypeReg typeReg) {
        if (typeReg.getIdTypeReg() == null || typeReg.getDescriptionReg() == null)
        {
            return Response.status(500).build();
        }
        TypeObject myTypeObject = new TypeObject(typeReg.getIdTypeReg(),typeReg.getDescriptionReg());
        this.gameManager.addTypeObject(myTypeObject);
        return Response.status(200).entity(myTypeObject).build();
    }

    @GET
    @ApiOperation(value = "get the coins of an Object", notes = "Gets the coins of an object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Coins.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/{name}/coins")
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
    @Path("/{name}/description")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescriptionObject(@PathParam("name") String name) {
        String des = this.gameManager.getDescriptionObject(name);
        if(des == "") return Response.status(404).entity(des).build();
        return Response.status(200).entity(des).build();
    }
}