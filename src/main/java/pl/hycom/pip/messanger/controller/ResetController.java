package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.hycom.pip.messanger.service.EmailService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/reset/forgetPassword")
    public String getForgetPasswordView() {
        return FORGET_VIEW;
    }

    @RequestMapping("/send")
    public String sendEmail(HttpServletRequest request) {
        String mail = request.getParameter("email");
        emailService.sendEmail(emailService.constructEmail(mail,"test", "test"));
        return "redirect:/login";
    }

}
