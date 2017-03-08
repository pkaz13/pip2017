package pl.hycom.pip.messanger.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Integer id;

    @Column(name = "name", nullable = false)
    @Getter @Setter private String name;

    @Column(name = "description", nullable = false)
    @Getter @Setter private String description;

    @Column(name = "imageUrl", nullable = false)
    @Getter @Setter private String imageUrl;
}
