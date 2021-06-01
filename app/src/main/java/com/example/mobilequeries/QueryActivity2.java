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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryActivity2 extends AppCompatActivity {

    // @param dates is for the content in spinner
    public static String[] dates = {"2020-12-01", "2020-12-02", "2020-12-03", "2020-12-04",
            "2020-12-05", "2020-12-06", "2020-12-07", "2020-12-08",
            "2020-12-09", "2020-12-10", "2020-12-11", "2020-12-12",
            "2020-12-13", "2020-12-14", "2020-12-15", "2020-12-16",
            "2020-12-17", "2020-12-18", "2020-12-19", "2020-12-20",
            "2020-12-21", "2020-12-22", "2020-12-23"};

    // @params spinner1 and spinner2 are used for date range
    private static Spinner spinner1, spinner2;
    // button for execution
    private static Button selectBtn;
    // connection for Azure SQL server
    public Connection con;
    // query result string
    public static String[] resultString = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query2);

        // finding both spinners in .xml file
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        // creating adapter for first spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(QueryActivity2.this,
                android.R.layout.simple_spinner_item, dates);
        // making the spinner a dropdown menu
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // applying the adapter
        spinner1.setAdapter(adapter1);

        // same actions for the second spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(QueryActivity2.this,
                android.R.layout.simple_spinner_item, dates);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        // finding the button in .xml file
        selectBtn = findViewById(R.id.result_query2);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking the date range
                checkDates();
            }
        });
    }

    private void checkDates() {
        // getting the selected dates
        String date1 = spinner1.getSelectedItem().toString();
        String date2 = spinner2.getSelectedItem().toString();

        try {
            // formatting the dates for comparison
            Date selectDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
            Date selectDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);

            if (selectDate1.compareTo(selectDate2) <= 0) {
                // if date1 smaller or equal than date2 then connect to SQL
                Toast.makeText(getApplicationContext(),
                        "Range: True", Toast.LENGTH_SHORT).show();
                try {
                    con = connect_azure();
                    if (con == null) {
                        // if not connected internet not available or not turned on
                        Toast.makeText(getApplicationContext(), "CHECK INTERNET!", Toast.LENGTH_SHORT).show();
                    } else {
                        String query = "SELECT TOP(5) start_date,end_date,passenger_num,trip_distance, PULoc , DOLoc, ABS(payment) as payment\n" +
                                " FROM [output]\n" +
                                " WHERE (start_date BETWEEN '" + date1 + "' AND '" + date2 + "')\n" +
                                " AND   (end_date BETWEEN '" + date1 + "' AND '" + date2 + "') AND trip_distance != 0\n" +
                                " ORDER BY trip_distance";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        int i = 0;
                        while (rs.next()) {
                            resultString[i] = (i + 1) + ")\tStart Date: " + rs.getString("start_date") + "\n"
                                    + "\t\t\tEnd Date: " + rs.getString("end_date") + "\n"
                                    + "\t\t\tPassenger No: " + rs.getString("passenger_num") + "\n"
                                    + "\t\t\tTrip Distance: " + rs.getString("trip_distance") + "\n"
                                    + "\t\t\tPickup Location: " + rs.getString("PULoc") + "\n"
                                    + "\t\t\tDropOff Location: " + rs.getString("DOLoc") + "\n"
                                    + "\t\t\tPayment: " + rs.getString("payment") + "\n";
                            i++;
                        }

                    }
                } catch (SQLException throwables) {
                    Toast.makeText(getApplicationContext(), throwables.toString(), Toast.LENGTH_SHORT).show();
                }
                //opening the child activity of QueryActivity2
                openChildActivity();
            } else {
                // if date1 one bigger than date2 then range is wrong
                Toast.makeText(getApplicationContext(),
                        "Range: False", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void openChildActivity() {
        Intent intent = new Intent(this, QueryActivity2Child.class);
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