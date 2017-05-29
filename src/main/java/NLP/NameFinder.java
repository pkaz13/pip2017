package NLP;

import java.io.IOException;

/**
 * Created by Piotr on 2017-05-29.
 */
public class NameFinder {

    public static void main(String args[]) throws IOException {
        String tokenModelFile = "C:/OpenNLP/en-token.bin";
        String modelFile = "C:/OpenNLP/en-ner-person.bin";
        String inputFile = "C:/OpenNLP/names.txt";

        String[] result = NameFinderUtil.getNames(tokenModelFile, modelFile, inputFile);

        for(String s: result)
            System.out.println(s);

    }
}
