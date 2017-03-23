package pl.hycom.pip.messanger.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;

@RestController
@RequestMapping(value = "/admin/products")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }
}
