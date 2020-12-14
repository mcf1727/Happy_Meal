package com.elisof.fr.happymeal.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NutritionDao {
    @Query("SELECT * FROM nutrition")
    LiveData<Nutrition[]> loadAllNutritions();

    @Insert
    void insertNutrition(Nutrition nutrition);

    @Delete
    void deleteNutrition(Nutrition nutrition);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNutrition(Nutrition nutrition);

    @Query("SELECT * FROM nutrition WHERE food = :food")
    Nutrition loadNutritionByFood(String food);
}
