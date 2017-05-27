package pl.hycom.pip.messanger.repository.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by maciek on 23.05.17.
 */
@Data
@Entity
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String authority;

    public Role() {}

    public Role(String roleName) { authority = roleName; }

}
