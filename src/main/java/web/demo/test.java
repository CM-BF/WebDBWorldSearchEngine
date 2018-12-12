package web.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class test {
	public static void main(String[] args) throws Exception{
		Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		String s = "Large-scale parallelization and distributed processing techniques to speed up the offline training of complex recommender models;\n" + 
        		"Scalable hash and indexing techniques to speed up the online recommendation and reduce the storage cost;\n" + 
        		"Machine learning techniques that effectively extract and fuse heterogeneous and multi- modal content features to improve recommendations;\n" + 
        		"Context-aware recommendation systems incorporating various contextual information such as location-based recommendation and social recommendation;\n" + 
        		"Incremental recommendation solutions and online learning models to deal with continuous updates, especially real-time streaming data for recommendations;\n" + 
        		"Active learning techniques to acquire high-quality and informative user feedback data;\n" + 
        		"Robust recommender models that are resistant to spam reviews and ratings;\n" + 
        		"Spam detection techniques to discover the malicious attackers and spam reviews or ratings;\n" + 
        		"Data cleaning techniques to improve the quality of user generated behaviour and content data.\n" + 
        		"Notes for Research Papers";
		Annotation document = new Annotation(s);
        System.out.println("load finished");
        pipeline.annotate(document);
        String[] lines = s.split("\n");
        List<Boolean> lines_read = new ArrayList<>();
        for(int i=0; i<lines.length; i++) {
        	lines_read.add(false);
        }
        List<Annotation> linenlp = new ArrayList<>();
        for(String line : lines) {
        	linenlp.add(new Annotation(line));	
	    	pipeline.annotate(linenlp.get(linenlp.size()-1));
        }
        int bl=0, el;
        float nercount=0, wordcount=0;
        for(int i = 0; i<= lines.length; i++) {
        	if(i == lines.length || lines[i].equals("")) {
        		el = i;
        		if(Math.abs(wordcount - 0)>1e-5 && nercount/wordcount > 0.4 && (wordcount>10 || el-bl-1>=3)) {
        			for(int j=bl+1; j<el; j++) {
        				if(!lines_read.get(j)) {
        					System.out.println(lines[j]);
        				}
        			}
        		}
        		bl = i;
        		nercount = 0;
        		wordcount = 0;
        		continue;
        	}
        	for (CoreLabel token : linenlp.get(i).get(TokensAnnotation.class)) {
        		if(token.get(TextAnnotation.class).matches("[a-zA-Z]*")) {
        			wordcount++;
        			if(token.get(PartOfSpeechAnnotation.class).contains("NN") && token.get(NamedEntityTagAnnotation.class).equals("O")) {
        				nercount++;
        			}
        		}   		
        	}
        }
        System.out.println("End of processing");
	}
}
