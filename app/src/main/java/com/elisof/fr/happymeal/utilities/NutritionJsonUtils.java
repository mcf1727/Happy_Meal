package com.elisof.fr.happymeal.utilities;

import com.elisof.fr.happymeal.data.Nutrition;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NutritionJsonUtils {
    @GET("api/nutrition-data")
    Call<Nutrition> getNutritionByFood(@Query("app_id") String app_id, @Query("app_key") String app_key, @Query("ingr") String food);
}
