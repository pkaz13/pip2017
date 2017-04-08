package pl.hycom.pip.messanger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class KeywordController {

    private static final String KEYWORDS_VIEW = "keywords";

    private final KeywordService keywordService;
    private final ProductService productService;

    @GetMapping("/admin/keywords")
    public String showProducts(Model model) {
        prepareModel(model, new Keyword());
        return KEYWORDS_VIEW;
    }

    @PostMapping("/admin/keywords")
    public String addOrUpdateKeyword(@Valid Keyword keyword, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            prepareModel(model, keyword);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            log.info("Validation keyword error");

            return KEYWORDS_VIEW;
        }

        keywordService.addOrUpdateKeyword(keyword);

        return "redirect:/admin/keywords";
    }

    @GetMapping("/admin/keywords/{keywordId}/delete")
    public ModelAndView deleteKeyword(@PathVariable("keywordId") final Integer id) {

        Keyword deletedKeyword = keywordService.findKeywordById(id);
        if (productService.findAllProductsContainingAtLeastOneKeyword(Arrays.asList(deletedKeyword)).isEmpty()) {
            log.info("cannot delete keyword = " + deletedKeyword);
        } else {
            keywordService.deleteKeyword(id);
            log.info("Keyword[" + id + "] deleted !!!");
        }

        return new ModelAndView("redirect:/admin/keywords");
    }

    private void prepareModel(Model model, Keyword keyword) {
        List<Keyword> allKeywords = keywordService.findAllKeywords();
        model.addAttribute("keywords", allKeywords);
        model.addAttribute("keywordForm", keyword);
    }

}
