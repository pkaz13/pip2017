package pl.hycom.pip.messanger.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by patry on 18/03/2017.
 */

@Data
@Entity
@Table(name = "PRODUCTS_KEYWORDS")
@IdClass(KeywordProductMappingId.class)
public class KeywordProductMapping implements Serializable {
    @Id
    private Integer productId;

    @Id
    private Integer keywordId;
}
