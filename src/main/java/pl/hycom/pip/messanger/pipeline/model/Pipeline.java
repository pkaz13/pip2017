package pl.hycom.pip.messanger.pipeline.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.ToString;

@XmlRootElement(name = "pipeline")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
public class Pipeline {

    @XmlElement(name = "pipelinechain")
    private List<PipelineChain> pipelineChains;

}
