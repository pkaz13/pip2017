package ApacheNLP;

/**
 * Created by Piotr on 2017-05-29.
 */
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.InputStream;

public class Tokenizator {

    public static void main(String args[]) throws Exception{

        String sentence = "Witaj w Hycomie. To jest przykładowy tekst , który będzie podzielony na mniejsze części. Właśnie tak.";


        //Loading the Tokenizer model
        InputStream inputStream = new FileInputStream("C:/OpenNLP/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStream);

        //Instantiating the TokenizerME class
        TokenizerME tokenizer = new TokenizerME(tokenModel);

        //Tokenizing the given raw text
        String tokens[] = tokenizer.tokenize(sentence);

        //Printing the tokens
        for (String a : tokens)
            System.out.println(a);
    }
}
