package pl.hycom.pip.messanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by maciek on 06.03.17.
 */

@Controller
public class RootController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/admin/home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
