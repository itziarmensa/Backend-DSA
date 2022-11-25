package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Object;

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

    private GameManager gj;


    public GameManagerService() {
        this.gj = GameManagerImpl.getInstance();

        Object o1 = new Object("11","Espada", "Espada con poderes", 3.1);
        gj.addObject(o1);
        Object o2 = new Object("22","Anillo", "Anillo teletransportador", 2.7);
        gj.addObject(o2);
        Object o3 = new Object("33","Traje", "Traje invisible", 4.5);
        gj.addObject(o3);
        Object o4 = new Object("44","Gafas", "Gafas visión del futuro", 5.25);
        gj.addObject(o4);
        Object o5 = new Object("55","Pistola", "Pistola laser", 1.35);
        gj.addObject(o5);
        Object o6 = new Object("66","Capa", "Capa voladora", 5);
        gj.addObject(o6);

    }




    @GET
    @ApiOperation(value = "get all Objects", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Object.class, responseContainer = "List"),
    })
    @Path("/Object")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects() {

        List<Object> objects = this.gj.getTienda();
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
        Object o = this.gj.getObject(nombre);
        if (o == null) return Response.status(404).build();
        else return Response.status(201).entity(o).build();
    }



}