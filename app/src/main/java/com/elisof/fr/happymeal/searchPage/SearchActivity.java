package com.elisof.fr.happymeal.searchPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import com.elisof.fr.happymeal.R;
import com.elisof.fr.happymeal.detailPage.DetailActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText searchEditText = findViewById(R.id.et_search);

        Button searchButton = findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodToSearch = searchEditText.getText().toString();

                if (!foodToSearch.equals("")) {
                    Intent intentToStartDetailActivity = new Intent(SearchActivity.this, DetailActivity.class);
                    intentToStartDetailActivity.putExtra(DetailActivity.EXTRA_FOOD_TO_SEARCH, foodToSearch);
                    startActivity(intentToStartDetailActivity);
                } else {
                    Toast.makeText(SearchActivity.this, getString(R.string.PLEASE_ENTER_FOOD), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        overridePendingTransition(R.anim.anim_activity_up, R.anim.anim_activity_stay);
    }
}