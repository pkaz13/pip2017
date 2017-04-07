package pl.hycom.pip.messanger.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.hycom.pip.messanger.model.Keyword;

/**
 * Created by patry on 18/03/2017.
 */
public interface KeywordRepository extends CrudRepository<Keyword, Integer> {

    List<Keyword> findByWordIgnoreCaseStartingWith(String keyword);

    Keyword findByWord(String keyword);
}
