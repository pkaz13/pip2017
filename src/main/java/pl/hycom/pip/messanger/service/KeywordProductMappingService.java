package pl.hycom.pip.messanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jvnet.hk2.annotations.Service;
import pl.hycom.pip.messanger.model.KeywordProductMapping;
import pl.hycom.pip.messanger.model.KeywordProductMappingId;
import pl.hycom.pip.messanger.repository.KeywordProductMappingRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by patry on 18/03/2017.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log4j2
public class KeywordProductMappingService {
    private final KeywordProductMappingRepository repository;

    public void addMapping(KeywordProductMapping mapping) {
        log.info("addMapping method from KeywordProductMappingService class invoked");
        repository.save(mapping);
    }

    public KeywordProductMapping findMappingByIds(KeywordProductMappingId id) {
        log.info("findMappingByIds method from KeywordProductMappingService class invoked");
        return repository.findOne(id);
    }

    public List<KeywordProductMapping> findAllMappings() {
        log.info("findAllMappings method from KeywordProductMappingService class invoked");
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteMapping(KeywordProductMappingId id) {
        log.info("deleteMapping method from KeywordProductMappingService class invoked");
        repository.delete(id);
    }
}
