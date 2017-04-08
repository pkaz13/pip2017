package pl.hycom.pip.messanger.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.repository.KeywordRepository;

/**
 * Created by patry on 18/03/2017.
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public Keyword addKeyword(Keyword keyword) {
        log.info("addKeyword method from KeywordService class invoked");
        return keywordRepository.save(keyword);
    }

    public Keyword findKeywordById(Integer id) {
        log.info("findKeywordById method from KeywordService class invoked");
        return keywordRepository.findOne(id);
    }

    public List<Keyword> findAllKeywords() {
        log.info("findAllKeywords method from KeywordService class invoked");
        return StreamSupport.stream(keywordRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteKeyword(Integer id) {
        log.info("deleteKeyword method from KeywordService class invoked");
        keywordRepository.delete(id);
    }

    public void updateKeyword(Integer id, String newWord) {
        log.info("updateKeyword method from KeywordService class invoked");
        Keyword keyword = keywordRepository.findOne(id);
        if (findKeywordByWord(newWord) == null) {
            keyword.setWord(newWord);
            log.info("keyword update");

            keywordRepository.save(keyword);
        }
    }

    public void deleteAllKeywords() {
        keywordRepository.deleteAll();
    }

    public List<Keyword> findKeywordsBySearchTerm(String searchTerm) {
        log.info("findKeywordsBySearchTerm method from KeywordService invoked");
        return keywordRepository.findByWordIgnoreCaseStartingWith(searchTerm);
    }

    public Keyword findKeywordByWord(String word) {
        log.info("findKeywordsByWord method from KeywordService invoked");
        return keywordRepository.findByWordIgnoreCase(word);
    }
    
    // // TODO: 2017-04-08  zapisywanie takich samych slow kluczowych o roznej wielkosci liter
    public void addOrUpdateKeyword(Keyword keyword) {

       if (keyword.getId() != null && keyword.getId() != 0) {
            updateKeyword(keyword.getId(), keyword.getWord());
       } else {
            addKeyword(keyword);
       }
    }
}
