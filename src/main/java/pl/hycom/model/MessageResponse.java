package pl.hycom.model;

/**
 * Created by patry on 04/03/2017.
 */
public class MessageResponse {
    public Recipient recipient;
    public MessageData message;

    public static class Recipient {
        public String id;
    }

    public static class MessageData {
        public String text;
    }
}
