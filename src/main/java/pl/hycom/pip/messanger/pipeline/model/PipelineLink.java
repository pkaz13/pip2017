package pl.hycom.pip.messanger.pipeline.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.ToString;

@XmlRootElement(name = "pipelinelink")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
public class PipelineLink {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "processor")
    private Processor processor;

    @XmlElement(name = "transition")
    private List<Transition> transitions;
}
