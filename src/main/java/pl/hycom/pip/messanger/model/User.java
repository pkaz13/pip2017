package pl.hycom.pip.messanger.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Monia on 2017-05-20.
 */
@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 40)
    @Size(min = 3, max = 40)
    private String firstname;

    @NotNull
    @Column(length = 40)
    @Size(min = 3, max = 40)
    private String lastname;

    @NotNull
    @Column(length = 40)
    @Size(min = 6, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Column(length = 64)
    @Size(min = 8, max = 64)
    private String password;

}