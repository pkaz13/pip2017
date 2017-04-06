package pl.hycom.pip.messanger.handler.processor;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.handler.PipelineMessageHandler;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;

import java.util.Arrays;

/**
 * Created by szale_000 on 2017-04-06.
 */
@Component
@Log4j2
public class GenerateKeywordsProcessor implements PipelineProcessor {

    public static final String KEYWORDS = "KEYWORDS";

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        log.info("Started keyword generating");

        String message = ctx.get(PipelineMessageHandler.MESSAGE, String.class);
        String[] keywords = generateKeywords(message);
        ctx.put(KEYWORDS, keywords);

        return 1;
    }

    protected String[] generateKeywords(String message) {
        message = processMessage(message);
        String[] keywords = StringUtils.split(message, " ");
        keywords = processKeywords(keywords);
        return keywords;
    }

    protected String processMessage(String message) {
        final String regex = "[{}\\[\\]()!@#$%^&*~'?\".,/+]";
        message = StringUtils.replaceAll(message, regex, "");
        message = StringUtils.lowerCase(message);
        return message != null ? message : "";
    }

    protected String[] processKeywords(String[] keywords) {
        String[] processedKeywords = Arrays.stream(Arrays.copyOf(keywords, keywords.length))
                .filter(s -> StringUtils.length(s) > 2)
                .distinct()
                .toArray(String[]::new);
        return processedKeywords;
    }

}
