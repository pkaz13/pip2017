package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.repository.KeywordRepository;
import pl.hycom.pip.messanger.service.KeywordService;
import static org.junit.Assert.assertEquals;


/**
 * Created by Piotr on 20.03.2017.
 */
@RunWith(SpringRunner.class)
/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
@Log4j2
public class KeywordServiceTest {

    @Mock
    public KeywordRepository keywordRepository;

    @InjectMocks
    public KeywordService keywordService;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        /*Keyword keyword1 = new Keyword();
        keyword1.setId(1);
        keyword1.setWord("test1");

        Keyword keyword2 = new Keyword();
        keyword2.setId(2);
        keyword2.setWord("test2");

        keywordService.addKeyword(keyword1);
        keywordService.addKeyword(keyword2);*/
    }

    @Test
    public void addKeywordTest(){

        Keyword keyword1 = new Keyword();
        keyword1.setId(1);
        keyword1.setWord("test");

        keywordService.addKeyword(keyword1);
        //when(keywordRepository.count()).thenReturn(1l);

        assertEquals(1, keywordService.findAllKeywords().size());

    }

}
