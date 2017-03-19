package pl.hycom.pip.messanger.handler;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.templates.GenericTemplate;
import lombok.extern.log4j.Log4j2;
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

    private MessengerSendClient sendClient;

    public MessengerProductsRecommendationHandler(MessengerSendClient sendClient) {
        this.sendClient = sendClient;
    }

    @Override
    public void handle(TextMessageEvent msg) {
        sendGenericMessage(msg.getSender().getId());
    }

    private void sendGenericMessage(String id) {

        //TODO: zmienić hardkodowana wartosc produktow
        final List<Product> products = productService.getFewProducts(3);
        final GenericTemplate genericTemplate = getStructuredMessage(products);

        log.info("Sending structured message to[" + id + "]");

        try {
            sendClient.sendTemplate(id, genericTemplate);
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
