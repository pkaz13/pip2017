package pl.hycom.pip.messanger.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rafal Lebioda on 24.04.2017.
 */

@Data
@NoArgsConstructor
public class Product {
    private Integer id;

    @Size(min = 1, max = 80)
    private String name;

    @Size(min = 1, max = 80)
    private String description;

    private String imageUrl;

    private Set<Keyword> keywords = new HashSet<>();

    public Product(pl.hycom.pip.messanger.repository.model.Product product) {
        id=product.getId();
        name=product.getName();
        description=product.getDescription();
        imageUrl=product.getImageUrl();
        for(pl.hycom.pip.messanger.repository.model.Keyword keyword :  product.getKeywords()){
            keywords.add(new Keyword(keyword));
        }
    }
}
