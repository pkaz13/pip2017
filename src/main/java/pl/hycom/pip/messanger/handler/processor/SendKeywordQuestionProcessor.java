package pl.hycom.pip.messanger.handler.processor;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.QuickReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
import pl.hycom.pip.messanger.repository.model.Keyword;

import java.util.List;

/**
 * Created by szale_000 on 2017-05-28.
 */
@Component
@Log4j2
public class SendKeywordQuestionProcessor implements PipelineProcessor {

    @Autowired
    private MessengerSendClient sendClient;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        log.info("Started process of SendKeywordQuestionProcessor");
        StringBuilder messageBuilder = new StringBuilder();

        List<QuickReply> quickReplies = getQuickReplies();
        String id = ctx.get(SENDER_ID, String.class);
        Keyword keywordToBeAsked = ctx.get(KEYWORD_TO_BE_ASKED, Keyword.class);
        String message = messageBuilder.append("Keyword: ").append(keywordToBeAsked.getWord()).toString();

        sendQuickReply(id, message, quickReplies);

        return 1;
    }

    private void sendQuickReply(String id, String message, List<QuickReply> quickReplies) {
        try {
            sendClient.sendTextMessage(id, message, quickReplies);
        } catch (MessengerApiException | MessengerIOException e) {
            e.printStackTrace();
        }
    }

    private List<QuickReply> getQuickReplies() {
        return QuickReply.newListBuilder()
                .addTextQuickReply("yes", "YES_PAYLOAD").toList()
                .addTextQuickReply("no", "NO_PAYLOAD").toList()
                .build();
    }
}
