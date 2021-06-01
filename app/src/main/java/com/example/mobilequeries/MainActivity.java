package com.example.mobilequeries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    // Connection for Azure SQL Server
    public Connection con;
    // @param date and @param passNumber will be used to store 1st query result
    public static String[] date = new String[5];
    public static String[] passNumber = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find button in .xml file
        Button button = findViewById(R.id.query1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // connecting to Azure
                    con = connect_azure();
                    if (con == null) {
                        // if connection failed, internet not turned on or unavailable
                        Toast.makeText(getApplicationContext(), "CHECK INTERNET!", Toast.LENGTH_SHORT).show();
                    } else {
                        // if connection successful query executed
                        String query = "SELECT TOP(5) SUM(output.passenger_num) as PassNum ,output.start_date\n" +
                                "FROM [output]\n" +
                                "GROUP BY start_date\n" +
                                "ORDER BY PassNum DESC  ";
                        // creating statement object to be executed
                        Statement stmt = con.createStatement();
                        // preparing result set object
                        ResultSet rs = stmt.executeQuery(query);

                        int i = 0;
                        // adding result set info to @param date and @param passNumber
                        while (rs.next()) {
                            date[i] = rs.getString("start_date");
                            passNumber[i] = rs.getString("PassNum");
                            i++;
                        }
                    }
                } catch (SQLException throwables) {
                    Toast.makeText(getApplicationContext(), throwables.toString(), Toast.LENGTH_SHORT).show();
                }

                // beautifying
                for (int i = 0; i < date.length; i++) {
                    System.out.println("Date : " + date[i] + " Passanger Number: " + passNumber[i]);
                }

                // when button pressed, switch to another activity for result
                openActivity1();

                try {
                    // close SQL connection
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // configuring button for 2nd query
        Button button2 = findViewById(R.id.query2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        // configuring button for 3rd query
        Button button3 = findViewById(R.id.query3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });
    }

    // switching to other activities
    public void openActivity1() {
        Intent intent = new Intent(this, QueryActivity1.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, QueryActivity2.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent = new Intent(this, QueryActivity3.class);
        startActivity(intent);
    }

    public Connection connect_azure() {
        // initializing ThreadPolicy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // applying the policy
        StrictMode.setThreadPolicy(policy);
        // creating a null connection
        Connection connection = null;
        // creating a null connection string
        String ConnectionURL = null;
        try {
            // indicating that a connection is being established
            Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
            // configuring the driver
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // configuring the connection to database
            ConnectionURL = "database:taxi";
            // connecting to the SQL database
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