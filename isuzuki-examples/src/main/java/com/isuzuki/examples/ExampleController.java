package com.isuzuki.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @GetMapping("/api/v1/examples")
    public ResponseEntity<String> examples() {
        logger.info("examples");
        return ResponseEntity.ok("Hello World");
    }

}
