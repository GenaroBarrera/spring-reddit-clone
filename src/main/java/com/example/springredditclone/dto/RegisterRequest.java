package com.example.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class: Data Transfer Object (for user registration request)
 * The API call should contain the request body which is of type RegisterRequest.
 * Through this class we are transferring the user details like username, password and email as part of the RequestBody.
 * We call this kind of classes as a DTO (Data Transfer Object).
 * We will create this class inside a different package called com.example.springredditclone.dto
 * "This is the Data Transfer Object aka dto"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
