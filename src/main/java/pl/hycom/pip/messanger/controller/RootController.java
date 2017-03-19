package pl.hycom.pip.messanger.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

/**
 * Created by maciek on 06.03.17.
 */

@Controller
@Log4j2
public class RootController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/admin";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {

        if (request.isUserInRole("ROLE_ADMIN")) {
            log.info("Redirecting to home");
            return home();
        }

        return "login";
    }

}
