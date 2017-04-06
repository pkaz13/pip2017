package pl.hycom.pip.messanger.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repository.ProductRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;

    @Value("${messenger.recommendation.products-amount}")
    private Integer numberOfProducts;

    public Product addProduct(Product product) {
        log.info("Adding product: " + product);

        return productRepository.save(product);
    }

    public Product findProductById(Integer id) {
        log.info("Searching for product with id[" + id + "]");

        return productRepository.findOne(id);
    }

    public List<Product> findAllProducts() {
        log.info("Searching all products");

        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Integer id) {
        log.info("Deleting product[" + id + "]");

        productRepository.delete(id);
    }

    public Product updateProduct(Product product) {
        log.info("Updating product: " + product);

        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setImageUrl(product.getImageUrl());
        return productRepository.save(updatedProduct);
    }

    public Product addOrUpdateProduct(Product product) {
        if (product.getId() != null && product.getId() != 0) {
            Product updatedProduct = updateProduct(product);
            log.info("Product updated !!!");
            return updatedProduct;
        }

        Product addedProduct = addProduct(product);
        log.info("Product added !!!");
        return addedProduct;
    }

    public List<Product> getRandomProducts(int howManyProducts) {
        log.info("Searching for [" + howManyProducts + "] random products");

        List<Product> products = new ArrayList<>(howManyProducts);
        int quantity = (int) productRepository.count();
        if (quantity == 0 || howManyProducts > quantity) {
            products.addAll(findAllProducts());
            return products;
        }

        for (int i = 0; i < howManyProducts; i++) {
            PageRequest pr = new PageRequest(new Random().nextInt(quantity - products.size()), 1);
            products.addAll(productRepository.findSomeProducts(products, pr));
        }

        return products;
    }

    public List<Product> findAllProductsContainingAtLeastOneKeyword(Keyword... keywords) {
        return Arrays.stream(Optional.ofNullable(keywords).orElse(new Keyword[] {})).filter(Objects::nonNull)
                .flatMap(k -> productRepository.findProductsWithKeyword(k).stream()).filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }

    public Product addKeywordsToProduct(Integer id, Keyword... keywords) {
        Product product = findProductById(id);
        product.getKeywords().addAll(Arrays.asList(keywords));
        return productRepository.save(product);
    }

    public Product removeKeywordsFromProduct(Integer id, Keyword... keywords) {
        Product product = findProductById(id);
        product.getKeywords().removeAll(Arrays.asList(keywords));
        return productRepository.save(product);
    }

    public List<Product> findBestFittingProducts(Keyword ... keywords) {
        log.info("Finding best fitting products");
        //TODO: move this constant to some properties
        //final int numberOfProducts = 3;

        List<Product> productsWithKeywords = findAllProductsContainingAtLeastOneKeyword(keywords);
        PriorityQueue<Map.Entry<Product, Integer>> productsQueue =
                new PriorityQueue<>((o1, o2) -> o2.getValue() - o1.getValue());
        for (Product product : productsWithKeywords) {
            Map.Entry<Product, Integer> queueEntry = new HashMap.SimpleEntry<>(product, 0);
            for (Keyword keyword : keywords) {
                if (product.containsKeyword(keyword)) {
                    queueEntry.setValue(queueEntry.getValue() + 1);
                }
            }
            productsQueue.add(queueEntry);
        }
        List<Product> bestFittingProducts = new ArrayList<>();
        for (int i = 0; i < numberOfProducts && !productsQueue.isEmpty(); ++i) {
            bestFittingProducts.add(productsQueue.poll().getKey());
        }
        return bestFittingProducts;
    }

    public void deleteAllProducts() {
        log.info("Deleting all products");

        productRepository.deleteAll();
    }

    public int count() {
        return findAllProducts().size();
    }
}
