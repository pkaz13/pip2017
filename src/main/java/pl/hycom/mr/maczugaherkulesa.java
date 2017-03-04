package pl.hycom.mr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Piotr on 2017-03-03.
 */
@Controller
public class maczugaherkulesa {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "maczuga herkulesa  !";
    }
}
