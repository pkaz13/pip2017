package pl.hycom.pip.messanger.repository;

import org.springframework.stereotype.Repository;
import pl.hycom.pip.messanger.model.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByName(String name);
}
