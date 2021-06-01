package com.example.mobilequeries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.mobilequeries.MainActivity.date;
import static com.example.mobilequeries.MainActivity.passNumber;

public class QueryActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query1);

        // @param result used for printing the dates
        String[] result = new String[5];
        // preparing the string to be displayed in the ListView
        for (int i = 0; i < date.length; i++) {
            result[i] = (i + 1) + ")\tDate: " + date[i] + "\n"
                    + "\t\t\tPassanger: " + passNumber[i] + "\n";
        }

        // finding the ListView in the .xml file
        final ListView lv = findViewById(R.id.query1_list);
        // making a result list from string array
        final List<String> result_list = new ArrayList<String>(Arrays.asList(result));
        // preparing an adapter for the list
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, result_list);
        // adding content onto the list
        lv.setAdapter(arrayAdapter);

    }
}