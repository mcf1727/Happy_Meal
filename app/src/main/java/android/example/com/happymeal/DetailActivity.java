package android.example.com.happymeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.happymeal.data.AppDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import android.example.com.happymeal.data.Nutrient;
import android.example.com.happymeal.data.Nutrients;
import android.example.com.happymeal.data.Nutrition;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.example.com.happymeal.utilities.NutritionJsonUtils;

import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_FOOD_TO_SEARCH = "android.example.com.happymeal.EXTRA_FOOD_TO_SEARCH";
    private static final String RECIPE_BASE_URL = "https://api.edamam.com/";
    private static final String FOOD_SHARE_HASHTAG = "#HappyMeal #Edamam";
    //https://api.edamam.com/api/nutrition-data?app_id=${YOUR_APP_ID}&app_key=${YOUR_APP_KEY}&ingr=1%20large%20apple
    //https://api.edamam.com/api/nutrition-data?app_id=1ac5019d&app_key=b372563e9a44d1f0f4e5b39d0a4c21c9&ingr=1%20large%20apple

    private static boolean NUTRITION_SAVED;

    String foodToShare;
    Nutrition nutrition;
    String foodToSearch;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(EXTRA_FOOD_TO_SEARCH)) {
                foodToSearch = intentThatStartedThisActivity.getStringExtra(EXTRA_FOOD_TO_SEARCH);
            } else {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                foodToSearch = sharedPreferences.getString("recent_nutrition", "NoRecentNutrition");
            }
            if (foodToSearch != null) {
                searchFood(foodToSearch);
                setTitle(foodToSearch);
            }
        }

        setUpNutritionDetailViewModel();
    }

    private void setUpNutritionDetailViewModel() {

    }

    private void searchFood(String foodToSearch) {
        if (isOnline()) {
            //Service Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RECIPE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NutritionJsonUtils serviceJson = retrofit.create(NutritionJsonUtils.class);
            Call<Nutrition> callJson = serviceJson.getNutritionByFood("1ac5019d", "b372563e9a44d1f0f4e5b39d0a4c21c9", foodToSearch);

            callJson.enqueue(new Callback<Nutrition>() {
                @Override
                public void onResponse(Call<Nutrition> call, Response<Nutrition> response) {
                    //TODO when result can't be found / enter nothing / enter espace
                    nutrition = response.body();
                    float calories = nutrition.getCalories();
                    String[] dietLabels = nutrition.getDietLabels();
                    Nutrients nutrients = nutrition.getNutrients();

                    if (String.valueOf(calories) != null && dietLabels.length != 0 && nutrients != null) {

                        nutrition.setFood(foodToSearch);
                        nutrition.setMainDietLabel(dietLabels[0]);
                        foodToShare = foodToSearch + "\n" + "Calories : " + calories + " Kcal" + "\n" + "Diet label : " + dietLabels[0] + "\n" + FOOD_SHARE_HASHTAG;

                        Nutrient[] objectNutrients = new Nutrient[31];
                        objectNutrients[0] = nutrients.getENERC_KCAL();
                        objectNutrients[1] = nutrients.getFAT();
                        objectNutrients[2] = nutrients.getFASAT();
                        objectNutrients[3] = nutrients.getFAMS();
                        objectNutrients[4] = nutrients.getFAPU();
                        objectNutrients[5] = nutrients.getCHOCDF();
                        objectNutrients[6] = nutrients.getFIBTG();
                        objectNutrients[7] = nutrients.getSUGAR();
                        objectNutrients[8] = nutrients.getPROCNT();
                        objectNutrients[9] = nutrients.getCHOLE();
                        objectNutrients[10] = nutrients.getNA();
                        objectNutrients[11] = nutrients.getCA();
                        objectNutrients[12] = nutrients.getMG();
                        objectNutrients[13] = nutrients.getK();
                        objectNutrients[14] = nutrients.getFE();
                        objectNutrients[15] = nutrients.getZN();
                        objectNutrients[16] = nutrients.getP();
                        objectNutrients[17] = nutrients.getVITA_RAE();
                        objectNutrients[18] = nutrients.getVITC();
                        objectNutrients[19] = nutrients.getTHIA();
                        objectNutrients[20] = nutrients.getRIBF();
                        objectNutrients[21] = nutrients.getNIA();
                        objectNutrients[22] = nutrients.getVITB6A();
                        objectNutrients[23] = nutrients.getFOLDFE();
                        objectNutrients[24] = nutrients.getFOLFD();
                        objectNutrients[25] = nutrients.getFOLAC();
                        objectNutrients[26] = nutrients.getVITB12();
                        objectNutrients[27] = nutrients.getVITD();
                        objectNutrients[28] = nutrients.getTOCPHA();
                        objectNutrients[29] = nutrients.getVITK1();
                        objectNutrients[30] = nutrients.getWATER();

                        String[] stringNutrients = new String[31];
                        String[] labelStringNutrients = new String[31];
                        float[] quantityStringNutrients = new float[31];
                        String[] unitStringNutrients = new String[31];

                        for (int i = 0; i < 31; i++) {
                            if (objectNutrients[i] != null) {
                                labelStringNutrients[i] = objectNutrients[i].getLabel();
                                //TODO have only 2 numbers after .
                                quantityStringNutrients[i] = objectNutrients[i].getQuantity();
                                unitStringNutrients[i] = objectNutrients[i].getUnit();
                                stringNutrients[i] = labelStringNutrients[i] + " : " + quantityStringNutrients[i] + " " + unitStringNutrients[i];
                                //Log.d("testss", stringNutrients[i]);
                            } else {
                                stringNutrients[i] = "Lack of information for this nutrient";
                            }
                        }

                        TextView detailCaloriesTextView = findViewById(R.id.tv_detail_calories);
                        TextView detailDietLabelTextView = findViewById(R.id.tv_detail_diet_label);
                        RecyclerView mRecyclerView = findViewById(R.id.rv_detail_nutrients);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        NutrientAdapter mNutrientAdapter = new NutrientAdapter();
                        mRecyclerView.setAdapter(mNutrientAdapter);

                        detailCaloriesTextView.setText("Calories : " + calories + " Kcal");
                        detailDietLabelTextView.setText("Diet label : " + dietLabels[0]);
                        mNutrientAdapter.setNutrientData(stringNutrients);

                        // Save this food as the recent nutrition by SharedPreference
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(stringNutrients);
                        editor.putString("recent_nutrition", foodToSearch)
                                .putString("nutrients_widget", json);
                        editor.apply();

                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(DetailActivity.this);
                        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(DetailActivity.this, NutrientWidgetProvider.class));
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget_nutrients_list);
                        NutrientWidgetProvider.updateNutritionWidgets(DetailActivity.this, appWidgetManager,appWidgetIds);

                    } else {
                        Toast.makeText(DetailActivity.this, "Your entered data isn't correct or it can't be found", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<Nutrition> call, Throwable t) {
                    Log.d(LOG_TAG, t.getMessage());
                }
            });
        } else {
            Toast.makeText(this,  getString(R.string.NO_INTERNET_TOAST), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Check if the internet connection is available.
     * @return Boolean (true when there is internet connection, others return false)
     */
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        //TODO replace menu option icon
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        NUTRITION_SAVED = mDb.nutritionDao().loadNutritionByFood(foodToSearch) != null;
                        if (!NUTRITION_SAVED) {
                            mDb.nutritionDao().insertNutrition(nutrition);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, "Food saved", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, "The food has already been saved", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                return true;

            case R.id.delete:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        NUTRITION_SAVED = mDb.nutritionDao().loadNutritionByFood(foodToSearch) != null;
                        if (NUTRITION_SAVED) {
                            Nutrition nutritionToDelete = mDb.nutritionDao().loadNutritionByFood(foodToSearch);
                            mDb.nutritionDao().deleteNutrition(nutritionToDelete);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, "Food deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, "Can't delete the food that has not been saved", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                return true;

            case R.id.share:
                shareText(foodToShare);
                Log.d("log", "share");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Function to share Movie information
     *
     * @param foodToShare The information to share about the movie
     */
    private void shareText(String foodToShare) {
        String mimeType = "text/plain";
        String title = "Share detail food";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(foodToShare)
                .startChooser();
    }
}