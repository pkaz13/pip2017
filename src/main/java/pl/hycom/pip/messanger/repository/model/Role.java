package pl.hycom.pip.messanger.repository.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private long id;

    @Column
    private String authority;

}
