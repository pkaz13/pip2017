package pl.hycom.pip.messanger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rafal Lebioda on 02.03.2017.
 */
@Controller
public class WebhookMessenger
{
    @RequestMapping("/webhook")
    @ResponseBody
    String verify(HttpServletRequest request)
    {
        String verifyToken = request.getParameter("hub.verify_token");
        String mode = request.getParameter("hub.mode");
        String response=request.getParameter("hub.challenge ");
        if(mode=="Subscribe" && verifyToken=="token")
        {
            return response;
        }
        else
        {
            return "Failed validation. Make sure the validation tokens match.";
        }
    }
}
