package pl.gienius.biblioteka.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);
    @GetMapping("hello")
    public String index(){
        logger.info("HelloController");
        return "hello";
    }
}
