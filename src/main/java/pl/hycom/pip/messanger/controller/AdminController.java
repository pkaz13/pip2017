package pl.hycom.pip.messanger.controller;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.setup.MessengerSetupClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hycom.pip.messanger.model.Greeting;

/**
 * Created by piotr on 12.03.2017.
 */

@Log4j2
@Controller
public class AdminController {

    @Autowired
    private MessengerSetupClient setupClient;

    //returns view Home.html
    @RequestMapping(value = "/admin/hello")
    public String Home() {
        return "home";
    }

    @GetMapping("/admin/greeting")
    public String adminGreeting(Model model){
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/admin/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        try {
            setupClient.setupWelcomeMessage(greeting.getContent());
            log.info("Greeting text correctly updated");
        } catch (MessengerApiException|MessengerIOException e) {
            log.error("Error during changing greeting message",e);
        }
        return "greeting";
    }

}
