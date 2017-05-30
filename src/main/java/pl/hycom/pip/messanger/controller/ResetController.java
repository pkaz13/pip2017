package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.hycom.pip.messanger.controller.model.UserDTO;
import pl.hycom.pip.messanger.model.PasswordResetToken;
import pl.hycom.pip.messanger.repository.model.User;
import pl.hycom.pip.messanger.service.EmailService;
import pl.hycom.pip.messanger.service.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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

    @GetMapping("/reset/forget/password")
    public String getForgetPasswordView() {
        return FORGET_VIEW;
    }

    @PostMapping("/send")
    public String sendEmail(HttpServletRequest request, Model model) throws MalformedURLException {
        String mail = request.getParameter("email");

        if (mail == null) {
            model.addAttribute("error", new ObjectError("mailIsEmpty", "You need to write your email, dumbass."));
            return "redirect:/reset/forget/password";
        }
        User user = userService.findUserByEmail(mail);

        if(user == null) {
            model.addAttribute("info", new ObjectError("sendOrNotSend", "If user exists, mail was sent."));
            return "redirect:/reset/forget/password";
        }

        String token = userService.generateToken();
        userService.createPasswordResetTokenForUser(user, token);
        emailService.sendEmail(emailService.constructResetTokenEmail(getURLBase(request), user, token));
        model.addAttribute("info", new ObjectError("sendOrNotSend", "If user exists, mail was sent."));
        return "redirect:/login";
    }

    @GetMapping("/change/password/token/{token}")
    public String getChangePasswordView(@PathVariable("token") final String token, Model model) {
        model.addAttribute("resetToken", userService.getTokenByToken(token));
        return "changePassword";
    }

    @PostMapping(value = "/change/password")
    public String changePassword(@Valid PasswordResetToken token, BindingResult bindingResult, Model model,  HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("token", token);
            log.info("Reset token validation error." + bindingResult.getAllErrors());
            return "changePassword";
        }

        String userMail = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");

        User user = userService.findUserByEmail(userMail);
        PasswordResetToken resetToken = userService.getTokenByToken(token.getToken());

        if (token == null) {
            log.info("token is null");
            return "redirect:/reset/forget/password";
        }

        if (userService.validatePasswordResetToken(token.getToken(), userMail)) {
                userService.changePassword(user, newPassword);
                log.info("User's password changed");
                return "redirect:/login";
        }
        log.info("Failed to change user's password");
        return "redirect:/reset/forget/password";
    }

    private String getURLBase(HttpServletRequest request) throws MalformedURLException {

        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    }
}
