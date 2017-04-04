package pl.hycom.pip.messanger.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, JpaSpecificationExecutor {


    @Query("Select p from Product p where p not in (:productsForCustomer)")
    public List<Product> findSomeProducts(@Param("productsForCustomer") List<Product> products, Pageable pa);

    @Query("select p from Product p where (:requiredKeyword) member of p.keywords")
    public List<Product> findProductsWithKeyword(@Param("requiredKeyword") Keyword keyword);
}
