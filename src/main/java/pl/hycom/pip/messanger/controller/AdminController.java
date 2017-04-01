package pl.hycom.pip.messanger.controller;


import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.profile.MessengerProfileClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.hycom.pip.messanger.model.Greeting;
import pl.hycom.pip.messanger.model.GreetingListWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by piotr on 12.03.2017.
 */

@Log4j2
@Controller
public class AdminController {

    @Autowired
    private MessengerProfileClient profileClient;

    @RequestMapping(value = "/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin", "showBackLink", false);
    }

    @GetMapping("/admin/greetings")
    public String getGreetings(Model model) {
        List<com.github.messenger4j.profile.Greeting> greetings = getGreetingsWithDefaultLocale(profileClient);
        sortByLocale(greetings);

        GreetingListWrapper greetingListWrapper = new GreetingListWrapper(greetings);
        model.addAttribute("greetingListWrapper", greetingListWrapper);
        model.addAttribute("greeting", new Greeting());
        return "greetings";
    }

    @PostMapping("/admin/greetings")
    public String addGreetings(@ModelAttribute GreetingListWrapper greetingListWrapper) {
        try {
            profileClient.setupWelcomeMessages(greetingListWrapper.extractGreetings());
            log.info("Greeting text correctly updated");
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during changing greeting message", e);
        }
        return "redirect:/admin/greetings";
    }

    @PostMapping("/admin/greeting")
    public String addGreeting(@ModelAttribute Greeting greeting) {
        try {
            List<com.github.messenger4j.profile.Greeting> greetings = getGreetingsWithDefaultLocale(profileClient);
            com.github.messenger4j.profile.Greeting profileGreeting =
                    new com.github.messenger4j.profile.Greeting(greeting.getText(), greeting.getLocale());
            greetings.add(profileGreeting);
            profileClient.setupWelcomeMessages(greetings);
            log.info("Greeting text correctly updated");
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during changing greeting message", e);
        }
        return "redirect:/admin/greetings";
    }

    //Jest get, bo nie wiedziałem jak odwołać sie do posta/deleta z linka z front-endu
    @GetMapping("/admin/deleteGreeting/{locale}")
    public String removeGreeting(@PathVariable String locale) {
        if (StringUtils.equals(locale, "default")) {
            //TODO: pokazac komunikat ze nie wolno usuwac default lub zablokować taką opcję
            return "redirect:/admin/greetings";
        }
        try {
            List<com.github.messenger4j.profile.Greeting> greetings = getGreetingsWithDefaultLocale(profileClient);
            greetings.removeIf(g -> StringUtils.equals(g.getLocale(), locale));
            profileClient.removeWelcomeMessage();
            profileClient.setupWelcomeMessages(greetings);
            log.info("Deleting greeting succeeded");
        } catch (MessengerApiException | MessengerIOException e) {
            log.info("Deleting greeting failed", e);
        }
        return "redirect:/admin/greetings";
    }

    private List<com.github.messenger4j.profile.Greeting> getGreetingsWithDefaultLocale(MessengerProfileClient profileClient) {
        List<com.github.messenger4j.profile.Greeting> greetings = getGreetings(profileClient);
        injectDefaultLocale(greetings);
        return greetings;
    }

    private List<com.github.messenger4j.profile.Greeting> getGreetings(MessengerProfileClient profileClient) {
        try {
            return new ArrayList<>(profileClient.getWelcomeMessage().getGreetings());
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during getting greeting text from facebook", e);
            return Collections.emptyList();
        }
    }

    private void injectDefaultLocale(List<com.github.messenger4j.profile.Greeting> greetings) {
        if (!containsLocale(greetings, "default")) {
            greetings.add(new com.github.messenger4j.profile.Greeting("Hello", "default"));
        }
    }

    private void sortByLocale(List<com.github.messenger4j.profile.Greeting> greetings) {
        greetings.sort((g1, g2) -> StringUtils.compare(g1.getLocale(), g2.getLocale()));
    }

    private boolean containsLocale(List<com.github.messenger4j.profile.Greeting> greetings, String locale) {
        return greetings.stream().anyMatch(g -> StringUtils.equals(g.getLocale(), locale));
    }
}
