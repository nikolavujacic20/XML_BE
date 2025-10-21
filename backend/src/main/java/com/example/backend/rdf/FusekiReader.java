package com.example.backend.rdf;

import com.example.backend.dto.MetadataSearchParams;
import com.example.backend.utils.AuthenticationUtilitiesFuseki;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FusekiReader {
    private static final String GRAPH_URI = "metadata"; // http://localhost:8080/fuseki/AutorskaPravaData/data/metadata
    private static final String PREDICATE_TRIPLET_PART = "<http://www.ftn.uns.ac.rs/autorskoDelo/predicate/";
    private static final String SUBJECT_TRIPLET_PART = "<http://www.ftn.uns.ac.rs/autorskoDelo/";
    private static final String PREDICATE_ID_METADATA_NAME = "broj_prijave"; // "<http://www.ftn.uns.ac.rs/autorskoDelo/predicate/broj_prijave>";
    private static final String SUBJECT = "autorskoDelo";

    public static void readAllDataFromDatabase() throws IOException {
        FusekiAuthenticationUtilities.ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

        System.out.println("\n[INFO] Retrieving ALL triples from RDF Database from the graph \"" + conn.dataEndpoint + "/" + GRAPH_URI + "\".");
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + "/" + GRAPH_URI, "?s ?p ?o");

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        ResultSetFormatter.out(System.out, results);
        query.close();
    }

    public static String getRdfString(String brojPrijave) throws Exception {
        ResultSet results = getRdfByBrojPrijave(brojPrijave);

        return ResultSetFormatter.asXMLString(results);
    }

    public static String getJsonString(String brojPrijave) throws Exception {
        ResultSet results = getRdfByBrojPrijave(brojPrijave);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, results);

        return outputStream.toString();
    }

    private static ResultSet getRdfByBrojPrijave(String brojPrijave) throws IOException {
        String whereQueryByBrojPrijave = createWhereQueryByBrojPrijavePart(brojPrijave);
        ResultSetRewindable results = select(whereQueryByBrojPrijave);
        ResultSetFormatter.out(System.out, results);
        results.reset();

        return results;
    }

    private static String createWhereQueryByBrojPrijavePart(String brojPrijave) {
        String whereStr = "?s ?p ?o "; //.concat(createPredicateIdTripletQueryPart());
        String filterStr = "FILTER ( ?s = ".concat(SUBJECT_TRIPLET_PART).concat(brojPrijave).concat("> )");
        whereStr = whereStr.concat(filterStr);

        return whereStr;
    }

    public static List<String> findByMetadata(MetadataSearchParams param) throws IOException {
        String whereQueryPart = createWhereQueryPart(param);
        ResultSetRewindable results = select(whereQueryPart);
        return getDocumentIds(results);
    }

    public static List<String> findByMetadata(List<MetadataSearchParams> params) throws IOException {
        return findByCombinedMetadata(params);
    }

    private static String createWhereQueryPart(MetadataSearchParams param) {
        String whereStr = createPredicateIdTripletQueryPart();
        String filterStr = "FILTER(";
        whereStr = concateToWhere(param, whereStr);
        filterStr = concateToFilter(param, filterStr, true);
        filterStr = filterStr.concat(")");
        whereStr = whereStr.concat(filterStr);

        return whereStr;
    }

    private static String concateToWhere(MetadataSearchParams msp, String whereStr, List<String> alreadyConcatenatedProperties) {
        if (!alreadyConcatenatedProperties.contains(msp.getProperty())) {
            String whereConcat = createWhereConcat(msp);
            whereStr = whereStr.concat(whereConcat);
            alreadyConcatenatedProperties.add(msp.getProperty());
        }

        return whereStr;
    }


    private static String getEquivalenceOperator(String wordOperator) {
        if ("NOT".equals(wordOperator) || "!".equals(wordOperator) || "not".equals(wordOperator)) {
            return "!=";
        } else {
            return "=";
        }
    }

    private static List<String> getDocumentIds(ResultSet results) {
        List<String> idsDocuments = new ArrayList<>();

        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            RDFNode idNode = querySolution.get(PREDICATE_ID_METADATA_NAME);
            String id = idNode.toString();

            if (!idsDocuments.contains(id)){
                idsDocuments.add(id);
            }
        }

        return idsDocuments;
    }

    private static String getLogicalOperator(String wordOperator) {
        if ("OR".equals(wordOperator) || "||".equals(wordOperator) || "or".equals(wordOperator)) {
            return "||";
        } else {
            return "&&";
        }
    }

    private static String createWhereConcat(MetadataSearchParams msp) {
        return "?" + SUBJECT + PREDICATE_TRIPLET_PART + msp.getProperty() + "> ?" + msp.getProperty() + " . ";
    }

    private static String concateToFilter(MetadataSearchParams msp, String filterStr, boolean isFirstParam) {
        String logicalOperator = getLogicalOperator(msp.getOperator());
        String equivalenceOperator = getEquivalenceOperator(msp.getOperator());

        String filterConcat;
        if (isFirstParam) {
            filterConcat = "?" + msp.getProperty() + " " + equivalenceOperator + " \"" + msp.getValue() + "\" ";
        } else {
            filterConcat = logicalOperator + " ?" + msp.getProperty() + " " + equivalenceOperator + " \"" + msp.getValue() + "\" ";
        }

        return filterStr.concat(filterConcat);
    }

    private static String concateToWhere(MetadataSearchParams msp, String whereStr) {
        String whereConcat = createWhereConcat(msp);
        whereStr = whereStr.concat(whereConcat);

        return whereStr;
    }

    private static String createCombinedWhereQueryPart(List<MetadataSearchParams> params) {
        String whereStr = createPredicateIdTripletQueryPart();
        String filterStr = "FILTER(";
        List<String> alreadyConcatenatedProperties = new ArrayList<>();
        int numOfParamsConcated = 0;

        for (MetadataSearchParams msp : params) {
            whereStr = concateToWhere(msp, whereStr, alreadyConcatenatedProperties);
            filterStr = concateToFilter(msp, filterStr, numOfParamsConcated == 0);
            numOfParamsConcated += 1;
        }

        filterStr = filterStr.concat(")");
        whereStr = whereStr.concat(filterStr);

        return whereStr;
    }

    private static ResultSetRewindable select(String whereQueryPart) throws IOException {
        AuthenticationUtilitiesFuseki.ConnectionProperties conn = AuthenticationUtilitiesFuseki.loadProperties();
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + "/" + GRAPH_URI, whereQueryPart);
        System.out.println(sparqlQuery);
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet r = query.execSelect();
        ResultSetRewindable results = ResultSetFactory.copyResults(r);
        query.close();

        return results;
    }

    private static String createPredicateIdTripletQueryPart() {
        return "?".concat(SUBJECT)
                .concat(PREDICATE_TRIPLET_PART).concat(PREDICATE_ID_METADATA_NAME).concat("> ")
                .concat("?").concat(PREDICATE_ID_METADATA_NAME).concat(" . ");
    }


    private static List<String> findByCombinedMetadata(List<MetadataSearchParams> params) throws IOException {
        String whereQueryPart = createCombinedWhereQueryPart(params);
        ResultSetRewindable results = select(whereQueryPart);

        return getDocumentIds(results);
    }
}
