package pl.hycom.pip.messanger.handler.processor;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.templates.GenericTemplate;
import com.github.messenger4j.send.templates.Template;

import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.handler.PipelineMessageHandler;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;

@Component
@Log4j2
public class GenerateMessageProcessor implements PipelineProcessor {

    @Autowired
    private MessengerSendClient sendClient;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {

        log.info("Started process of GenerateMessageProcessor");

        @SuppressWarnings("unchecked")
        List<Product> products = ctx.get(LoadBestMatchingProductsProcessor.PRODUCTS, List.class);
        String senderId = ctx.get(PipelineMessageHandler.SENDER_ID, String.class);

        if (CollectionUtils.isEmpty(products)) {
            sendTextMessage(senderId, "No products found.");
            return 1;
        }

        sendStructuredMessage(senderId, getStructuredMessage(products));
        return 1;
    }

    private void sendStructuredMessage(String id, Template template) {
        try {
            sendClient.sendTemplate(id, template);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending structured answer", e);
        }
    }

    private void sendTextMessage(String id, String message) {
        try {
            sendClient.sendTextMessage(id, message);
        } catch (MessengerApiException | MessengerIOException e) {
            log.error("Error during sending answer", e);
        }
    }

    private GenericTemplate getStructuredMessage(List<Product> products) {
        GenericTemplate.Element.ListBuilder listBuilder = GenericTemplate.newBuilder().addElements();
        for (Product product : products) {
            listBuilder
                    .addElement(product.getName())
                    .subtitle(product.getDescription())
                    .imageUrl(product.getImageUrl())
                    .toList();
        }
        return listBuilder.done().build();
    }
}
