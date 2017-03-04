package pl.hycom.pip.messanger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Controller
public class WebhookMessenger
{
//    @RequestMapping(value = "/webhook", method = GET)
//    @ResponseBody
//    String verify(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("verifing");
//        String verifyToken = request.getParameter("hub.verify_token");
//        String mode = request.getParameter("hub.mode");
//        String response_msg=request.getParameter("hub.challenge");
//        if(verifyToken==null||mode==null|| response_msg==null) {
//            response.setStatus(403);
//            return "Brak parametru !!!";
//        }
//        if(mode.equals("subscribe") && verifyToken.equals("token")) {
//            response.setStatus(200);
//            return response_msg;
//        }
//        else {
//            response.setStatus(403);
//            return "Failed validation. Make sure the validation tokens match.";
//        }
//    }

    @RequestMapping(value = "/webhook", method = GET, produces = MediaType.TEXT_PLAIN)
    public String verify(@RequestParam("hub.verify_token") final String verifyToken,
                         @RequestParam("hub.mode") final String mode,
                         @RequestParam("hub.challenge") final String challenge) {
        if (verifyToken.equals("token") && mode.equals("subscribe")) {
            return challenge;
        } else {
            return "Failed validation. Make sure the validation tokens match.";
        }
    }

    @RequestMapping(value = "/webhook", method = POST, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public void sendMessage(@RequestBody final String body) {
        System.out.println("Request Body:");
        System.out.println(body);
    }
}
