import com.amazonaws.services.lambda.runtime.Context;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.coref.CorefCoreAnnotations;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

public class NlpTest
{
    public static String text = "Joe Smith was born in California. ";

    public String handleRequest(Map<String,Object> input, Context context) {

        // load input from environment variable
        String str = System.getenv("INPUT");
        if (str.length() > 0)
            text = str;

        // Setup CoreNLP
        String output = "Detected entity: ";
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");

        props.setProperty("coref.algorithm", "neural");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        CoreDocument doc = new CoreDocument(text);

        pipeline.annotate(doc);

        // output named entities
        for (CoreEntityMention em : doc.entityMentions())
            output += String.format(" "+em.text()+"("+em.entityType() + ") ");

        // output sentiment
        int s = 0, sum = 0;
        output += "The sentiment of the sentences are: ";
        for (int i = 0; i < doc.sentences().size(); ++i) {
            CoreSentence sentence = doc.sentences().get(i);
            output += sentence.sentiment() + ", ";

            if (sentence.sentiment().equals("Positive")) {
                s = 3;
            }
            else if (sentence.sentiment().equals("Very Positivee")) {
                s = 4;
            }
            else if (sentence.sentiment().equals("Negative")) {
                s = 1;
            }
            else if (sentence.sentiment().equals("Very Negative")) {
                s = 0;
            }
            else {
                s = 2;
            }

            sum += s;
        }

        output += ", and the overall sentiment rating is " + sum / doc.sentences().size();

        return output;
    }
}
