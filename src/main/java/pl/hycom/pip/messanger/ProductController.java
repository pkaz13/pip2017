package pl.hycom.pip.messanger;

import pl.hycom.pip.messanger.service.ProductService;
import pl.hycom.pip.messanger.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/products")
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }
}
