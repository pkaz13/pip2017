package pl.hycom.pip.messanger.controller;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

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

        if (keywordService.findKeywordByWord(keyword.getWord()) != null) {
            prepareModel(model, keyword);
            model.addAttribute("error", new ObjectError("keywordExists", "Keyword already exists."));

            return KEYWORDS_VIEW;
        }

        keywordService.addOrUpdateKeyword(keyword);

        return "redirect:/admin/keywords";
    }

    @GetMapping("/admin/keywords/{keywordId}/delete")
    public String deleteKeyword(@PathVariable("keywordId") final Integer id, Model model) {

        Keyword deletedKeyword = keywordService.findKeywordById(id);
        if (productService.findAllProductsContainingAtLeastOneKeyword(Arrays.asList(deletedKeyword)).isEmpty()) {
            keywordService.deleteKeyword(id);
            log.info("Keyword[" + id + "] deleted !!!");
        } else {
            prepareModel(model, new Keyword());
            ObjectError error = new ObjectError("keywordInUsage", "Keyword is bound to product(s).");
            model.addAttribute("error", error);
            log.info("cannot delete keyword = " + deletedKeyword);
            return KEYWORDS_VIEW;
        }

        return "redirect:/admin/keywords";
    }

    private void prepareModel(Model model, Keyword keyword) {
        List<Keyword> allKeywords = keywordService.findAllKeywords();
        model.addAttribute("keywords", allKeywords);
        model.addAttribute("keywordForm", keyword);
    }

}
