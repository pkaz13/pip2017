package pl.hycom.pip.messanger.controller;

import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hycom.pip.nlp.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafal Lebioda on 25.05.2017.
 */
@Log4j2
@Controller
public class NLPController {

    public static List<Result> outputList = new ArrayList<>();


    private static String NLP_VIEW = "nlp";
    private static  String NLP_RESULT = "nlpResult";

        @RequestMapping(value = "/admin/nlp" , method = RequestMethod.GET)
        public String returnView(Model model) {
        return NLP_VIEW;

        }

    @RequestMapping(value = "/admin/nlp" , method = RequestMethod.POST)
    public String metoda(Model model , @RequestParam String txtInput) throws IOException, InterruptedException, JSONException {
         return NLP_VIEW;

    }

    @RequestMapping(value = "/admin/nlpResult" , method = RequestMethod.GET)
    public String result(Model model) {
            model.addAttribute("lista" , outputList);
            return NLP_RESULT;

    }


    }







