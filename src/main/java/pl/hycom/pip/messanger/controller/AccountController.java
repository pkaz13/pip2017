package pl.hycom.pip.messanger.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.service.UserService;

import javax.validation.Valid;

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
    public String showAccount(Model model, @AuthenticationPrincipal User caller) {
        //TO DO : zrobic pobieranie aktualnego uzytkownika !!!
        User user=userService.findAllUsers().get(0);
        model.addAttribute("user", user);
        return ACCOUNT_VIEW;
    }

    @PostMapping("/admin/account")
    public String updateAccount(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            log.info("Validation user account error !!!");
            return ACCOUNT_VIEW;
        }
        else {
            userService.updateUser(user);
            return "redirect:/admin/account";
        }
    }

}
