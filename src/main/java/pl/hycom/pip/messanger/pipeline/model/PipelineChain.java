package pl.hycom.pip.messanger.pipeline.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.ToString;

@XmlRootElement(name = "pipelinechain")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
public class PipelineChain {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "headlink")
    private String headLink;

    @XmlElement(name = "pipelinelink")
    private List<PipelineLink> pipelineLinks;

}
