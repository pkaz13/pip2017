package pl.hycom.pip.messanger.model;

import lombok.Data;

/**
 * Created by patry on 04/03/2017.
 */
@Data
public class MessageResponse {
    private Recipient recipient;
    private MessageData message;

    @Data
    public static class Recipient {
        private String id;
    }

    @Data
    public static class MessageData {
        private String text;
    }
}
