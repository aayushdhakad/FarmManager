package com.example.shriganesh;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {LVField.class,LVFertilizer.class,Field.class},version = 1)
public abstract class Database extends RoomDatabase {
    private static final String DB_NAME = "db";
    private static Database instance;
    public static synchronized Database getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Database.class,DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract DaoLV daoLV();
}
