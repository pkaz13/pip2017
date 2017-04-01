package pl.hycom.pip.messanger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
        List<com.github.messenger4j.profile.Greeting> greetings = new ArrayList<>();
        for (Greeting greeting : getGreetings()) {
            greetings.add(new com.github.messenger4j.profile.Greeting(greeting.getText(), greeting.getLocale()));
        }
        return greetings;
    }
}
