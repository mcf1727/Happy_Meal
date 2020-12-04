package android.example.com.happymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Intent intentToStartDetailActivity = new Intent(SearchActivity.this, DetailActivity.class);
                intentToStartDetailActivity.putExtra(DetailActivity.EXTRA_FOOD_TO_SEARCH, foodToSearch);
                startActivity(intentToStartDetailActivity);
            }
        });
    }

}