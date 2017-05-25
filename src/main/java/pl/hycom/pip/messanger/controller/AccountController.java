package pl.hycom.pip.messanger.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.service.UserService;

/**
 * Created by Rafal Lebioda on 25.05.2017.
 */
@Log4j2
@Controller
public class AccountController {

    private static final String ACCOUNT_VIEW = "account";

    @Autowired
    private UserService userService;

    @GetMapping("/admin/account")
    public String showUsers(Model model) {
        User user=userService.findAllUsers().get(0);
        model.addAttribute("user", user);
        return ACCOUNT_VIEW;
    }
}
