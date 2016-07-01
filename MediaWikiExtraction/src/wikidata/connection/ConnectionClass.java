package wikidata.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ConnectionClass {
	
	public ConnectionClass(){
		
	}
	
	private String getShortDescription(String subject) throws IOException{
		
		URL url = new URL("https://de.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "_"));
		String text = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
		    String line = null;
		    while (null != (line = br.readLine())) {
		    	System.out.println(line);
		        line = line.trim();
		        if (!line.startsWith("|")
		                && !line.startsWith("{")
		                && !line.startsWith("}")
		                && !line.startsWith("<center>")
		                && !line.startsWith("---")) {
		            text += line;
		        }
		        if (text.length() > 200) {
		            break;
		        }
		    }
		}
		System.out.println("text = " + text);
		return text;
	}
	
	public static void main(String[] args) {
		ConnectionClass conn = new ConnectionClass();
		try {
			System.out.println(conn.getShortDescription("Apple"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
