package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hycom.pip.messanger.model.PasswordResetToken;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.service.EmailService;
import pl.hycom.pip.messanger.service.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Piotr on 21.05.2017.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class ResetController {

    private static final String FORGET_VIEW = "forgetPassword";

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/reset/forgetPassword")
    public String getForgetPasswordView() {
        return FORGET_VIEW;
    }

    @RequestMapping("/send")
    public String sendEmail(HttpServletRequest request) throws MalformedURLException {
        String mail = request.getParameter("email");

        User user = userService.findUserByEmail(mail);

        if(user == null) {
            return "redirect: /reset/forgetPassword";
        }

        String token = userService.generateToken();
        userService.createPasswordResetTokenForUser(user, token);
        emailService.sendEmail(emailService.constructResetTokenEmail(getURLBase(request), user, token));
        return "redirect: /login";
    }

    @GetMapping("/changePassword/token/{value}")
    public String getChangePasswordView(@PathVariable("value") final String token, Model model) {
        PasswordResetToken resetToken = userService.getTokenByToken(token);
        model.addAttribute("resetToken", resetToken);

        return "changePassword";
    }

    @PostMapping(value = "/savePassword")
    public String changePassword(HttpServletRequest request) {
        String userMail = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");

        User user = userService.findUserByEmail(userMail);
        if(user.getEmail() == "messenger.recommendations2017@gmail.com") {
            userService.changePassword(user, newPassword);
        }
        return "redirect: /reset/forgetPassword";
    }

    private String getURLBase(HttpServletRequest request) throws MalformedURLException {

        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    }

}
