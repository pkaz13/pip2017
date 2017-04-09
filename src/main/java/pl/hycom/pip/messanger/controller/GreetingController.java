package pl.hycom.pip.messanger.controller;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.profile.MessengerProfileClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.hycom.pip.messanger.model.Greeting;
import pl.hycom.pip.messanger.model.GreetingListWrapper;
import pl.hycom.pip.messanger.service.GreetingService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by piotr on 12.03.2017.
 *
 */

@Log4j2
@Controller
public class GreetingController {

    private static final String ADMIN_GREETINGS = "/admin/greetings";
    private static final String REDIRECT_ADMIN_GREETINGS = "redirect:" + ADMIN_GREETINGS;

    @Autowired
    private MessengerProfileClient profileClient;

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private MessageSource bundleMessageSource;

    @GetMapping(ADMIN_GREETINGS)
    public String getGreetings(Model model) {
        prepareModel(model);
        return "greetings";
    }

    @PostMapping(ADMIN_GREETINGS)
    public String addGreetings(@Valid GreetingListWrapper greetingListWrapper, BindingResult bindingResult, Model model) {
        try {
            profileClient.setupWelcomeMessages(greetingListWrapper.extractGreetings());
            log.info("Greeting text correctly updated");
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during changing greeting message", e);
        }
        return REDIRECT_ADMIN_GREETINGS;
    }

    @PostMapping("/admin/greeting")
    public String addGreeting(@Valid Greeting greeting, BindingResult bindingResult, Model model) {
        try {
            if (!greetingService.isValidLocale(greeting.getLocale())) {
                log.error("Not supported locale[" + greeting.getLocale() + "]");
                addError(bindingResult, "greeting.locale.empty");
            }

            if (bindingResult.hasErrors()) {
                prepareModel(model, greeting);
                model.addAttribute("errors", bindingResult.getFieldErrors());
                log.error("Greeting validation errors: " + bindingResult.getAllErrors());
                return "greetings";
            }

            List<com.github.messenger4j.profile.Greeting> greetings = getGreetingsWithDefaultLocale(profileClient);
            com.github.messenger4j.profile.Greeting profileGreeting = new com.github.messenger4j.profile.Greeting(greeting.getText(), greeting.getLocale());
            greetings.add(profileGreeting);
            profileClient.setupWelcomeMessages(greetings);
            log.info("Greeting text correctly updated");
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during changing greeting message", e);
            addError(bindingResult, "unexpectedError");
            prepareModel(model, greeting);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "greetings";
        }

        return REDIRECT_ADMIN_GREETINGS;
    }

    // Jest get, bo nie wiedziałem jak odwołać sie do posta/deleta z linka z front-endu
    @GetMapping("/admin/deleteGreeting/{locale}")
    public String removeGreeting(@PathVariable String locale) {
        if (StringUtils.equals(locale, "default")) {
            /*addError(bindingResult, "greetings.invalidOperation");
            prepareModel(model);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            log.error("Greetings validation errors: "+bindingResult.getAllErrors());
            return "greetings";*/
            return REDIRECT_ADMIN_GREETINGS;
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
        return REDIRECT_ADMIN_GREETINGS;
    }

    private void prepareModel(Model model, Greeting greeting, GreetingListWrapper greetingListWrapper, List<com.github.messenger4j.profile.Greeting> greetings) {
        model.addAttribute("greetingListWrapper", greetingListWrapper);
        model.addAttribute("availableLocale", greetingService.getAvailableLocale(greetings));
        model.addAttribute("greeting", greeting);
    }

    private void prepareModel(Model model) {
        prepareModel(model, new Greeting());
    }

    private void prepareModel(Model model, Greeting greeting) {
        List<com.github.messenger4j.profile.Greeting> greetings = getGreetingsWithDefaultLocale(profileClient);
        sortByLocale(greetings);
        GreetingListWrapper greetingListWrapper = new GreetingListWrapper(greetings);
        prepareModel(model, greeting, greetingListWrapper, greetings);
    }

    private void prepareModel(Model model, GreetingListWrapper greetingListWrapper) {
        List<com.github.messenger4j.profile.Greeting> greetings = getGreetingsWithDefaultLocale(profileClient);
        sortByLocale(greetings);
        prepareModel(model, new Greeting(), greetingListWrapper, greetings);
    }

    private void addError(BindingResult bindingResult, String objectName, String messageCode, Object... args) {
        bindingResult.addError(new ObjectError(objectName, bundleMessageSource.getMessage(messageCode, args, "Unsupported operation", LocaleContextHolder.getLocale())));
    }

    private void addError(BindingResult bindingResult, String messageCode, Object... args) {
        addError(bindingResult, "all", messageCode, args);
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
