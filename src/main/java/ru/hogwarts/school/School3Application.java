package ru.hogwarts.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.hogwarts.school.service.AvatarService;

@SpringBootApplication

public class School3Application {
    private static final Logger LOG = LoggerFactory.getLogger(School3Application.class );
    public static void main(String[] args) {
        LOG.debug("ПОЕХАЛИИИИИ!!!!!!!!!!!");
        SpringApplication.run(School3Application.class, args);
    }

}
