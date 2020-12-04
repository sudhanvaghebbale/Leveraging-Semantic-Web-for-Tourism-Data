package com.example.ser531;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

import java.util.ArrayList;

public class NearestPlacesPage extends AppCompatActivity {

    private String placeType = "All Places";
    private boolean covidSafety = false;
    private boolean popularity = false;

    ArrayList<Place> places = new ArrayList<>();

    Spinner spinner;
    CheckBox covidCheckBox;
    CheckBox popularityCheckBox;
    SearchView searchView;
    LinearLayout mainLinearLayout;
    TextView textView;
    Queries queries;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_places_page);
        getSupportActionBar().setTitle("SER531");
        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");
        System.out.println(latitude + "Ankit");
        queries = new Queries(latitude, longitude);
        queries.KEYWORD = "";
        spinner = (Spinner) findViewById(R.id.spinner);
        covidCheckBox = findViewById(R.id.covidCheckBox);
        popularityCheckBox = findViewById(R.id.popularityCheckBox);
        searchView = findViewById(R.id.keywordSearchView);
        mainLinearLayout = this.findViewById(R.id.linearLayoutInsideScrollView);
        textView = findViewById(R.id.textView5);
        Button submitButton = findViewById(R.id.nearestPlacesSubmit);
        Button goToMapsButton = findViewById(R.id.goToMapButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.places_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        executeQuery(covidSafety, popularity, placeType, queries.KEYWORD);
        createViews();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeType = spinner.getSelectedItem().toString();
                covidSafety = covidCheckBox.isChecked();
                popularity = popularityCheckBox.isChecked();
                String keyword = searchView.getQuery().toString();

                queries.KEYWORD = keyword;
                executeQuery(covidSafety, popularity, placeType, queries.KEYWORD);

                createViews();
            }
        });

        goToMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NearestPlacesPage.this, MapsActivity.class);
                i.putExtra("places", places);
                startActivity(i);
            }
        });

    }

    private void createViews() {
        if((mainLinearLayout).getChildCount() > 0)
            (mainLinearLayout).removeAllViews();
        mainLinearLayout.addView(textView);
        for(Place place : places) {
            TextView tv = new TextView(this);
            tv.setText(place.getName() + "                                                        " + place.getType());
            tv.setTextSize(22);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
            lp.setMargins(0,15,0,10);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(lp);
            linearLayout.addView(tv);
            //tv.setMargins(0,10,0,10);
            mainLinearLayout.addView(linearLayout);
        }
    }

    private void executeQuery(boolean covidSafety, boolean popularity, String placeType, String keyword) {
        this.places = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String serviceEndPoint = "http://ec2-34-207-128-85.compute-1.amazonaws.com:3030/dataset3";

        String query = queryBuilder(covidSafety, popularity, placeType, keyword);
        //System.out.println(query);


//        Query query1 = QueryFactory.create(query, Syntax.syntaxARQ);
//        QueryExecution qe = QueryExecutionFactory.create(QueryFactory.create(query1), new
//                DatasetImpl(ModelFactory.createDefaultModel()));
//        ResultSet resultSet = qe.execSelect();
        QueryExecution q = new QueryEngineHTTP(serviceEndPoint, query);
        ResultSet results = q.execSelect();
        //ResultSetFormatter.out(System.out, results);
        while(results.hasNext()) {
            QuerySolution qs = results.next();
            String latitude = qs.get("?lat").toString();
            String longitude = qs.get("?long").toString();
            String name = qs.get("?name").toString();
            String URI = qs.get("?subject").toString();
            String type;
            if(URI.contains("ontology-14"))
                type = "Pub";
            else if(URI.contains("ontology-2"))
                type = "POI";
            else
                type = "Tube Station";
            Place place = new Place(Double.parseDouble(latitude), Double.parseDouble(longitude), URI, name, type);
            places.add(place);

        }
        //ResultSetFormatter.out(System.out, results);
    }
    private String queryBuilder(boolean covidSafety, boolean popularity, String placeType, String keyword) {
        String query = "";
        if(keyword.equals("")) {
            if(placeType.equals("POI"))
                query = queries.nearestPOI();

            else if(placeType.equals("Pubs"))
                query = queries.nearestPubs();// get nearest Pubs

            else if(placeType.equals("Tube Stations"))
                query = queries.nearestStations();//get nearest stations

            else if(covidSafety) {
                if(popularity) {
                    query = queries.nearestCovidSafePopularFederated();// get nearest federated with covid and popularity
                }
                else
                    query = queries.nearestCovidSafeFederated();// get nearest federated with only covid.
            }

            else if(popularity)
                query = queries.nearestPopularFederated();// get nearest federated with only popularity.

            else {
                query = queries.nearestFederated();
            }
                //get all federated.

        }

        else {
            query = queries.KeyWordSearchfederated();//get keyword federated.
        }

        return query;
    }
}
