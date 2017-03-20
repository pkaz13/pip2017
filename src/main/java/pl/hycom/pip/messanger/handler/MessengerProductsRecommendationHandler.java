package pl.hycom.pip.messanger.handler;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.templates.GenericTemplate;
import com.github.messenger4j.send.templates.Template;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;

import java.util.List;


/**
 * Created by maciek on 19.03.17.
 */
@Component
@Log4j2
public class MessengerProductsRecommendationHandler implements TextMessageEventHandler {

    @Autowired
    private ProductService productService;
    @Autowired
    private MessengerSendClient sendClient;

    @Override
    public void handle(TextMessageEvent msg) {
        sendAnswer(msg.getSender().getId());
    }

    private void sendAnswer(String id) {

        log.info("Sending answer message to[" + id + "]");

        //TODO: zmienić hardkodowana wartosc produktow
        final List<Product> products = productService.getFewProducts(3);

        if (CollectionUtils.isEmpty(products)) {
            sendTextMessage(id, "No products found.");
        } else {
            final GenericTemplate genericTemplate = getStructuredMessage(products);
            sendStructuredMessage(id, genericTemplate);
        }
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

    //TODO: przeniesc metode do innej klasy, uwazac na null
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
