package pl.hycom.model;

import java.util.List;

/**
 * Created by patry on 04/03/2017.
 */
public class MessageRequestBody {

    public String object;
    public List<Entry> entry;

    public static class Entry {
        public String id;
        public String time;
        public List<Messaging> messaging;
    }

    public static class Messaging {
        public Sender sender;
        public Recipient recipient;
        public String timestamp;
        public Message message;
    }

    public static class Sender {
        public String id;
    }

    public static class Recipient {
        public String id;
    }

    public static class Message {
        public String mid;
        public String seq;
        public String text;
    }
}
