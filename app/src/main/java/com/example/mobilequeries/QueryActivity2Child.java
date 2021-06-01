package com.example.mobilequeries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.mobilequeries.QueryActivity2.resultString;

public class QueryActivity2Child extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_activity2_child);


        final ListView lv = findViewById(R.id.query2_result);
        final List<String> result_list = new ArrayList<String>(Arrays.asList(resultString));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,result_list);
        lv.setAdapter(arrayAdapter);
    }
}