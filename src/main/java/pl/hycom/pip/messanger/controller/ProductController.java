package pl.hycom.pip.messanger.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProductController {

    private final ProductService productService;

    @GetMapping("/admin/products")
    public String findAllProducts(Model model) {
        List<Product> allProducts = productService.findAllProducts();
        model.addAttribute("products", allProducts);

        return "products";
    }
}
