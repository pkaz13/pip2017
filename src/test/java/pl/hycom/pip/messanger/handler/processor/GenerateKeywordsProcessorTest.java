package pl.hycom.pip.messanger.handler.processor;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by szale_000 on 2017-04-06.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class GenerateKeywordsProcessorTest {

    GenerateKeywordsProcessor sut = new GenerateKeywordsProcessor();

    @Test
    public void processMessageShouldRemoveUnwantedSymbols() throws Exception {
        //given
        String message = "te{}[]()!@#$%s^&*~'?\".,/+t";
        //when
        String refactoredMessage = sut.processMessage(message);
        //then
        Assertions.assertThat(refactoredMessage).isEqualTo("test");
    }

    @Test
    public void processMessageShouldMakeLowerCase() throws Exception {
        //given
        String message = "rANdoM TESt";
        //when
        String refactoredMessage = sut.processMessage(message);
        //then
        Assertions.assertThat(refactoredMessage).isEqualTo("random test");
    }

    @Test
    public void processMessageShouldReturnEmptyIfGivenNull() throws Exception {
        //given
        String message = null;
        //when
        String refactoredMessage = sut.processMessage(message);
        //then
        Assertions.assertThat(refactoredMessage).isEqualTo("");
    }

    @Test
    public void processMessageShouldNotRemoveDash() throws Exception {
        //given
        String message = "auto-pilot";
        //when
        String refactoredMessage = sut.processMessage(message);
        //then
        Assertions.assertThat(refactoredMessage).isEqualTo("auto-pilot");
    }

    @Test
    public void processKeywordsShouldRemoveSmallWords() throws Exception {
        //given
        String[] keywords = StringUtils.split("A bb ccc dddd eeeee f ggg", " ");
        //when
        String[] processKeywords = sut.processKeywords(keywords);
        //then
        Assertions.assertThat(processKeywords).containsOnly("ccc", "dddd", "eeeee", "ggg");
    }

    @Test
    public void processKeywordsShouldRemoveDuplicates() throws Exception {
        //given
        String[] keywords = StringUtils.split("aaa aaa bbb bbb ccc ccc", " ");
        //when
        String[] processKeywords = sut.processKeywords(keywords);
        //then
        Assertions.assertThat(processKeywords).containsOnlyOnce("aaa", "bbb", "ccc");
    }

    @Test
    public void processKeywordsShouldIgnoreMultipleSpaces() throws Exception {
        //given
        String[] keywords = StringUtils.split("aaa     bbb", " ");
        //when
        String[] processKeywords = sut.processKeywords(keywords);
        //then
        Assertions.assertThat(processKeywords).containsOnly("aaa", "bbb");
    }

    @Test
    public void generateKeywordsTest() throws Exception {
        //given
        String message = "Ala m4 kota, a kot ma ale(?), nie mam pomysłu na stringa wcale!!";
        //when
        String[] processKeywords = sut.generateKeywords(message);
        //then
        Assertions.assertThat(processKeywords).containsOnlyOnce("kota", "pomysłu", "stringa", "wcale");
    }

}