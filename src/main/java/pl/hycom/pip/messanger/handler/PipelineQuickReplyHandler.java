package pl.hycom.pip.messanger.handler;

import com.github.messenger4j.receive.events.QuickReplyMessageEvent;
import com.github.messenger4j.receive.handlers.QuickReplyMessageEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineManager;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
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
    public void handle(QuickReplyMessageEvent msg) {
        log.info("Received quick reply event");
        log.info("Payload: " + msg.getQuickReply().getPayload());

        Map<String, Object> params = new HashMap<>();

        params.put(PipelineProcessor.SENDER_ID, msg.getSender().getId());
        params.put(PipelineProcessor.MESSAGE, msg.getText());
        params.put(PipelineProcessor.KEYWORDS_EXCLUDED, new LinkedList<Keyword>());

        try {
            pipelineManager.runProcess(PIPELINECHAIN_NAME, params);
        } catch (PipelineException e) {
            log.error(e);
        }
    }
}
