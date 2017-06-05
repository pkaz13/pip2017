package pl.hycom.pip.messanger.service;

import PolishNLP.Result;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResultService {
    private static final String FILE_PATH = "C:\\NLP_pl\\result.xml";


    public static ArrayList<Result> getResults() {
        ArrayList<Result> resultList = new ArrayList<Result>();
        try {
            File input = new File(FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document document = db.parse(input);
            document.getDocumentElement().normalize();
            NodeList orthList = document.getElementsByTagName("orth");
            NodeList baseList = document.getElementsByTagName("base");

            for (int i = 0; i < baseList.getLength(); i++) {

                resultList.add(new Result(orthList.item(i).getTextContent(), baseList.item(i).getTextContent()));

            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


    return resultList;


}

}
