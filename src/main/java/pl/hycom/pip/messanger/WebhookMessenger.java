package pl.hycom.pip.messanger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Controller
public class WebhookMessenger
{
    @RequestMapping(value = "/webhook", method = GET)
    @ResponseBody
    String verify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("verifing");
        String verifyToken = request.getParameter("hub.verify_token");
        String mode = request.getParameter("hub.mode");
        String response_msg=request.getParameter("hub.challenge");
        if(verifyToken==null||mode==null|| response_msg==null) {
            response.setStatus(403);
            return "Brak parametru !!!";
        }
        if(mode.equals("subscribe") && verifyToken.equals("token")) {
            response.setStatus(200);
            return response_msg;
        }
        else {
            response.setStatus(403);
            return "Failed validation. Make sure the validation tokens match.";
        }
    }

    @RequestMapping(value = "/webhook", method = POST)
    @ResponseBody
    String respond(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("responding");
        return "Hello World";
    }


}
