package pl.hycom.pip.messanger.pipeline;

import lombok.NonNull;

public interface PipelineProcessor {

    int runProcess(@NonNull PipelineContext ctx) throws PipelineException;

}
