package pl.hycom.pip.messanger.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineManager;

@Log4j2
public class PipelineMessageHandler implements TextMessageEventHandler {

    public static final String SENDER_ID = "senderId";
    public static final String MESSAGE = "message";

    private PipelineManager pipelineManager;

    @Value("${messenger.pipelineFileURL}")
    private String pipelineUrl;

    @Override
    public void handle(TextMessageEvent msg) {

        Map<String, Object> params = new HashMap<>();

        params.put(SENDER_ID, msg.getSender().getId());
        params.put(MESSAGE, msg.getText());

        try {
            PipelineManager pipelineManager = new PipelineManager(pipelineUrl);
            pipelineManager.runProcess("processMessage", params);
        } catch (PipelineException e) {
            log.error(e);
        }

    }

}
