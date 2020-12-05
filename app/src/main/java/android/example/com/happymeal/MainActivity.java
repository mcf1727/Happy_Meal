package android.example.com.happymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.com.happymeal.data.AppDatabase;
import android.example.com.happymeal.data.Nutrition;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NutritionAdapter.ListItemClickListener{

    private AppDatabase mDb;
    NutritionAdapter mNutritionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        RecyclerView mRecyclerView = findViewById(R.id.rv_main_food_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNutritionAdapter = new NutritionAdapter(this);
        mRecyclerView.setAdapter(mNutritionAdapter);
//        mNutritionAdapter.setNutritionData(nutritions);

        FloatingActionButton searchFab = findViewById(R.id.search_fab);
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), "toStartSearchActivity", Toast.LENGTH_SHORT).show();
                Intent intentToStartSearchActivity = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentToStartSearchActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Nutrition[] nutritions = mDb.nutritionDao().loadAllNutritions();
                // TODO to simplify by Android Architecture components
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNutritionAdapter.setNutritionData(nutritions);
                    }
                });
            }
        });
    }

    @Override
    public void onListItemClick(String foodToSearch) {
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra(DetailActivity.EXTRA_FOOD_TO_SEARCH, foodToSearch);
        startActivity(intentToStartDetailActivity);
    }
}