package web.demo;

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


public class nlpdemo2 
{
    public static void main( String[] args )
    {
    	// creates a StanfordCoreNLP object, with POS tagging, lemmatization,
        // NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        String text = "DBWorld Message\n[Sorry for multiple copies] \n**********************************************************************************************\nInvited Session on \n          Interdisciplinary Approaches in Data Science and Digital Transformation Practice (IADSDTP 2019)\nURL: http://idt-19.kesinternational.org/cmsISdisplay.php (IS13)\nOrganized within the framework of the \n          11th International Conference on Intelligent Decision Technologies (KES-IDT 2019)\nSt. Julians, Malta, 17 - 19 June, 2019\nURL: http://idt-19.kesinternational.org/\n***********************************************************************************************\nSCOPE & TOPICS\nOne of the hot issues in many organization systems is how to transform large amounts of daily collected operational data into the \nuseful knowledge from the perspective of declared company goals and expected business values. The main concerns of this invited session are \nData Science (DS) and Digital Transformation (DT) paradigms, as a set of theories, methodologies, processes, architectures, and technologies \nthat transform raw data into meaningful and useful information, knowledge, and value. Various interdisciplinary oriented DS and DT approaches \nmay provide organizations the ability to use their data to improve quality of business, increase financial efficiency and operational \neffectiveness, conduct innovative research and satisfy regulatory requirements. Applications of appropriate DS and DS implementation \nmethodologies together with outcomes related to collaborative and interdisciplinary approaches are inevitable when applying DT approaches to \nlarge and complex organization systems. For many years, such interdisciplinary approaches were used in analyzing big data gathered from not \nonly business sectors, but also public, non-profit, and government sectors.\nThe main goal of the session is to attract researchers from all over the world who will present their contributions, interdisciplinary \napproaches or case studies in the area of DS and DT. The focus in Data Science may be set to various aspects, such as: data warehousing, \nreporting, online analytical processing, data analytics, data mining, process mining, text mining, predictive analytics and prescriptive \nanalytics, as well as various aspects of machine learning, big data and time series analysis. We express an interest in gathering scientists \nand practitioners interested in applying DS and DT approaches in public and government sectors, such as healthcare, education, or security \nservices. However, experts from all sectors are welcomed.\nSubmissions are expected from, but not limited to the following topics:\n* Data Science and Digital Transformation \u2013 Theoretical and practical aspects\n* Data Science and Digital Transformation Applications and Industry Experience\n* Digitization and impacts for Digital Transformation\n* Business Intelligence, Digitization and data driven business models\n* Business Analytics and the new role of IT in enterprises \n* Impacts of Business Analytics for the performance of profit or non-profit organisations\n* Data Warehousing, Data Mining, Online Analytical Processing, and Reporting Capabilities\n* Statistical analysis and characterization, predictive analytics and prescriptive analytics\n* Process Mining, Pattern Mining, and Swarm Intelligence\n* Data quality assessment and improvement: preprocessing, cleaning, and missing data\n* Semi-structured or unstructured data in Business Intelligence systems\n* Information integration for data and text mining\n* Dynamic Pricing: potentials and DT Approaches\n* Cloud-computing models and scalability in DT systems\n* Mobile BI, Smart Data, Smart Services, and Smart Products\n* Data privacy and security issues in Data Science and DT systems\n* Digital Marketing, new web services, sematic web and data analytics\n* Data Science and Analytics for Healthcare and other Public Sectors\n* Educational Data Mining\n* Social network data analysis\n* Web survey methods in Business Intelligence and Data Science\n* Organizational and human factors, skills, and qualifications for DS and DT Approaches\n* Teaching DS and DT approaches in academic and industrial environments\n* Smart Data, Smart Products, Smart Service World\n* Artificial Intelligence, Machine Learning, Deep Learning \u2013 Theoretical and practical aspects\nPAPER SUBMISSION\n* Papers will be refereed and accepted on the basis of their scientific merit and relevance to the conference.\n* The required full paper length is 8 to 10 pages. Call for papers and detailed information for the authors can be found at http://idt-19.kesinternational.org/submission.php. \n* Papers to be considered for the conference must be submitted through the PROSE online submission and review system available at http://idt-19.kesinternational.org/prose.php. \nIMPORTANT DATES\n* Paper submission: 11 January, 2019\n* Acceptance notification: 8 February, 2019\n* Final paper submission: 8 March, 2019\n* Conference: 17 - 19 June, 2019\nWORKSHOP CHAIRS\n* Ralf-Christian H\u00e4rting, University of Aalen, Germany\n* Ivan Lukovic, University of Novi Sad, Serbia \n";
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);
        // run all Annotators on this text
        System.out.println("load finished");
        pipeline.annotate(document);
        
        // these are all the sentences in this document
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<String> words = new ArrayList<>();
        List<String> posTags = new ArrayList<>();
        List<String> nerTags = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(TextAnnotation.class);
                words.add(word);
                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                posTags.add(pos);
                // this is the NER label of the token
                String ne = token.get(NamedEntityTagAnnotation.class);
                nerTags.add(ne);
            }
            // This is the syntactic parse tree of sentence 
            Tree tree = sentence.get(TreeAnnotation.class); 
            System.out.println("Tree:\n"+ tree); 

            // This is the dependency graph of the sentence 
            SemanticGraph dependencies = sentence.get(CollapsedDependenciesAnnotation.class); 
            System.out.println("Dependencies\n:"+ dependencies);
        }
        System.out.println(words.toString());
        System.out.println(posTags.toString());
        System.out.println(nerTags.toString());
        
        // This is a map of the chain 
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class); 
        System.out.println("Map of the chain:\n" + graph);

    	System.out.println( "End of Processing" );
    }
}

