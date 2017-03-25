package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.service.KeywordService;

import static org.junit.Assert.assertEquals;


/**
 * Created by Piotr on 20.03.2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@ActiveProfiles({"dev", "kazmierczak", "testdb"})
//@ActiveProfiles("testdb")
@Log4j2
public class KeywordServiceTest {

    @Autowired
    private KeywordService keywordService;


    @Before
    public void setUp(){



    }

    @Test
    public void addKeywordTest(){
        Keyword keyword = new Keyword();
        keyword.setWord("test1");

        keywordService.addKeyword(keyword);

        assertEquals(1, keywordService.findAllKeywords().size());

    }

}
