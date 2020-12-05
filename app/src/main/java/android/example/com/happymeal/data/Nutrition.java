package android.example.com.happymeal.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutrition")
public class Nutrition {
    @ColumnInfo(name = "calories")
    private float calories;
    @Ignore
    private String[] dietLabels;
    @Ignore
    private Nutrients totalNutrients;
    @ColumnInfo(name = "food")
    private String food;
    @ColumnInfo(name = "main_diet_label")
    private String mainDietLabel;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Nutrition() {}

    public float getCalories() { return  calories; }
    public void setCalories(float calories) { this.calories = calories; }

    public String[] getDietLabels() { return dietLabels; }
    public void setDietLabels(String[] dietLabels) { this.dietLabels = dietLabels; }

    public Nutrients getNutrients() { return totalNutrients; }
    public void setNutrients(Nutrients totalNutrients) { this.totalNutrients = totalNutrients; }

    public String getFood() { return food; }
    public void setFood(String food) { this.food = food; }

    public String getMainDietLabel() { return mainDietLabel; }
    public void setMainDietLabel(String mainDietLabel) { this.mainDietLabel = mainDietLabel; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
