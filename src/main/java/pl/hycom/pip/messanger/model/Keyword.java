package pl.hycom.pip.messanger.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Created by patry on 18/03/2017.
 */

@Data
@Entity
@Table(name = "KEYWORDS")
public class Keyword implements Serializable {

    private static final long serialVersionUID = -8450849049742048985L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max= 100)
    private String word;
}
