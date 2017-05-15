/*
 *   Copyright 2012-2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package pl.hycom.pip.messanger.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

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

