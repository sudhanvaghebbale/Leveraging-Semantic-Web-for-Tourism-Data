package com.example.ser540;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
//import org.apache.jena.query.QueryExecution;
//import org.apache.jena.query.QueryExecutionFactory;
//import org.apache.jena.query.ResultSet;
//import org.apache.jena.query.ResultSetFormatter;
//import org.apache.jena.sparql.lang.SPARQLParser;
import com.hp.hpl.jena.query.*;

public class LandingPage extends AppCompatActivity {
    EditText latitudeEditText;
    EditText longitudeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        latitudeEditText = this.findViewById(R.id.gpsXEditText);
        longitudeEditText = this.findViewById(R.id.gpsYEditText);


        Button btn2= this.findViewById(R.id.gpsSubmitButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String latitude = latitudeEditText.getText().toString();
                final String longitude = longitudeEditText.getText().toString();
                Intent i=new Intent(LandingPage.this, NearestPlacesPage.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                startActivity(i);
            }
        });

    }
}
