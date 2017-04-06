package pl.hycom.pip.messanger.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class GreetingService {

    private Map<String, String> availableLocale = Collections.synchronizedMap(new HashMap<>());

    public Map<String, String> getAvailableLocale(List<com.github.messenger4j.profile.Greeting> greetings) {
        Map<String, String> locale = new TreeMap<>(availableLocale);

        // remove existing locale
        greetings.stream().forEach(g -> locale.remove(g.getLocale()));

        return locale;
    }

    public boolean isValidLocale(String locale) {
        return availableLocale.containsKey(locale);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000) // every 1 hour
    private synchronized void retrieveLocaleFromFacebook() {
        Map<String, String> availableLocaleTmp = new HashMap<>();

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse("https://www.facebook.com/translations/FacebookLocales.xml");

            NodeList nl = document.getElementsByTagName("locale");
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                availableLocaleTmp.put(e.getElementsByTagName("representation").item(0).getTextContent(), e.getElementsByTagName("englishName").item(0).getTextContent());
            }

            log.info("Loaded available locale from facebook: " + availableLocaleTmp);

        } catch (SAXException | IOException | ParserConfigurationException e) {
            log.error(e.toString());
            availableLocaleTmp = Collections.emptyMap();
        }

        synchronized (availableLocale) {
            availableLocale.clear();
            availableLocale.putAll(availableLocaleTmp);
        }
    }

}
