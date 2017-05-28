package pl.hycom.pip.messanger.handler.processor;

import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;

/**
 * Created by szale_000 on 2017-05-28.
 */
public class SendKeywordQuestionProcessor implements PipelineProcessor {

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        return 1;
    }
}
