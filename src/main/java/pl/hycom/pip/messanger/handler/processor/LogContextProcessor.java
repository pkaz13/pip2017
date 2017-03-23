package pl.hycom.pip.messanger.handler.processor;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;

@Component
@Log4j2
public class LogContextProcessor implements PipelineProcessor {

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {

        log.info("Pipeline context: " + ctx);

        return 1;
    }

}
