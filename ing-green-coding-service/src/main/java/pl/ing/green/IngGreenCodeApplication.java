package pl.ing.green;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main application written in Spring Boot.
 *
 * @author Jakub Strychowski
 */
@SpringBootApplication
@RestController
public class IngGreenCodeApplication {

    /**
     * Starts spring boot application.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        SpringApplication.run(IngGreenCodeApplication.class, args);
    }

//  Uncommend if you wish to have security error (cross site scripting).
//  This can be used to test security scaning in GitHub    
//    @GetMapping("/echo")
//    public String hello(@RequestParam(value = "message", defaultValue = "ECHO Echoo echooo echoooo ooo") String message) {
//      return message;
//    }
}
