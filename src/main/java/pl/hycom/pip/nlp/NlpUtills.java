package pl.hycom.pip.nlp;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Piotr on 2017-06-09.
 */
@Log4j2
public class NlpUtills {

    public static ArrayList<Result> inputStreamToResultList(InputStream is) {
        ArrayList<Result> resultList = new ArrayList<Result>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    if (    systemId.contains("ccl.dtd")) {
                        return new InputSource(new StringReader(""));
                    }
                    else return null;

                }
            });
            Document doc = builder.parse(is);
            NodeList orthList = doc.getElementsByTagName("orth");
            NodeList baseList = doc.getElementsByTagName("base");
            for (int i = 0; i < orthList.getLength(); i++) {
                log.info(orthList.item(i).getTextContent());
                log.info(baseList.item(i).getTextContent());
                //docelowo będzie wywalone
                //
            }

            for (int i = 0; i < baseList.getLength(); i++) {

                resultList.add(new Result(orthList.item(i).getTextContent(), baseList.item(i).getTextContent()));
            }


        } catch (Exception ex) {
            log.error(ex.getMessage() + " Error during calling inputStreamToNodeList method ");
        }
        return resultList;
    }


}
