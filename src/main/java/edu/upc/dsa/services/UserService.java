package edu.upc.dsa.services;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.User;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UserService {

    private GameManager gameManager;

    public UserService() throws EmailAddressNotValidException, UserAlreadyExistsException {
        this.gameManager = GameManagerImpl.getInstance();
        if (gameManager.size() == 0) {
            Credentials credentials1 = new Credentials(new EmailAddress("oscar.boullosa@estudiantat.upc.edu"), "myPassword1");
            this.gameManager.registerUser("Óscar", "Boullosa Dapena", "08/03/2001", credentials1);
            Credentials credentials2 = new Credentials(new EmailAddress("itziar.mensa@estudiantat.upc.edu"), "myPassword2");
            this.gameManager.registerUser("Itziar", "Mensa Minguito", "24/11/2001", credentials2);
        }
    }

    @POST
    @ApiOperation(value = "register a new user", notes = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 409, message = "User already exists")
    })
    @Path("/")
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
}