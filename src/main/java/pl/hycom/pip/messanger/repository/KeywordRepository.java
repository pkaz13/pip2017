package pl.hycom.pip.messanger.repository;

import org.springframework.data.repository.CrudRepository;
import pl.hycom.pip.messanger.model.Keyword;

/**
 * Created by patry on 18/03/2017.
 */
public interface KeywordRepository extends CrudRepository<Keyword, Integer  > {
}
