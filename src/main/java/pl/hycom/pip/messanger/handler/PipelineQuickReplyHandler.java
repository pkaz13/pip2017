package pl.hycom.pip.messanger.handler;

import com.github.messenger4j.receive.events.QuickReplyMessageEvent;
import com.github.messenger4j.receive.handlers.QuickReplyMessageEventHandler;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.handler.model.Payload;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineManager;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
import pl.hycom.pip.messanger.pipeline.model.Pipeline;
import pl.hycom.pip.messanger.repository.model.Keyword;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by patry on 04/06/17.
 */

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PipelineQuickReplyHandler implements QuickReplyMessageEventHandler {

    private static final String PIPELINECHAIN_NAME = "processQuickReply";
    private final PipelineManager pipelineManager;

    @Override
    public void handle(QuickReplyMessageEvent event) {
        log.info("Received quick reply event");

        Map<String, Object> params = new HashMap<>();

        Gson gson = new Gson();
        String payloadString = event.getQuickReply().getPayload();
        Payload payload = gson.fromJson(payloadString, Payload.class);

        String answer = event.getText();

        params.put(PipelineProcessor.SENDER_ID, event.getSender().getId());
        params.put(PipelineProcessor.MESSAGE, event.getText());
        params.put(PipelineProcessor.KEYWORDS_EXCLUDED, payload.getExcludedKeywords());
        params.put(PipelineProcessor.KEYWORDS, payload.getKeywords());
        params.put(PipelineProcessor.KEYWORD_TO_BE_ASKED, payload.getKeywordToBeAsked());
        params.put(PipelineProcessor.ANSWER, answer);

        try {
            pipelineManager.runProcess(PIPELINECHAIN_NAME, params);
        } catch (PipelineException e) {
            log.error(e);
        }
    }
}
