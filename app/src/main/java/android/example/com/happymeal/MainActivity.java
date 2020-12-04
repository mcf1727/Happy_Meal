package android.example.com.happymeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}