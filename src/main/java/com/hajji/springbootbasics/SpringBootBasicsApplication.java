package com.hajji.springbootbasics;

<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
=======
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca
public class SpringBootBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicsApplication.class, args);
<<<<<<< HEAD
        log.info("✅ Spring Boot Basics Application started successfully!");
=======
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca
    }
}
