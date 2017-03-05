package pl.hycom.model;

import lombok.Data;

/**
 * Created by patry on 04/03/2017.
 */
@Data
public class MessageResponse {
    private Recipient recipient;
    private MessageData message;

    @Data(staticConstructor="of")
    public static class Recipient {
        private String id;
    }

    @Data(staticConstructor="of")
    public static class MessageData {
        private String text;
    }
}
