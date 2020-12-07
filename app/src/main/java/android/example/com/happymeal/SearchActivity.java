package android.example.com.happymeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
                //Toast.makeText(SearchActivity.this, "start search", Toast.LENGTH_SHORT).show();
                //TODO correct editText
                String foodToSearch = searchEditText.getText().toString();

                if (!foodToSearch.equals("")) {
                    Intent intentToStartDetailActivity = new Intent(SearchActivity.this, DetailActivity.class);
                    intentToStartDetailActivity.putExtra(DetailActivity.EXTRA_FOOD_TO_SEARCH, foodToSearch);
                    startActivity(intentToStartDetailActivity);
                } else {
                    Toast.makeText(SearchActivity.this, "Please enter the food", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_search);
        //toolbar.inflateMenu(R.menu.menu_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}