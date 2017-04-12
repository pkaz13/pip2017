package pl.hycom.pip.messanger.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class ProductController {

    private static final String PRODUCTS_VIEW = "products";

    private final ProductService productService;
    private final KeywordService keywordService;

    @GetMapping("/admin/products")
    public String showProducts(Model model) {
        prepareModel(model, new Product());
        return PRODUCTS_VIEW;
    }

    @PostMapping("/admin/products")
    public String addOrUpdateProduct(@Valid Product product, BindingResult bindingResult, Model model, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            prepareModel(model, product);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            log.info("Validation product error !!!");

            return PRODUCTS_VIEW;
        }

        productService.addOrUpdateProduct(product, StringUtils.split(request.getParameter("keywords-holder"), ','));

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/{productId}/delete")
    public ModelAndView deleteProduct(@PathVariable("productId") final Integer id) {
        productService.deleteProduct(id);
        log.info("Product[" + id + "] deleted !!!");

        return new ModelAndView("redirect:/admin/products");
    }

    @ResponseBody
    @GetMapping("/admin/product/keyword/suggestions")
    public List<String> getKeywordsSuggestions(@RequestParam("searchTerm") String searchTerm) {
        return keywordService.findKeywordsBySearchTerm(searchTerm).stream().map(Keyword::getWord).collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/admin/products/{productId}/keywords")
    public List<String> getProductKeywords(@PathVariable("productId") final Integer id) {
        log.info("Searching for product's [" + id + "] keywords");

        return productService.findProductById(id).getKeywords().stream().map(Keyword::getWord).collect(Collectors.toList());
    }

    private void prepareModel(Model model, Product product) {
        List<Product> allProducts = productService.findAllProducts();
        model.addAttribute("products", allProducts);
        model.addAttribute("productForm", product);
    }

}
