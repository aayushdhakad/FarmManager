package com.example.shriganesh;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DaoLV {
    @Query("Select * from lvField")
    List<LVField> getAll();

    @Query("Select * from fertilizer")
    List<LVFertilizer> getAllFertilizer();

    @Query("Select * from field")
    List<Field> getAllFields();

    @Insert
    void insert(LVField lvField);

    @Insert
    void insert(LVFertilizer lvFertilizer);

    @Insert
    void insert(Field field);

    @Delete
    void delete(LVField lvField);

    @Delete
    void delete(LVFertilizer lvFertilizer);

    @Delete
    void delete(Field field);
}
