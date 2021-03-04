package com.example.springredditclone.controller;

import com.example.springredditclone.dto.AuthenticationResponse;
import com.example.springredditclone.dto.LoginRequest;
import com.example.springredditclone.dto.RefreshTokenRequest;
import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.service.AuthService;
import com.example.springredditclone.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;


/**
 * Class: Authentication Controller
 * So we have created a RestController and inside this controller,
 * we first created a method that will be invoked whenever a
 * POST request is made to register the user’s in our application.
 */

/**
 * @RestController is a specialized version of the controller.
 * It includes the @Controller and @ResponseBody annotations and as a result, simplifies the controller implementation:
 */
@RestController
/**
 * @RequestMapping is one of the most common annotation used in Spring Web applications.
 * This annotation maps HTTP requests to handler methods of MVC and REST controllers.
 *
 * The API call should contain the request body which is of type RegisterRequest. Through this class we are transferring the user details like username, password and email as part of the RequestBody.
 * We call this kind of classes as a DTO (Data Transfer Object).
 * We will create this class inside a different package called com.example.springredditclone.dto
 */
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    /**
     * signup() Rest Endpoint for signup
     * The signup method inside the AuthController is calling another method inside the AuthService class,
     * which is mainly responsible to create the User object and storing it in the database.
     *
     * @PostMapping is specialized version of @RequestMapping annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST).
     * @PostMapping annotated methods handle the HTTP POST requests matched with given URI expression. e.g.
     * @param registerRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest); //Note: this an api call, it contains registerRequest as the request body!
        return new ResponseEntity(OK);
    }

    /**
     * login() REST Endpoint for Login
     * The login endpoint accepts POST request and passes the LoginRequest object to the login() method of the AuthService class
     * TODO: Note you have to signup and verify your email before you can login (avoid 403 forbidden in postman test)
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    /**
     * verifyAccount()
     * Create Endpoint to Verify Users
     * So we have created the logic to send out emails after registration,
     * now let’s create an endpoint to Verify Users.
     * Add the below method to AuthController
     * @param token
     * @return
     */
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", OK);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
