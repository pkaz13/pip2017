package ApacheNLP;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


    public class NameFinderUtil {

        public static String[] getNames(String tokenizerModel, String nameModel,
                                        String inputFile) throws FileNotFoundException {

            InputStream modelIn = new FileInputStream(nameModel);
            TokenNameFinderModel model = null;

            try {
                model = new TokenNameFinderModel(modelIn);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (modelIn != null) {
                    try {
                        modelIn.close();
                    } catch (IOException e) {
                    }
                }
            }

            NameFinderME nameFinder = new NameFinderME(model);

            TokenizerUtil tokenizerUtil = new TokenizerUtil(tokenizerModel);
            String[] tokens = tokenizerUtil
                    .tokenizeUsingLearnableTokenizer(FileUtils
                            .getFileDataAsString(inputFile));

            Span nameSpans[] = nameFinder.find(tokens);

            List<String> names = new ArrayList<>();

            for (Span span : nameSpans) {
                int start = span.getStart();
                int end = span.getEnd();

                String temp = "";
                for (int i = start; i < end; i++) {
                    temp = temp + tokens[i];
                }

                names.add(temp);
            }
            String[] temp = new String[names.size()];

            return names.toArray(temp);
        }
    }

