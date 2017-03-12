package pl.hycom.pip.messanger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 12.03.2017.
 */

@Controller
public class AdminController {

    //returns view Home.html
    @RequestMapping(value = "/admin/home")
    public String Home()
    {
        return "Home";
    }

}
