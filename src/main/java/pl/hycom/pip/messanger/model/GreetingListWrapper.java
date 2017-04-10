package pl.hycom.pip.messanger.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by marcinus on 25.03.17.
 * This wrapper is needed for ThymeLeaf's foreach
 * see greetings.html file
 */
@Data
@NoArgsConstructor
public class GreetingListWrapper {
    private List<Greeting> greetings = new ArrayList<>();

    public GreetingListWrapper(List<com.github.messenger4j.profile.Greeting> profileGreetingsList) {
        for (com.github.messenger4j.profile.Greeting greeting : profileGreetingsList) {
            greetings.add(new Greeting(greeting));
        }
    }

    public List<com.github.messenger4j.profile.Greeting> extractGreetings() {
        List<com.github.messenger4j.profile.Greeting> out = new ArrayList<>();
        for (Greeting greeting : greetings) {
            out.add(new com.github.messenger4j.profile.Greeting(greeting.getText(), greeting.getLocale()));
        }
        return out;
    }
}
