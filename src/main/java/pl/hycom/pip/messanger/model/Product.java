package pl.hycom.pip.messanger.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String imageUrl;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ID")
    private Set<Keyword> keywords;
}
