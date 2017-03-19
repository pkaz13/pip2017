package pl.hycom.pip.messanger.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import javax.inject.Inject;
<<<<<<< HEAD
import java.util.ArrayList;
=======
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
>>>>>>> 9f87c8da8120b66ddc6e37e49ba391e541aa9b9d
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor(onConstructor=@__(@Inject))
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(Product product) {
        log.info("Invoking of addProduct(product) method from ProductService class");
        productRepository.save(product);
    }

    public Product findProductById(Integer id) {
        log.info("Invoking of findProductById(id) method from ProductService class");
        return productRepository.findOne(id);
    }

    public List<Product> findAllProducts() {
        log.info("Invoking of findAllProducts() method from ProductService class");
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Integer id) {
        log.info("Invoking of deleteProduct(id) method from ProductService class");
        productRepository.delete(id);
    }

    public void updateProductName(Integer id, String newName) {
        log.info("Invoking of updateProductName(id, newName) method from ProductService class");
        Product product = productRepository.findOne(id);
        product.setName(newName);
        productRepository.save(product);
    }

<<<<<<< HEAD
    public List<Product> getFewProducts(int howManyProducts) {
        ArrayList<Product> products = new ArrayList<Product>();
        if (!findAllProducts().isEmpty()) {
            if (howManyProducts <= findAllProducts().size()) {   //jeśli baza ma mniej produktów niż klient chce to zwraca całą listę produktów
                Product temp = new Product();
                for (int i = 0; i < howManyProducts; i++) {
                    if ((temp = findAllProducts().get(new Random().nextInt(findAllProducts().size()))) != null) { 
                        if (!products.isEmpty()) {
                            boolean flag = false;
                            for (i = 0; i< products.size() ; i++) {
                                if (products.get(i).equals(temp)) {
                                    flag=true;
                                }
                            }
                            if ( flag==false) {
                                products.add(temp);
                            }
                            else {
                                i--;
                            }
                        }
                        else {
                            products.add(temp);
                        }
                    }
                    else {
                        i--;
                    }
                }
            }
            else {
                products = (ArrayList<Product>) findAllProducts();
            }
        }

        return products;
        }

    }

=======
    public ArrayList<Product> getFewProducts(int quantity) {
        ArrayList<Product> products = new ArrayList<>();

            for (int i = 0; i < quantity ; i++) {
                products.add(findProductById(new Random().nextInt(findAllProducts().size())));
            }
        return products ;
    }
}
>>>>>>> 9f87c8da8120b66ddc6e37e49ba391e541aa9b9d

