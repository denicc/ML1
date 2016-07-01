package wikidata.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class GetSPARQLData {

	
	public static void main(String[] args) throws Exception {

		String queryString;
		
		queryString = "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                "SELECT DISTINCT ?country ?countryLabel\n" +
                "WHERE\n" +
                "{\n" +
                "\t?country wdt:P31 wd:Q3624078 .\n" +
                "    ?country wdt:P1622 wd:Q13196750.\n" +
                "    ?country wdt:P30 wd:Q15\n" +
                "\tFILTER NOT EXISTS {?country wdt:P31 wd:Q3024240}\n" +
                "\tSERVICE wikibase:label { bd:serviceParam wikibase:language \"de\" }\n" +
                "}\n" +
                "ORDER BY ?countryLabel";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", queryString);
        try {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results, query);
            
            System.out.println(results.getResultVars().get(0));
            System.out.println(results.getResultVars().get(1));
            
            //List Statemnts
           
            System.out.println(results.getRowNumber());
            System.out.println(results.getResourceModel().listNameSpaces().toString());
            
            
            
            
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            qexec.close();
        }
		
		
	}
	
	
	
}
