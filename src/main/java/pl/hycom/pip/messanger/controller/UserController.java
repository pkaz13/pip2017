package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.hycom.pip.messanger.model.User;
import pl.hycom.pip.messanger.service.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class UserController {

    private static final String USERS_VIEW = "users";

    private final UserService userService;

    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        prepareModel(model, new User());
        return USERS_VIEW;
    }

    @PostMapping("/admin/users")
    public String addOrUpdateUser(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            prepareModel(model, user);
            log.info("Validation user error!");

            return USERS_VIEW;
        }

        if (userService.findUserByEmail(user.getEmail()) != null) {
            prepareModel(model, user);
            model.addAttribute("error", new ObjectError("userExists", "Użytkownik z takim adresem email już istnieje."));

            return USERS_VIEW;
        }

        userService.addOrUpdateUser(user);

        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{userId}/delete")
    public @ResponseBody
    void deleteProduct(@PathVariable("userId") final Integer id) {
        userService.deleteUser(id);
        log.info("User[" + id + "] deleted!");
    }

    private void prepareModel(Model model, User user) {
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("users", allUsers);
        model.addAttribute("userForm", user);
    }
}