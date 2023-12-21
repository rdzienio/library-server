package pl.gienius.biblioteka.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloRestController {

    Logger logger = LoggerFactory.getLogger(HelloRestController.class);

    @GetMapping("/api/hello")
    public String index(){
        logger.info("Log from index");
        return "Hello";
    }

    @GetMapping("/api/look")
    public String widacMnie(){
        logger.info("Log from widac");
        return "Widac mnie?";
    }
}
