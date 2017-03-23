package pl.hycom.pip.messanger.pipeline.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.ToString;

@XmlRootElement(name = "processor")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@ToString
public class Processor {

    @XmlAttribute(name = "bean")
    private String bean;

}
