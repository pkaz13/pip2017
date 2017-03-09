package pl.hycom.pip.messanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repositories.ProductRepository;

import java.util.Collection;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Product>> getAllProducts() {
        return new ResponseEntity<>((Collection<Product>) productRepository.findAll(), HttpStatus.OK);
    }
}
