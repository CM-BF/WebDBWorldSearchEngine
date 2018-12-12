package web.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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


public class nlp 
{
    public static void main( String[] args ) throws Exception
    {
    	List<Integer> sbdate = new ArrayList<>();
    	List<Integer> sbtopic = new ArrayList<>();
    	boolean Debug = false;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        File file = new File("/home/levishery/Documents/Web/search/crawlers/doc");
        int filecount = file.listFiles().length;
        for(int num=0; num<filecount; num++) {
        	// open file
            Path path = Paths.get("/home/levishery/Documents/Web/search/crawlers/doc/" + "Mail"+ num +".txt");
            String s = "";
            try {
            	BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            	s = reader.readLine();
            }catch(IOException e) {
            	e.printStackTrace();
            }
            
            
            // parse json *********************************
        	JSONParser parser = new JSONParser();
        	Object obj = parser.parse(s);
        	JSONObject dir = (JSONObject)obj;
        	String text = dir.get("content").toString();
            
//            String text = "DBWorld Message\n[Sorry for multiple copies] \n**********************************************************************************************\nInvited Session on \n          Interdisciplinary Approaches in Data Science and Digital Transformation Practice (IADSDTP 2019)\nURL: http://idt-19.kesinternational.org/cmsISdisplay.php (IS13)\nOrganized within the framework of the \n          11th International Conference on Intelligent Decision Technologies (KES-IDT 2019)\nSt. Julians, Malta, 17 - 19 June, 2019\nURL: http://idt-19.kesinternational.org/\n***********************************************************************************************\nSCOPE & TOPICS\nOne of the hot issues in many organization systems is how to transform large amounts of daily collected operational data into the \nuseful knowledge from the perspective of declared company goals and expected business values. The main concerns of this invited session are \nData Science (DS) and Digital Transformation (DT) paradigms, as a set of theories, methodologies, processes, architectures, and technologies \nthat transform raw data into meaningful and useful information, knowledge, and value. Various interdisciplinary oriented DS and DT approaches \nmay provide organizations the ability to use their data to improve quality of business, increase financial efficiency and operational \neffectiveness, conduct innovative research and satisfy regulatory requirements. Applications of appropriate DS and DS implementation \nmethodologies together with outcomes related to collaborative and interdisciplinary approaches are inevitable when applying DT approaches to \nlarge and complex organization systems. For many years, such interdisciplinary approaches were used in analyzing big data gathered from not \nonly business sectors, but also public, non-profit, and government sectors.\nThe main goal of the session is to attract researchers from all over the world who will present their contributions, interdisciplinary \napproaches or case studies in the area of DS and DT. The focus in Data Science may be set to various aspects, such as: data warehousing, \nreporting, online analytical processing, data analytics, data mining, process mining, text mining, predictive analytics and prescriptive \nanalytics, as well as various aspects of machine learning, big data and time series analysis. We express an interest in gathering scientists \nand practitioners interested in applying DS and DT approaches in public and government sectors, such as healthcare, education, or security \nservices. However, experts from all sectors are welcomed.\nSubmissions are expected from, but not limited to the following topics:\n* Data Science and Digital Transformation \u2013 Theoretical and practical aspects\n* Data Science and Digital Transformation Applications and Industry Experience\n* Digitization and impacts for Digital Transformation\n* Business Intelligence, Digitization and data driven business models\n* Business Analytics and the new role of IT in enterprises \n* Impacts of Business Analytics for the performance of profit or non-profit organisations\n* Data Warehousing, Data Mining, Online Analytical Processing, and Reporting Capabilities\n* Statistical analysis and characterization, predictive analytics and prescriptive analytics\n* Process Mining, Pattern Mining, and Swarm Intelligence\n* Data quality assessment and improvement: preprocessing, cleaning, and missing data\n* Semi-structured or unstructured data in Business Intelligence systems\n* Information integration for data and text mining\n* Dynamic Pricing: potentials and DT Approaches\n* Cloud-computing models and scalability in DT systems\n* Mobile BI, Smart Data, Smart Services, and Smart Products\n* Data privacy and security issues in Data Science and DT systems\n* Digital Marketing, new web services, sematic web and data analytics\n* Data Science and Analytics for Healthcare and other Public Sectors\n* Educational Data Mining\n* Social network data analysis\n* Web survey methods in Business Intelligence and Data Science\n* Organizational and human factors, skills, and qualifications for DS and DT Approaches\n* Teaching DS and DT approaches in academic and industrial environments\n* Smart Data, Smart Products, Smart Service World\n* Artificial Intelligence, Machine Learning, Deep Learning \u2013 Theoretical and practical aspects\nPAPER SUBMISSION\n* Papers will be refereed and accepted on the basis of their scientific merit and relevance to the conference.\n* The required full paper length is 8 to 10 pages. Call for papers and detailed information for the authors can be found at http://idt-19.kesinternational.org/submission.php. \n* Papers to be considered for the conference must be submitted through the PROSE online submission and review system available at http://idt-19.kesinternational.org/prose.php. \nIMPORTANT DATES\n* Paper submission: 11 January, 2019\n* Acceptance notification: 8 February, 2019\n* Final paper submission: 8 March, 2019\n* Conference: 17 - 19 June, 2019\nWORKSHOP CHAIRS\n* Ralf-Christian H\u00e4rting, University of Aalen, Germany\n* Ivan Lukovic, University of Novi Sad, Serbia \n";
            Annotation document = new Annotation(text);
            System.out.println("load finished: " + num);
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
            }
            // A line-by-line divide
            String[] lines = text.split("\n");
            List<Boolean> lines_read = new ArrayList<>();
            for(int i=0; i<lines.length; i++) {
            	lines_read.add(false);
            }
            List<Annotation> linenlp = new ArrayList<>();
            for(String line : lines) {
            	linenlp.add(new Annotation(line));	
    	    	pipeline.annotate(linenlp.get(linenlp.size()-1));
            }
            
            
            if(Debug) {
	            for (int i=0; i<words.size(); i++) {
	            	System.out.print(i);
	            	System.out.print(":  "+ words.get(i) + "  ");
	            	System.out.print(posTags.get(i)+ "  ");
	            	System.out.print(nerTags.get(i)+ "  ");
	            	System.out.println();
	            }
            }
            
            if(Debug) System.out.println();
            // Json prepare
            dir.put("Time", new JSONArray());
            JSONArray time = (JSONArray) dir.get("Time");
            
            
            /* *******************************************************************
             * ****************************Deal with due Time********************
             * *******************************************************************/
            List<String> ddl = new ArrayList<>();
            		ddl.add("due");
            		ddl.add("ddl");
            		ddl.add("deadline");
            		ddl.add("submission");
            		ddl.add("conference");
            
            
            // A word-by-word method
            for (int i=0; i<words.size(); i++) {
            	// having deadline keywords
            	if (nerTags.get(i).equals("DATE")) {
            		int count = 0;
            		List<String> dates = new ArrayList<>();
            		for (int j=i; j<i+4 && j<words.size();j++) {
            			if (nerTags.get(j).equals("DATE")) {
            				count++;
            				dates.add(words.get(j));
            			}
            		}
            		if (count >= 2) {
            			for (int j=i; j>i-6 && j>=0; j--) {
            				if (ddl.contains(words.get(j).toLowerCase())) {
            					String deadword = words.get(j);
            					for (int k=0; k<lines.length; k++) {
            						boolean flag = true;
            						if (!lines[k].contains(deadword))
            							flag = false;
            						for (String date : dates) {
            							if (!lines[k].contains(date))
            								flag = false;
            						}
            						if (flag && !lines_read.get(k)) {
            							time.add(lines[k]);
            							if(Debug) System.out.println(time);
            							lines_read.set(k, true);
            						}
            					}
            				}
            			}	
            		}
            	}
            }
            // but if it hasn't got one
            // A line-by-line method
            boolean DateBlockSign = false;
            boolean first = true;
            for(int i=0; i<lines.length; i++) {
            	if (lines[i].toLowerCase().contains("important")
            			&& lines[i].toLowerCase().contains("date")) {
            		// find date block
            		DateBlockSign = true;
            		first = true;
            		continue;
            	}
            	if (DateBlockSign) {
            		// first in
            		if (first && linenlp.get(i).get(TokensAnnotation.class).size()<=2) {
            			first = false;
            			continue;
            		}
    				first = false;	
            		// check blank row
            		if (lines[i].equals("")) {		
            			continue;
            		}
            		// check block end
            		if (linenlp.get(i).get(TokensAnnotation.class).size() <= 4) {
            			DateBlockSign = false;
            			continue;
            		}
            		// in date block
            		// no date check
            		boolean have_date = false;
            		for(CoreLabel token : linenlp.get(i).get(TokensAnnotation.class)) {
            			if(token.get(NamedEntityTagAnnotation.class).contains("DATE")) {
            				have_date = true;
            			}
            		}
            		if (!lines_read.get(i) && have_date) {
    					time.add(lines[i]);
    					if(Debug) System.out.println(time);
    					lines_read.set(i, true);
            		}
            		
            	}
            }
            // date last: for SB author
            if (time.isEmpty()) {
            	System.out.println("sb author: date");
            	sbdate.add(num);
            	for(int i=0; i<words.size();i++) {
                	if(nerTags.get(i).equals("DATE")) {
                		List<String> dates = new ArrayList<>();
                		int count = 0;
                		for(int j=i; j<i+4 && j<words.size(); j++) {
                			if(nerTags.get(j).equals("DATE")) {
                				count++;
                				dates.add(words.get(j));
                			}
                		}
                		if(count>=2) {
                			String temp = "";
                			for(int j=i; j<i+4 && j<words.size(); j++) {
                    			if(nerTags.get(j).equals("DATE")) {
                    				temp = temp + words.get(j) + " ";
                    			}
                    		}
        					time.add(temp);
        					if(Debug) System.out.println(time);
        					break;
                		}
                	}
                }
            }
            
            
            /* *******************************************************************
             * ****************************Deal with the topic********************
             * *******************************************************************/
            // json prepare
            dir.put("Topic", new JSONArray());
            JSONArray topic = (JSONArray) dir.get("Topic");
            // A big end block sign : with less than 4 words and its up and down rows are useless.
            
            List<String> UpDownSigns = new ArrayList<>();
            	UpDownSigns.add("*");
            	UpDownSigns.add("=");
            	UpDownSigns.add("-");
            String UpDownSign = "*";
            
            //line-by-line method
            boolean TopicBlockSign = false;
            boolean TopicRightSign = false;
            boolean TopicUpDownSign = false;
            boolean firstin = true;
            for (int i=0; i< lines.length; i++) {
            	if (lines[i].toLowerCase().contains("topic") && 
            			linenlp.get(i).get(TokensAnnotation.class).size() <= 6) {
            		if(i==lines.length-1) break;
            		TopicBlockSign = true;
            		firstin = true;
            		// judge right sign
            		if (lines[i].charAt(lines[i].length()-1) == '*') {
            			TopicRightSign = true;
            		}else {
            			TopicRightSign = false;
            		}
            		// judge down sign
            		TopicUpDownSign = false;
            		if (linenlp.get(i+1).get(TokensAnnotation.class).size() <= 2) {
            			for (int j=0; j<UpDownSigns.size(); j++) {
            				if (lines[i+1].contains(UpDownSigns.get(j))) {
            					TopicUpDownSign = true;
            					UpDownSign = UpDownSigns.get(j);
            					break;
            				}
            			}
            		}else {
            			if(linenlp.get(i-1).get(TokensAnnotation.class).size() <= 2) {
            				for (int j=0; j<UpDownSigns.size(); j++) {
                				if (lines[i-1].contains(UpDownSigns.get(j))) {
                					TopicUpDownSign = true;
                					UpDownSign = UpDownSigns.get(j);
                					break;
                				}
                			}
            			}
            		}
            		continue;
            	}
            	if (TopicBlockSign) {
            		if(i+1>=lines.length) break;
            		// in the block
            		// cut off normal blank row
            		if (lines[i].equals("")) {
            			first = false;
            			continue;
            		}
            		// case1: simplest one no sign
            		if (!TopicRightSign && !TopicUpDownSign) {
            			// short words and blank row as the sign
            			if (linenlp.get(i).get(TokensAnnotation.class).size() <= 4 &&
            					lines[i+1].equals("") && lines[i-1].equals("")) {
            				TopicBlockSign = false;
            				continue;
            			}
            		}
            		// case2: have RightSign
            		if (TopicRightSign) {
            			if (lines[i].charAt(lines[i].length()-1) == '*') {
            				TopicBlockSign = false;
            				continue;
            			}
            		}
            		// case3: have UpDownSign
            		if (TopicUpDownSign) {
            			if (linenlp.get(i+1).get(TokensAnnotation.class).size() <= 2) {
            				if (lines[i+1].contains(UpDownSign)) {
            					TopicBlockSign = false;
            					continue;
            				}
            			}
            		}
            		// words case:
            		if (lines[i].toLowerCase().contains("important")&& lines[i].toLowerCase().contains("date")) {
            			TopicBlockSign = false;
            			continue;
            		}
            		if (lines[i].toLowerCase().contains("submission")) {
            			 for(CoreLabel token : linenlp.get(i).get(TokensAnnotation.class)) {
            				 if(token.get(NamedEntityTagAnnotation.class).equals("DATE")) {
            					 TopicBlockSign = false;
            				 }
            			 }
            			 if (!TopicBlockSign) {
            				 continue;
            			 }
            		}
            		if (lines_read.get(i)) {
            			// read by other block
            			TopicBlockSign = false;
            			continue;
            		}
            		// is really in the block
            		topic.add(lines[i]);
            		if(Debug) System.out.println(topic);
            	}
            }
            // Using "track" keywords: mail3
            // NER method
            if(topic.isEmpty()) {
            	int bl=0, el;
                float nercount=0, wordcount=0;
                for(int i = 0; i<= lines.length; i++) {
                	if(i == lines.length || lines[i].equals("")) {
                		el = i;
                		if(Math.abs(wordcount - 0)>1e-5 && nercount/wordcount > 0.4 && wordcount>10 && el-bl-1>=3) {
                			for(int j=bl+1; j<el; j++) {
                				if(!lines_read.get(j)) {
                					topic.add(lines[j]);
                					if(Debug) System.out.println(topic);
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
            }
            
            // For SB author
            int sbi=0;
            while (topic.isEmpty()) {
            	if(!lines[sbi].equals("")) {
            		System.out.println("sb author: topic");
            		sbtopic.add(num);
    	        	topic.add(lines[sbi]);
    	        	if(Debug) System.out.println(topic);
            	}
            	sbi++;
            }
            
            /* *******************************************************************
             * ****************************Deal with the Site********************
             * *******************************************************************/
            //json prepare
//            dir.put("Site", site);
            // words method
            String site="";
            for(int i=0; i<words.size(); i++) {
            	String country="";
            	if(nerTags.get(i).contains("COUNTRY")) {
            		boolean flag = true;
            		country = words.get(i);
            		for(int j=i-10; j< i+10 && j<words.size(); j++) {
            			if(j<0) j=0;
            			if(nerTags.get(j).contains("PERSON")) {
            				flag = false;
            			}
            		}
            		if(flag) {
            			List<String> location = new ArrayList<>();
            			for(int j=i-10; j<=i; j++) {
            				if(j<0) j=0;
                			if(nerTags.get(j).contains("LOCATION")
                					|| nerTags.get(j).contains("CITY")) {
                				location.add(words.get(j));
                			}
                		}
            			for(String loc : location) {
            				site = site + " " + loc;
            			}
            			site = site + ", " + country;
            			break;
            		}
            	}
            }
            dir.put("Site",	site);
			if(Debug) System.out.println(dir.get("Site"));
            /* ************************************************
             * ***************Save json file*******************
             * ************************************************ */
            StringWriter out = new StringWriter();
            dir.writeJSONString(out);
            String jsontext = out.toString();
            Path pathout = Paths.get("/home/levishery/eclipse-workspace/demo/documents/" + "Mail" + num + ".txt");
            try {
            	BufferedWriter writer = Files.newBufferedWriter(pathout, StandardCharsets.UTF_8);
            	writer.write(jsontext);
            	writer.flush();
            	writer.close();
            }catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        
        
	    	
        
    	System.out.println( "End of Processing" );
    	System.out.println("sbdate:");
    	System.out.println(sbdate);
    	System.out.println("sbtopic");
    	System.out.println(sbtopic);
    }
}

