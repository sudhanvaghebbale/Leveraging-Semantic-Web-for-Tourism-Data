package com.example.ser540;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class POIDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_details);
        String URL = getIntent().getStringExtra("id");

        TextView textView = findViewById(R.id.detailPageTextView);
        //textView.setText("Put some random text here \n and then check if it is \n working still");

        String serviceEndPoint = "http://ec2-34-207-128-85.compute-1.amazonaws.com:3030/dataset3";
        Queries queries = new Queries(URL);
        String query;
        if(URL.contains("ontology-14")) {
            query = queries.getPubsDetailsQuery();
            QueryExecution q = new QueryEngineHTTP(serviceEndPoint, query);
            ResultSet results = q.execSelect();
            QuerySolution qs = results.next();
            textView.setText("Name : " + qs.get("?name") + "\nAddress : " + qs.get("?address") +
                    "\nPopularity Rating : " + qs.get("?popularityRating") + "\nPopular Item : " + qs.get("?popularItemName") +
                    "\nCovid Rating : " + qs.get("?covidRating"));
        }
        else if (URL.contains("ontology-2")) {
            query = queries.getPOIDetailsQuery();
            QueryExecution q = new QueryEngineHTTP(serviceEndPoint, query);
            ResultSet results = q.execSelect();
            QuerySolution qs = results.next();
            textView.setText("Name : " + qs.get("?name") + "\nAddress : " + qs.get("?address") + "\nReview Count : " + qs.get("?reviewCount") +
                    "\nPolarity Count : " + qs.get("?polarityCount") + "\nPopularity Rating : " + qs.get("?popularityRating") +
                    "\nCovid Rating : " + qs.get("?covidRating"));
        }
        else {
            query = queries.getStationsDetailsQuery();
            QueryExecution q = new QueryEngineHTTP(serviceEndPoint, query);
            ResultSet results = q.execSelect();
            QuerySolution qs = results.next();
            textView.setText("Name : " + qs.get("?name") + "\nZone Color : " + qs.get("?zoneColor") +
                    "\nPopularity Rating : " + qs.get("?popularityRating") + "\nPlatform Count : " + qs.get("?platformCount") +
                    "\nCovid Rating : " + qs.get("?covidRating"));
        }

    }

}
