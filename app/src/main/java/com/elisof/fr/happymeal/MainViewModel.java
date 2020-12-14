package com.elisof.fr.happymeal;

import android.app.Application;
import com.elisof.fr.happymeal.data.AppDatabase;
import com.elisof.fr.happymeal.data.Nutrition;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<Nutrition[]> mNutritions;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the foods from the Database");
        mNutritions = database.nutritionDao().loadAllNutritions();
    }

    public LiveData<Nutrition[]> getNutritions() { return mNutritions; }
}
