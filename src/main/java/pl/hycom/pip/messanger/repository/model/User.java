package pl.hycom.pip.messanger.repository.model;

import lombok.Data;
import lombok.NonNull;

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
    @Pattern(regexp = "^[a-zA-ZĘÓĄŚŁŹŻŃĆęóąśźżćńł]{3,40}$")
    private String firstname;

    @NotNull
    @Column(length = 40)
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-ZĘÓĄŚŁŹŻŃĆęóąśźżćńł]{3,40}$")
    private String lastname;

    @NotNull
    @Column(length = 40, unique = true)
    @Size(min = 6, max = 40)
    @Pattern(regexp = "^[a-z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-z0-9.-]+$")
    private String email;

    @Column(length = 64)
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    @Column
    @Pattern(regexp = "^(\\+48)[5-9][0-9]{8}$")
    private String phoneNumber;

    @Column
    private String profileImageUrl;
}
