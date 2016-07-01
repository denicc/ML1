package wikidata.connection;


import java.util.UUID;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;



public class FusekiTest {
	

	 private static final String UPDATE_TEMPLATE = 
	            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
	            + "INSERT DATA"
	            + "{ <http://example/%s>    dc:title    \"A new theater\" ;"
	            + "                         dc:creator  \"A.N.Other\" ." + "}   ";

		private static final String FUSEKI_SERVER = "http://138.201.125.161:3030";

	 
	    public static void main(String[] args) {
	        //Add a new book to the collection
	        String id = UUID.randomUUID().toString();
	        System.out.println(String.format("Adding %s", id));
	        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
	                UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
	                FUSEKI_SERVER + "/ds/update");
	        upp.execute();
	        
//	        //Query the collection, dump output
	        QueryExecution qe = QueryExecutionFactory.sparqlService(
	        		FUSEKI_SERVER + "/ds/query", "SELECT * WHERE {?x ?r ?y}");
	        ResultSet results = qe.execSelect();
	        ResultSetFormatter.out(System.out, results);
	        qe.close();
	    }
	
}
