package pl.hycom.model;

import lombok.Data;

import java.util.List;

/**
 * Created by patry on 04/03/2017.
 */
@Data
public class MessageRequestBody {

    private String object;
    private List<Entry> entry;

    @Data(staticConstructor="of")
    public static class Entry {
        private String id;
        private String time;
        private List<Messaging> messaging;
    }

    @Data(staticConstructor="of")
    public static class Messaging {
        private Sender sender;
        private Recipient recipient;
        private String timestamp;
        private Message message;
    }

    @Data(staticConstructor="of")
    public static class Sender {
        private String id;
    }

    @Data(staticConstructor="of")
    public static class Recipient {
        private String id;
    }

    @Data(staticConstructor="of")
    public static class Message {
        private String mid;
        private String seq;
        private String text;
    }
}
