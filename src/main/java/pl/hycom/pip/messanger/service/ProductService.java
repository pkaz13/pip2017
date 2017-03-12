package pl.hycom.pip.messanger.service;

import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product findProductById(Integer id) {
        return productRepository.findOne(id);
    }

    public List<Product> findAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Integer id) {
        productRepository.delete(id);
    }
}

