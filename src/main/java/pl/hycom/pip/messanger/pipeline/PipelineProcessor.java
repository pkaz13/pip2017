package pl.hycom.pip.messanger.pipeline;

public interface PipelineProcessor {

    int runProcess(PipelineContext ctx) throws PipelineException;

}
