package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.repository.KeywordRepository;
import pl.hycom.pip.messanger.service.KeywordService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by Piotr on 20.03.2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@ActiveProfiles({"dev", "kazmierczak", "testdb"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Log4j2
public class KeywordServiceTest {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private KeywordRepository repository;

    private Keyword keyword;
    private Keyword keyword1;
    private Keyword keyword2;

    @Before
    public void setUp(){

        keyword = new Keyword();
        keyword.setWord("test");

        keyword1 = new Keyword();   //always in repository before test
        keyword1.setWord("test1");

        keyword2 = new Keyword();   //always in repository before test
        keyword2.setWord("test2");

        repository.save(keyword1);
        repository.save(keyword2);
    }

    @Test
    public void addUniqueKeywordTest(){

         //act
        keywordService.addKeyword(keyword);

        //assert
        assertEquals(3, keywordService.findAllKeywords().size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addNonUniqueKeywordTest() {
        //preperation
        Keyword keyword = new Keyword();
        keyword.setWord("test1");

        //act
        keywordService.addKeyword(keyword);
    }

    @Test
    public void findKeywordByIdTest() {
        //act
        repository.save(keyword);

        //assert
        assertNotNull(keywordService.findKeywordById(keyword.getId()));
        assertEquals("test", keywordService.findKeywordById(keyword.getId()).getWord());
    }

    @Test
    public void findAllKeyWordsTest() {

        //preperation
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(keyword1);
        keywords.add(keyword2);
        List<Keyword> keywordsFromRepository;

        //act
        keywordsFromRepository = keywordService.findAllKeywords();

        //assert
        assertEquals(keywords.size(), keywordsFromRepository.size());
    }

    @Test
    public void updateKeywordTest(){

        //preperation
        String newWord = "newWord";

        //act
        keywordService.updateKeyword(keyword1.getId(),newWord);

        //assert
        assertEquals(newWord, repository.findOne(keyword1.getId()).getWord());
    }

    @Test
    public void deleteKeywordTest(){

        //preperation
        long numberOfKeywordsBeforeAddingKeyword = repository.count();
        repository.save(keyword);
        long numberOfKeywordsAfterAddingKeyword = repository.count();

        //act
        keywordService.deleteKeyword(keyword.getId());
        long numberOfKeywordsAfterDeletingKeyword = repository.count();

        //assert
        assertEquals(2,numberOfKeywordsBeforeAddingKeyword);
        assertEquals(3,numberOfKeywordsAfterAddingKeyword);
        assertEquals(numberOfKeywordsBeforeAddingKeyword,numberOfKeywordsAfterDeletingKeyword);
    }

    @After
    public void cleanUp() {
        repository.deleteAll();
    }

}
