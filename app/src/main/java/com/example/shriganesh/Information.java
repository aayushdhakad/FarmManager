package com.example.shriganesh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Information extends AppCompatActivity {

    ArrayList<Field> info;
    RecyclerViewforInformation adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvFields = (RecyclerView) findViewById(R.id.rvFields);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        // Initialize contacts
        info = new ArrayList<Field>();
        info.add(new Field("Labour"));
        info.add(new Field("Fertilizer/Seed"));
        info.add(new Field("Other Expenses"));
        info.add(new Field("Sell"));
        info.add(new Field("Analysis"));
        // Create adapter passing in the sample user data
        adapter = new RecyclerViewforInformation(info);
        // Attach the adapter to the recyclerview to populate items
        rvFields.setAdapter(adapter);
        // Set layout manager to position the items
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rvFields.setLayoutManager(gridLayoutManager);

    }


}