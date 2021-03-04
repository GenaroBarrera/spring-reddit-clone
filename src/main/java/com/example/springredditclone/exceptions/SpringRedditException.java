package com.example.springredditclone.exceptions;

/**
 * Class: Custom Exception
 * Once the email message is prepared, we send the email message.
 * If there are any unexpected exceptions raised during sending the email,
 * we are catching those exceptions and rethrowing them as custom exceptions.
 * This is a good practice as we don’t expose the internal technical exception details to the user,
 * by creating custom exceptions, we can create our own error messages and provide them to the users.
 */
public class SpringRedditException extends RuntimeException {
    // added another constructor which takes the error message as well as the root cause of the exception,
    // this makes the error handling much clearer, and you can identify the root cause.
    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
