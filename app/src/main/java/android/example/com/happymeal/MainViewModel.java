package android.example.com.happymeal;

//import android.app.Application;
//import android.example.com.happymeal.data.AppDatabase;
//import android.example.com.happymeal.data.Nutrient;
//import android.example.com.happymeal.data.Nutrition;
//import android.graphics.Movie;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;

//public class MainViewModel extends AndroidViewModel {
//
//    private static final String TAG = MainViewModel.class.getSimpleName();
//
//    private LiveData<Nutrition[]> mNutritions;
//
//    public MainViewModel(@NonNull Application application) {
//        super(application);
//
//        AppDatabase database = AppDatabase.getInstance(this.getApplication());
//        Log.d(TAG, "Actively retrieving the foods from the Database");
//        mNutritions = database.nutritionDao().loadAllNutritions();
//    }
//
//    public LiveData<Nutrition[]> getNutritions() { return mNutritions; }
//}
