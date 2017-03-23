package pl.hycom.pip.messanger.pipeline;

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.pipeline.model.Pipeline;
import pl.hycom.pip.messanger.pipeline.model.PipelineChain;
import pl.hycom.pip.messanger.pipeline.model.PipelineLink;

@Log4j2
@Service
public class PipelineManager implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private Pipeline pipeline;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(getClass().getResourceAsStream("/pipeline.xml")));

        pipeline = (Pipeline) JAXBContext.newInstance(Pipeline.class).createUnmarshaller().unmarshal(xsr);
    }

    public void runProcess(String pipelineChainName, Map<String, Object> params) throws PipelineException {

        log.info("Starting pipeline[" + pipelineChainName + "] with params[" + params + "]");

        PipelineContext ctx = new PipelineContext(params);

        PipelineChain pipelineChain = findPipelineChain(pipelineChainName);

        PipelineLink pipelineLink = findPipelineLink(pipelineChain.getHeadLink(), pipelineChain);

        // TDOD: pobrac procesor,
        // pobrac beana z kontekstu,
        // uruchomic procesor z ctx,
        // sprawdzic wynik i poszukac nastepnego pipelineLinka w transition,
        // jak transition nie ma albo nie ma linka to koniec

        // TODO: bazujac na MessengerProductsRecommendationHandler trzeba stworzyc 2 procesory i wpiac w pipeline
        // pierwszy ma pobrac produkty i zapisac w ctx
        // drugi ma wyslac produkty w ramach odpowiedzi
    }

    private PipelineChain findPipelineChain(String pipelineChainName) throws PipelineException {
        for (PipelineChain pc : pipeline.getPipelineChains()) {
            if (StringUtils.equals(pc.getName(), pipelineChainName)) {
                return pc;
            }
        }

        throw new PipelineException("No pipelineChain with name[" + pipelineChainName + "]");
    }

    private PipelineLink findPipelineLink(String pipelineLinkName, PipelineChain pipelineChain) throws PipelineException {
        for (PipelineLink pl : pipelineChain.getPipelineLinks()) {
            if (StringUtils.equals(pl.getName(), pipelineLinkName)) {
                return pl;
            }
        }

        throw new PipelineException("No pipelineLink with name[" + pipelineLinkName + "] in pipelineChain with name[" + pipelineChain.getName() + "]");
    }
}
