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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Field> fields;
    RecyclerViewAdapter adapter;
    Database db;
    DaoLV daoLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Database.getInstance(this);
        daoLV = db.daoLV();

        RecyclerView rvFields = (RecyclerView) findViewById(R.id.rvFields);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                fields = daoLV.getAllFields();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new RecyclerViewAdapter(fields);
                        rvFields.setAdapter(adapter);
                    }
                });
            }
        });


        // Set layout manager to position the items
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rvFields.setLayoutManager(gridLayoutManager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddField.class);
                someActivityResultLauncher.launch(intent);
            }
        });


    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String newField = data.getStringExtra("message_return");

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                db.daoLV().insert(new Field(newField));
                                List<Field> changes = db.daoLV().getAllFields();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fields.clear();
                                        fields.addAll(changes);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });

                    }
                }
            });

}