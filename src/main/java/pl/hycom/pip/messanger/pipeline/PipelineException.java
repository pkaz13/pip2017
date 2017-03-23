package pl.hycom.pip.messanger.pipeline;

public class PipelineException extends Exception {

    private static final long serialVersionUID = -472767063863425099L;

    public PipelineException() {
        super();
    }

    public PipelineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PipelineException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipelineException(String message) {
        super(message);
    }

    public PipelineException(Throwable cause) {
        super(cause);
    }

}
