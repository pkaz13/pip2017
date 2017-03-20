package pl.hycom.pip.messanger.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    private static final long serialVersionUID = 9211285852881742074L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String imageUrl;

    @OneToMany(
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.REFRESH
            },
            fetch = FetchType.EAGER
    )
    @OrderColumn
    private Set<Keyword> keywords;
}
