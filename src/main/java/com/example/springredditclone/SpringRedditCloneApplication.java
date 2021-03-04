package com.example.springredditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Class: Main SpringBootApp
 */

@SpringBootApplication
/**
 * @EnableAsync
 * If you check the timestamp, we have a delay of more than 10 seconds to send out the email,
 * that means even though the registration is completed, the user has to wait 10 more seconds to see the response.
 * Even though this is not much time, in the real world situation we should handle the Email Sending functionality Asynchronously,
 * we can also handle it by using a Message Queue like RabbitMQ, but I think that would be an overkill for our use-case.
 * Let’s enable the Async module in spring by adding the @EnableAsync to our Main class
 */
@EnableAsync
public class SpringRedditCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringRedditCloneApplication.class, args);
	}
}
