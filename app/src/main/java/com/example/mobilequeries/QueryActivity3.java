package com.example.mobilequeries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.mobilequeries.QueryActivity2.dates;


public class QueryActivity3 extends AppCompatActivity {

    public static float sourceLong, sourceLat, destinationLong, destinationLat;
    public static Spinner spinner3;
    public static Button doneBtn;
    public Connection con;

    public static String srcZone, destZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query3);

        // setting the spinner for google map usage
        spinner3 = findViewById(R.id.spinnerMap);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(QueryActivity3.this,
                android.R.layout.simple_spinner_item, dates);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter1);

        doneBtn = findViewById(R.id.mapsBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLatLong();
            }
        });

    }
    public void getLatLong() {
        String date = spinner3.getSelectedItem().toString();
        try {
            con = connect_azure();
            if (con == null) {
                Toast.makeText(getApplicationContext(), "CHECK INTERNET!", Toast.LENGTH_SHORT).show();
            } else {
                String querySource = "SELECT TOP(1) taxi_data.longitude, taxi_data.latitude, taxi_data.LocationID, taxi_data.ZONE\n" +
                        "FROM [output] AS main_data, [taxi_zones] as taxi_data\n" +
                        "WHERE main_data.start_date ='" + date + "' AND main_data.PULoc = taxi_data.LocationID\n" +
                        "ORDER By trip_distance DESC;";

                Statement stmtSource = con.createStatement();
                ResultSet rsSource = stmtSource.executeQuery(querySource);

                while (rsSource.next()) {
                    System.out.println("PU: long: " + rsSource.getString("longitude") + "lat: " + rsSource.getString("latitude"));
                    sourceLat = rsSource.getFloat("latitude");
                    sourceLong = rsSource.getFloat("longitude");
                    srcZone = rsSource.getString("ZONE");
                }
                String queryDestination = "SELECT TOP(1) taxi_data.longitude, taxi_data.latitude, taxi_data.LocationID, taxi_data.ZONE\n" +
                        "FROM [output] AS main_data, [taxi_zones] as taxi_data\n" +
                        "WHERE main_data.start_date ='" + date + "' AND main_data.DOLoc = taxi_data.LocationID\n" +
                        "ORDER BY trip_distance DESC;";

                Statement stmtDestination = con.createStatement();
                ResultSet rsDestination = stmtDestination.executeQuery(queryDestination);
                while (rsDestination.next()) {
                    System.out.println("DO: long: " + rsDestination.getString("longitude") + "lat: " + rsDestination.getString("latitude"));
                    destinationLat = rsDestination.getFloat("latitude");
                    destinationLong = rsDestination.getFloat("longitude");
                    destZone = rsDestination.getString("ZONE");
                }
            }
        } catch (SQLException throwables) {
            Toast.makeText(getApplicationContext(), throwables.toString(), Toast.LENGTH_SHORT).show();
        }
        // opening the child activity (Map)
        openChildActivityMap();
        System.out.println("PULoc: long = " + sourceLong + " lat = " + sourceLat);
        System.out.println("DOLoc: long = " + destinationLong + " lat = " + destinationLat);
        System.out.println("SRCZONE: " + srcZone + " DESTZONE" + destZone);
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void openChildActivityMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public Connection connect_azure() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "database:taxi";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException throwables) {
            Toast.makeText(getApplicationContext(), throwables.toString(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return connection;
    }
}