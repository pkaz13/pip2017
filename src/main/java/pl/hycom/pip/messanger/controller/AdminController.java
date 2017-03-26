package pl.hycom.pip.messanger.controller;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.profile.MessengerProfileClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/admin/greeting")
    public String adminGreeting(Model model) {
        List<com.github.messenger4j.profile.Greeting> greetings = getGreetings(profileClient);
        injectMissingGreetings(greetings);
        sortByLocale(greetings);

        GreetingListWrapper greetingListWrapper = new GreetingListWrapper(greetings);
        model.addAttribute("greetingListWrapper", greetingListWrapper);
        return "greeting";
    }

    @PostMapping("/admin/greeting")
    public String greetingSubmit(@ModelAttribute GreetingListWrapper greetingListWrapper) {
        try {
            profileClient.setupWelcomeMessages(greetingListWrapper.extractGreetings());
            log.info("Greeting text correctly updated");
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during changing greeting message", e);
        }
        return "greeting";
    }

    private List<com.github.messenger4j.profile.Greeting> getGreetings(MessengerProfileClient profileClient) {
        try {
            return new ArrayList<>(profileClient.getWelcomeMessage().getGreetings());
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during getting greeting text from facebook", e);
            return Collections.emptyList();
        }
    }

    private void injectMissingGreetings(List<com.github.messenger4j.profile.Greeting> greetings) {
        if (!containsLocale(greetings, "default")) {
            greetings.add(new com.github.messenger4j.profile.Greeting("Hello", "default"));
        }
        if (!containsLocale(greetings, "en_GB")) {
            greetings.add(new com.github.messenger4j.profile.Greeting("Hi", "en_GB"));
        }
        if (!containsLocale(greetings, "pl_PL")) {
            greetings.add(new com.github.messenger4j.profile.Greeting("Witaj", "pl_PL"));
        }
    }

    private void sortByLocale(List<com.github.messenger4j.profile.Greeting> greetings) {
        greetings.sort((g1, g2) -> StringUtils.compare(g1.getLocale(), g2.getLocale()));
    }

    private boolean containsLocale(List<com.github.messenger4j.profile.Greeting> greetings, String locale) {
        return greetings.stream().anyMatch(g -> StringUtils.equals(g.getLocale(), locale));
    }
}
