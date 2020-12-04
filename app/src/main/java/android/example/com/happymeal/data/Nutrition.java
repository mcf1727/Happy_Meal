package android.example.com.happymeal.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(tableName = "nutrition")
public class Nutrition {
//    @ColumnInfo(name = "calories")
    private float calories;
//    @ColumnInfo(name = "dietLabels")
    private String[] dietLabels;
//    @ColumnInfo(name = "totalNutrients")
    private Nutrients totalNutrients;
//    @PrimaryKey
//    private int id;

    public Nutrition() {}

    public float getCalories() { return  calories; }
    public String[] getDietLabels() { return dietLabels; }
    public Nutrients getNutrients() { return totalNutrients; }
}
