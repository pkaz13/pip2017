package pl.hycom.pip.messanger.metamodel;

import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by patry on 01/04/2017.
 */
@StaticMetamodel(Product.class)
public class Product_ {
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, String> imageUrl;
    public static volatile SetAttribute<Product, Keyword> keywords;
}
