package com.example.springredditclone.util;

import lombok.experimental.UtilityClass;

/**
 * Class: Constants Utility
 * As this is a Utility Class, we have annotated this class with @UtilityClass which is a Lombok annotation,
 * this annotation will make the following changes at compile time to our class:
 *
 * Marks the class as final.
 * It generates a private no-arg constructor.
 * It only allows the methods or fields to be static.
 * A Utility class, by definition, should not contain any state. Hence it is usual to put shared constants or methods inside utility class so that they can be reused. As they are shared and not tied to any specific object it makes sense to mark them as static.
 */
@UtilityClass
public class Constants {
    //For the activation URL, as we are using this from our local machines let’s provide the URL as –
    public static final String ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification";
}
