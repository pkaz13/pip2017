package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * Created by Piotr on 21.05.2017.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class ResetController {

    @GetMapping("/reset/forgetPassword")
    public String getResetView() {
        return "forgetPassword";
    }

}
