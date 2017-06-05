package pl.hycom.pip.messanger.handler.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
import pl.hycom.pip.messanger.repository.model.Keyword;

import java.util.List;
import java.util.Objects;

/**
 * Created by patry on 04/06/17.
 */

@Component
@Log4j2
public class ProcessQuickReplyProcessor implements PipelineProcessor {

    private static final String YES_ANSWER = "tak";
    private static final String NO_ANSWER = "nie";

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        log.info("Started process of ProcessQuickReplyProcessor");

        String answer = ctx.get(ANSWER, String.class);
        List<Keyword> keywords = ctx.get(KEYWORDS, List.class);
        List<Keyword> excludedKeywords = ctx.get(KEYWORDS_EXCLUDED, List.class);
        Keyword keywordAsked = ctx.get(KEYWORD_TO_BE_ASKED, Keyword.class);

        if (Objects.equals(answer, YES_ANSWER)) {
            keywords.add(keywordAsked);
            ctx.put(KEYWORDS, keywords);
        } else {
            excludedKeywords.add(keywordAsked);
            ctx.put(KEYWORDS_EXCLUDED, excludedKeywords);
        }
        return 1;
    }
}
