package com.elisof.fr.happymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.elisof.fr.happymeal.data.AppDatabase;
import com.elisof.fr.happymeal.data.Nutrition;
import com.elisof.fr.happymeal.detailPage.DetailActivity;
import com.elisof.fr.happymeal.searchPage.SearchActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity implements NutritionAdapter.ListItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private AppDatabase mDb;
    NutritionAdapter mNutritionAdapter;
    private AdView mAdView;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        RecyclerView mRecyclerView = findViewById(R.id.rv_main_food_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNutritionAdapter = new NutritionAdapter(this);
        mRecyclerView.setAdapter(mNutritionAdapter);

        FloatingActionButton searchFab = findViewById(R.id.search_fab);
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentToStartSearchActivity = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentToStartSearchActivity);
            }
        });
        setUpMainViewModel();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        // Force a crash to test implementation Crashlytics
//        Button crashButton = new Button(this);
//        crashButton.setText("Crash!");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    private void setUpMainViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(getApplication());
        final MainViewModel viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        final Observer<Nutrition[]> nutritionObserver = new Observer<Nutrition[]>() {
            @Override
            public void onChanged(Nutrition[] nutritions) {
                View emptyView = findViewById(R.id.empty_view_main);
                if (nutritions.length == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
                Log.d(TAG, "Receiving database update from LiveData in ViewModel");
                mNutritionAdapter.setNutritionData(nutritions);
            }
        };
        viewModel.getNutritions().observe(this, nutritionObserver);
    }

    @Override
    public void onListItemClick(String foodToSearch) {
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra(DetailActivity.EXTRA_FOOD_TO_SEARCH, foodToSearch);
        startActivity(intentToStartDetailActivity);
    }
}