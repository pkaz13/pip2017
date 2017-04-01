package pl.hycom.pip.messanger.controller;


import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping("/admin/products")
    public String findAllProducts(Model model) {
        List<Product> allProducts = productService.findAllProducts();
        model.addAttribute("products", allProducts);
        model.addAttribute("productForm",new Product());
        return "products";
    }

    @PostMapping("/admin/products")
    public String productsSubmit(@Valid Product product, BindingResult bindingResult,Model model ) {
        try {
            if (bindingResult.hasErrors()) {
                List<Product> allProducts = productService.findAllProducts();
                model.addAttribute("products", allProducts);
                model.addAttribute("productForm",product);
                List<FieldError> errors=bindingResult.getFieldErrors();
                model.addAttribute("errors",errors);
                log.info("Validation product error !!!");
                return "products";
            }
            if (product.getId() != 0) {
                productService.updateProduct(product.getId(),product.getName(),product.getDescription(),product.getImageUrl());
                log.info("Product updated !!!");
            }
            else
            {
                productService.addProduct(product);
                log.info("Product added !!!");
            }
        }
        catch (Exception ex){
            log.error("Error during post request from admin/products"+ex);
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete")
    public ModelAndView deleteProduct(@RequestParam(value = "productId") final int id){
        productService.deleteProduct(id);
        log.info("Product deleted !!!");
        return new ModelAndView("redirect:/admin/products");
    }
}
