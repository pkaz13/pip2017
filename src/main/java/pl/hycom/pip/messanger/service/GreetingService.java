package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class GreetingService implements InitializingBean {

    private Map<String, String> availableLocale = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/messenger-locale.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                availableLocale.put(record.get(0), record.get(1));
            }
        }

        log.info("Locale loaded: " + availableLocale);
    }

    public Map<String, String> getAvailableLocale(List<com.github.messenger4j.profile.Greeting> greetings) {
        Map<String, String> locale = new TreeMap<>(availableLocale);

        // remove existing locale
        greetings.stream().forEach(g -> locale.remove(g.getLocale()));

        return locale;
    }

    public boolean isValidLocale(String locale) {
        return availableLocale.containsKey(locale);
    }
}
