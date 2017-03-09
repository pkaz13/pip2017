package pl.hycom.pip.messanger;

import pl.hycom.pip.messanger.services.ProductService;
import pl.hycom.pip.messanger.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Product>> findAllProducts() {
        return new ResponseEntity<> (productService.findAllProducts(), HttpStatus.OK);
    }
}
