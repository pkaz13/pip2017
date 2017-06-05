package pl.hycom.pip.messanger.controller;

import PolishNLP.Analysis;
import PolishNLP.Result;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hycom.pip.messanger.service.ResultService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafal Lebioda on 25.05.2017.
 */
@Log4j2
@Controller
public class NLPController {

    private final static String filePath = "C:\\NLP_pl\\demo.txt";

    private static String NLP_VIEW = "nlp";
    private static  String NLP_RESULT = "nlpResult";

        @RequestMapping(value = "/admin/nlp" , method = RequestMethod.GET)
        public String returnView(Model model) {
            String cos = new String();
            model.addAttribute("tekstDoAnalizy", cos);

        return NLP_VIEW;

        }

    @RequestMapping(value = "/admin/nlp" , method = RequestMethod.POST)
    public String metoda(Model model , @RequestParam String txtInput) throws IOException, InterruptedException, JSONException {
        PrintWriter out = new PrintWriter(filePath);
        out.println(txtInput);
        out.close();
        System.out.println(txtInput);
        Analysis.analize();

        return NLP_VIEW;

    }

    @RequestMapping(value = "/admin/nlpResult" , method = RequestMethod.GET)
    public String result(Model model) {

        List<Result> lista  = new ArrayList<Result>();
            model.addAttribute("lista" , ResultService.getResults());
            return NLP_RESULT;

    }


    }







