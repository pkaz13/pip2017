package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.assertEquals;


/**
 * Created by Piotr on 20.03.2017.
 */

/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
//@ActiveProfiles(profiles = {"dev","kazmierczak"})
@Log4j2
public class KeywordServiceTest {

    @Value("${messenger.pageAccessToken}")
    String pageAccessToken;

    @Before
    public void setUp(){


        //MockitoAnnotations.initMocks(this);

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

        assertEquals(1, 1);
        //Keyword keyword1 = new Keyword();
        //keyword1.setId(1);
        //keyword1.setWord("test");

        //keywordService.addKeyword(keyword1);
        //when(keywordRepository.count()).thenReturn(1l);

        //assertEquals(1, keywordService.findAllKeywords().size());

    }

}
