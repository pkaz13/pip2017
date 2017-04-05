package pl.hycom.pip.messanger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

/**
 * Created by piotr on 12.03.2017.
 */

@Log4j2
@Controller
public class AdminController {

    @RequestMapping(value = "/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin", "showBackLink", false);
    }

}
