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
        String verifyToken = request.getParameter("verify_token");
        String mode = request.getParameter("mode");
        String response=request.getParameter("challenge");
        if(mode.equals("Subscribe") && verifyToken.equals("token"))
        {
            return response;
        }
        else
        {
            return "Failed validation. Make sure the validation tokens match.";
        }
    }
}
