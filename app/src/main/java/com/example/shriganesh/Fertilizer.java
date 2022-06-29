package com.example.shriganesh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fertilizer extends AppCompatActivity {

    List<LVFertilizer> entities;
    EntityAdapterFertilizer adapter;
    Database db;
    DaoLV daoLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);
        db = Database.getInstance(this);
        daoLV = db.daoLV();
        RecyclerView rvFields = (RecyclerView) findViewById(R.id.rvEntities);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                entities = daoLV.getAllFertilizer();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Create adapter passing in the sample user data
                        adapter = new EntityAdapterFertilizer(entities);
                        // Attach the adapter to the recyclerview to populate items
                        rvFields.setAdapter(adapter);
                    }
                });
            }
        });

        // Set layout manager to position the items

        rvFields.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Fertilizer.this,AddField.class);
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

                                db.daoLV().insert(new LVFertilizer(newField,"0"));
                                List<LVFertilizer> changes = db.daoLV().getAllFertilizer();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        entities.clear();
                                        entities.addAll(changes);
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        });


                        Log.d("mytag",newField);

                    }
                }
            });

}