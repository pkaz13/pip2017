package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.service.KeywordService;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class KeywordController {

    private static final String KEYWORDS_VIEW = "keywords";

    private final KeywordService keywordService;

    @GetMapping("/admin/keywords")
    public String showProducts(Model model) {
        prepareModel(model, new Keyword());
        return KEYWORDS_VIEW;
    }

    private void prepareModel(Model model, Keyword keyword) {
        List<Keyword> allKeywords = keywordService.findAllKeywords();
        model.addAttribute("keywords", allKeywords);
    }

}
