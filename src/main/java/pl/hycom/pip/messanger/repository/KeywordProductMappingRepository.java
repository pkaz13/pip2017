package pl.hycom.pip.messanger.repository;

import org.springframework.data.repository.CrudRepository;
import pl.hycom.pip.messanger.model.KeywordProductMapping;
import pl.hycom.pip.messanger.model.KeywordProductMappingId;

/**
 * Created by patry on 18/03/2017.
 */
public interface KeywordProductMappingRepository extends CrudRepository<KeywordProductMapping, KeywordProductMappingId> {
}
