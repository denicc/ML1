package data.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class GetShortDescriptions {
	
public static void main(String[] args) throws Exception {
	 	
		getWikidata("");
		parseIntoJSONObject("Text");
		
	}
	
	private static void parseIntoJSONObject(String string) {
		
	}

	public void saveInFile(String description){
		
	}
	
		
	public static void getWikidata(String FilePath) throws JSONException, IOException{
		
		File fout = new File("logging/outList.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		
		try (BufferedReader br = new BufferedReader(new FileReader("testFiles/OrganisationNames_List.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String subject = line;
		    	
		    	URL url = new URL("https://de.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + subject.replace(" ", "%20"));
				String text = "";
				try (BufferedReader br2 = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
				    String line2 = null;
				    while (null != (line2 = br2.readLine())) {
				        line2 = line2.trim();
				        if (line2.contains(line + " steht für:")) {
							System.out.println("Doppelbedeutung " + "\n");
						}else{
							if (true) {
					            text += line2;
					        }
						}
				        
				    }
				}
				
				JSONObject json = new JSONObject(text);
				JSONObject query = json.getJSONObject("query");
				JSONObject pages = query.getJSONObject("pages");
			
				Iterator<?> a = pages.keys();
				
				while(a.hasNext()){
					String key = (String)a.next();
					JSONObject page = pages.getJSONObject(key);
					
					String extract = "";
					
					if(key.equals("-1")){
						 extract = "";
					}else{
						extract = page.getString("extract");	
						String writing = line + "extract: " + extract;
						System.out.println(line);
						System.out.println("extract = " + extract);
						bw.write(writing);
						bw.newLine();
					}
					
					

				}
		    	
		    
		    }
		    bw.close();
		}		
		
	}	
	
}
