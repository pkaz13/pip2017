package pl.hycom.pip.messanger.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    public boolean containsKeywords(String[] keywordValues) {
        for (String keywordValue : keywordValues) {
            if (keywords.stream()
                    .filter(keyword -> keyword.getWord().equals(keywordValue))
                    .count() == 0) {
                return false;
            }
        }
        return true;
    }
}
