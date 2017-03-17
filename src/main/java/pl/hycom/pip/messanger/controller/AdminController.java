package pl.hycom.pip.messanger.controller;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.setup.MessengerSetupClient;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hycom.pip.messanger.model.Greeting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by piotr on 12.03.2017.
 */

@Log4j2
@Controller
public class AdminController {

    @Autowired
    private MessengerSetupClient setupClient;

    @Value("${messenger.pageAccessToken}") private String pageAccessToken;
    //returns view Home.html
    @RequestMapping(value = "/admin/hello")
    public String Home() {
        return "home";
    }

    @GetMapping("/admin/greeting")
    public String adminGreeting(Model model) {
        Greeting greeting=new Greeting();
        try {
            String url = "https://graph.facebook.com/v2.6/me/messenger_profile?fields=greeting&access_token="+pageAccessToken;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            JSONObject object = new JSONObject(json);
            JSONArray dataArray=object.getJSONArray("data");
            object=dataArray.getJSONObject(0);
            dataArray=object.getJSONArray("greeting");
            object=dataArray.getJSONObject(0);
            String greetingText= (String)object.get("text");
            greeting.setContent(greetingText);
        }
        catch (IOException |JSONException  e) {
            log.error("Error during getting greeting text from facebook"+e);
        }
        model.addAttribute("greeting", greeting);
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
