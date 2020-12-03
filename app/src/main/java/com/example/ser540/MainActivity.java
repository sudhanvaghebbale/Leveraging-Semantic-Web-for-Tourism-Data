package com.example.ser540;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//import org.apache.jena.query.QueryExecution;
//import org.apache.jena.query.QueryExecutionFactory;
//import org.apache.jena.query.ResultSet;
//import org.apache.jena.query.ResultSetFormatter;
//import org.apache.jena.sparql.lang.SPARQLParser;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        TextView hello = this.findViewById(R.id.hello);
        hello.setText("DOES THIS WORK");
        String serviceEndPoint = "http://ec2-34-207-128-85.compute-1.amazonaws.com:3030/dataset2";

        String query = "SELECT ?subject ?predicate ?object" +
                "WHERE {" +
                "  ?subject ?predicate ?object" +
                "}" +
                "LIMIT 25";
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceEndPoint, query);
        ResultSet results = q.execSelect();

        ResultSetFormatter.out(System.out, results);
    }
}
