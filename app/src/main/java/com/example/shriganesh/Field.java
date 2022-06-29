package com.example.shriganesh;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "field")
class Field {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="full_name")
    private String mName;


    public Field(String name) {
       mName = name;
    }

    public String getName() {
        return mName;
    }


    public static ArrayList<Field> createFieldList() {
        ArrayList<Field> fields = new ArrayList<Field>();
        return fields;
    }
}
