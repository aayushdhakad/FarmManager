package com.example.shriganesh;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fertilizer")
class LVFertilizer {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name="full_name")
    public String mName;
    @ColumnInfo(name="Information")
    public String mCount;

    public LVFertilizer(String name, String  count) {
        mName = name;
        mCount = count;
    }

    public String getName() {
        return mName;
    }

    public String getCount() {
        return mCount;
    }

    public static ArrayList<LVFertilizer> createLVFieldList() {
        ArrayList<LVFertilizer> entities = new ArrayList<LVFertilizer>();
        return entities;
    }
}

