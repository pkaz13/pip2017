package pl.hycom.pip.messanger.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by patry on 18/03/2017.
 */

@Data
@Entity
@Table(name = "KEYWORDS")
public class Keyword implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String word;
}
