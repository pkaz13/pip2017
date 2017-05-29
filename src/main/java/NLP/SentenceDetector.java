package NLP;


import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.InputStream;

public class SentenceDetector {


    public static void main(String args[]) throws Exception {

        String sen = "Witaj w Hycomie. To jest przykładowy tekst , który będzie podzielony na mniejsze części. Właśnie tak.";
        //Loading a sentence model
        InputStream inputStream = new FileInputStream("C:/OpenNLP/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);

        //Instantiating the SentenceDetectorME class
        SentenceDetectorME detector = new SentenceDetectorME(model);

        //Detecting the position of the sentences in the paragraph
        Span[] spans = detector.sentPosDetect(sen);

        //Printing the sentences and their spans of a paragraph
        for (Span span : spans)
            System.out.println(sen.substring(span.getStart(), span.getEnd())+" "+ span);
    }





}
