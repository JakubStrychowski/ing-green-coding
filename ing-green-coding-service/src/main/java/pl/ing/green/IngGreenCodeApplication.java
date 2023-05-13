/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package pl.ing.green;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jakub Strychowski
 */
@SpringBootApplication
@RestController
public class IngGreenCodeApplication {

    
    public static void main(String[] args) {
      SpringApplication.run(IngGreenCodeApplication.class, args);
    }
    
    @GetMapping("/echo")
    public String hello(@RequestParam(value = "message", defaultValue = "ECHO Echoo echooo echoooo ooo") String message) {
      return message;
    }
    
    
}
