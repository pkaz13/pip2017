package pl.hycom.pip.messanger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maciek on 06.03.17.
 */

@Controller
public class HomeController {
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World";
    }
}
