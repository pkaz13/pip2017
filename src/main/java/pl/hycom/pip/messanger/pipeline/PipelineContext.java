package pl.hycom.pip.messanger.pipeline;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PipelineContext extends HashMap<String, Object> {

    private static final long serialVersionUID = 4022714046624372103L;

    public PipelineContext(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            Object o = get(key);
            if (o != null) {
                return clazz.cast(o);
            }

        } catch (ClassCastException e) {
            log.error(e);
        }

        return null;
    }

}
