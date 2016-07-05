package stanford.nlp.source;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;


public class StanfordNER {

	/**
	 * identify Name,organization location etc entities and return Map<List>
	 * @param text -- data
	 * @param model - Stanford model names out of the three models
	 * @return
	 */
	 public static LinkedHashMap <String,LinkedHashSet<String>> identifyNER(String text,String model)
	 {
	 LinkedHashMap <String,LinkedHashSet<String>> map=new <String,LinkedHashSet<String>>LinkedHashMap();
	 String serializedClassifier =model;
	 System.out.println(serializedClassifier);
	 CRFClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	 List<List<CoreLabel>> classify = classifier.classify(text);
	 for (List<CoreLabel> coreLabels : classify)
	 {
	 for (CoreLabel coreLabel : coreLabels)
	 {
	 
	 String word = coreLabel.word();
	 String category = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
	 if(!"O".equals(category))
	 {
	 if(map.containsKey(category))
	 {
	 // key is already their just insert in arraylist
	 map.get(category).add(word);
	 }
	 else
	 {
	 LinkedHashSet<String> temp=new LinkedHashSet<String>();
	 temp.add(word);
	 map.put(category,temp);
	 }
	 System.out.println(word+":"+category);
	 }
	 
	 }
	 
	 }
	 return map;
	 }
	 public static void main(String args[])
	 {
		 long startTime = System.currentTimeMillis();

		 try {
			BufferedReader br = new BufferedReader(new FileReader("testfiles/Organisation-TrainingFile-With-Description.txt"));
		    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			        
			   	 System.out.println(identifyNER(line, "classifiers/english.conll.4class.distsim.crf.ser.gz").toString());
			   	 
			    } 
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
     long stopTime = System.currentTimeMillis();
     long elapsedTime = stopTime - startTime;
     System.out.println(elapsedTime);
     
	 }

	
}
