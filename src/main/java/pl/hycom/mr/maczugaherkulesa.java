package pl.hycom.mr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class maczugaherkulesa {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello"
    };

}
