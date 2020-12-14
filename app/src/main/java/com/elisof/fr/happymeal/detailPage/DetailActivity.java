package com.elisof.fr.happymeal.detailPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.elisof.fr.happymeal.AppExecutors;
import com.elisof.fr.happymeal.R;
import com.elisof.fr.happymeal.data.AppDatabase;
import com.elisof.fr.happymeal.widget.NutrientWidgetProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import com.elisof.fr.happymeal.data.Nutrient;
import com.elisof.fr.happymeal.data.Nutrients;
import com.elisof.fr.happymeal.data.Nutrition;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.elisof.fr.happymeal.utilities.NutritionJsonUtils;

import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD_TO_SEARCH = "android.example.com.happymeal.EXTRA_FOOD_TO_SEARCH";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final String APP_ID = "INSERT_YOUR_APP_ID";
    private static final String APP_KEY = "INSERT_YOUR_APP_KEY";
    private static final String RECIPE_BASE_URL = "https://api.edamam.com/";
    private static final String FOOD_SHARE_HASHTAG = "#HappyMeal #Edamam";

    private static boolean NUTRITION_SAVED;

    String foodToShare;
    Nutrition nutrition;
    String foodToSearch;
    private AppDatabase mDb;

    ProgressBar mProgressBar;
    private int mProgressStatus = 0;

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
                foodToSearch = sharedPreferences.getString(getString(R.string.RECENT_NUTRITION), getString(R.string.NO_RECENT_NUITRITION));
            }
            if (foodToSearch != null) {
                searchFood(foodToSearch);
                setTitle(foodToSearch);
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressBar = findViewById(R.id.progress_bar_detail);
        ProgressAsyncTask mProgressAsyncTask;
        mProgressAsyncTask = new ProgressAsyncTask();
        mProgressAsyncTask.execute();

    }

    class ProgressAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            int max = mProgressBar.getMax();
            while (mProgressStatus < max) {
                int add = max/100;
                mProgressStatus = add + mProgressBar.getProgress();
                publishProgress(mProgressStatus);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            mProgressBar.setProgress(values[0]);
        }
    }

    private void searchFood(String foodToSearch) {
        if (isOnline()) {
            //Service Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RECIPE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NutritionJsonUtils serviceJson = retrofit.create(NutritionJsonUtils.class);
            Call<Nutrition> callJson = serviceJson.getNutritionByFood(APP_ID, APP_KEY, foodToSearch);

            callJson.enqueue(new Callback<Nutrition>() {
                @Override
                public void onResponse(Call<Nutrition> call, Response<Nutrition> response) {
                    nutrition = response.body();
                    float calories = nutrition.getCalories();
                    String[] dietLabels = nutrition.getDietLabels();
                    Nutrients nutrients = nutrition.getNutrients();

                    if (String.valueOf(calories) != null && dietLabels.length != 0 && nutrients != null) {

                        String caloriesLine = getString(R.string.CALORIES_TITLE) + calories + getString(R.string.CALORIES_UNIT);
                        String dietLabelLine = getString(R.string.DIET_LABEL_TITLE) + dietLabels[0];

                        nutrition.setFood(foodToSearch);
                        nutrition.setMainDietLabel(dietLabels[0]);
                        foodToShare = foodToSearch + "\n" + caloriesLine + "\n" + dietLabelLine + "\n" + FOOD_SHARE_HASHTAG;

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
                                quantityStringNutrients[i] = objectNutrients[i].getQuantity();
                                unitStringNutrients[i] = objectNutrients[i].getUnit();
                                stringNutrients[i] = labelStringNutrients[i] + " : " + quantityStringNutrients[i] + " " + unitStringNutrients[i];
                            } else {
                                stringNutrients[i] = getString(R.string.LACK_INFORMATION_NUTRIENT);
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

                        mProgressBar.setVisibility(View.GONE);
                        detailCaloriesTextView.setText(caloriesLine);
                        detailDietLabelTextView.setText(dietLabelLine);
                        mNutrientAdapter.setNutrientData(stringNutrients);

                        // Save this food as the recent nutrition by SharedPreference
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(stringNutrients);
                        editor.putString(getString(R.string.RECENT_NUTRITION), foodToSearch)
                                .putString(getString(R.string.NUTRIENTS_WIDGET), json);
                        editor.apply();

                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(DetailActivity.this);
                        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(DetailActivity.this, NutrientWidgetProvider.class));
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget_nutrients_list);
                        NutrientWidgetProvider.updateNutritionWidgets(DetailActivity.this, appWidgetManager,appWidgetIds);

                    } else {
                        Toast.makeText(DetailActivity.this, getString(R.string.ENTERED_DATA_NOT_CORRECT_OR_CAN_NOT_BE_FOUOND), Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(DetailActivity.this, getString(R.string.FOOD_SAVED), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, getString(R.string.FOOD_ALREADY_SAVED), Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(DetailActivity.this, getString(R.string.FOOD_DELETED), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, getString(R.string.CAN_NOT_DELETE_NOTE_NOT_BEEN_SAVED), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                return true;

            case R.id.share:
                shareText(foodToShare);
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