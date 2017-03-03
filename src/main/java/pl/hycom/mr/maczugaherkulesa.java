package pl.hycom.mr;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Piotr on 2017-03-03.
 */
public class maczugaherkulesa {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Maczuga Herkulesa !";
    }
}
