package pl.hycom.pip.messanger.services;

import jersey.repackaged.com.google.common.collect.Lists;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return Lists.newArrayList(productRepository.findAll());
    }
}
