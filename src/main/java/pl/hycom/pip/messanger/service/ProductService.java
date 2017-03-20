package pl.hycom.pip.messanger.service;

import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor(onConstructor=@__(@Inject))
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager em;

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

    public List<Product> getFewProducts(int howManyProducts) {
        List<Product> products = new ArrayList<>(howManyProducts);
        int quantity = (int) productRepository.count();
        if (quantity == 0 || howManyProducts > quantity) {
            products.addAll(findAllProducts());
            return products;
        } else {
            for (int i = 0; i < howManyProducts; i++) {

                products.addAll(em.createQuery("Select p from Product p where p not in (:productsForCustomer)").setParameter("productsForCustomer", products).setFirstResult(em.createQuery("SELECT count(p) from Product p where p not in (:ps)").setParameter("ps", products).getFirstResult()).setMaxResults(1).getResultList());
            }
        }
        return products;
    }



    }


