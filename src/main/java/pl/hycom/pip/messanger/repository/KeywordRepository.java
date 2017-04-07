package pl.hycom.pip.messanger.repository;

import org.springframework.data.repository.CrudRepository;

import pl.hycom.pip.messanger.model.Keyword;

import java.util.List;

/**
 * Created by patry on 18/03/2017.
 */
public interface KeywordRepository extends CrudRepository<Keyword, Integer> {

    List<Keyword> findByWordIgnoreCaseStartingWith(String keyword);

    Keyword findByWordIgnoreCase(String keyword);
}
