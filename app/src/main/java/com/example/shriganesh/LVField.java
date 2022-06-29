package com.example.shriganesh;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "lvField")
class LVField {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name="full_name")
    public String mName;
    @ColumnInfo(name="Information")
    public String mCount;

    public LVField(String name, String  count) {
        mName = name;
        mCount = count;
    }

    public String getName() {
        return mName;
    }

    public String getCount() {
        return mCount;
    }

    public static ArrayList<LVField> createLVFieldList() {
        ArrayList<LVField> entities = new ArrayList<LVField>();
        return entities;
    }
}
