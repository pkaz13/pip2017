package pl.hycom.pip.messanger.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    private static final long serialVersionUID = 9211285852881742074L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 80)
    @Size(min = 1, max = 80)
    private String name;

    @NotNull
    @Column(length = 80)
    @Size(min = 1, max = 80)
    private String description;

    @NotNull
    private String imageUrl;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    private Set<Keyword> keywords = new HashSet<>();

    public boolean containsKeyword(Keyword keyword) {
        return keywords.contains(keyword);
    }

    public boolean addKeyword(Keyword keyword) {
        return keywords.add(keyword);
    }
}
