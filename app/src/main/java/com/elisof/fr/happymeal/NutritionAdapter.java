package com.elisof.fr.happymeal;

import android.content.Context;

import com.elisof.fr.happymeal.data.Nutrition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.NutritionAdapterViewHolder>{

    private Nutrition[] mNutritions;

    /**
     * An on-click listener that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final ListItemClickListener mOnClickListener;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(String foodToSearch);
    }

    public NutritionAdapter(ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public NutritionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.nutrition_list_item;

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NutritionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionAdapterViewHolder holder, int position) {
        Nutrition nutrition = mNutritions[position];
        String food = nutrition.getFood();
        float calories = nutrition.getCalories();
        String mainDietLabel = nutrition.getMainDietLabel();
        holder.mNutritionFoodTextView.setText(food);
        String caloriesLine = holder.itemView.getContext().getString(R.string.CALORIES_TITLE) + calories +  holder.itemView.getContext().getString(R.string.CALORIES_UNIT);
        String dietLabelLine =  holder.itemView.getContext().getString(R.string.DIET_LABEL_TITLE) + mainDietLabel;
        holder.mNutritionCaloriesTextView.setText(caloriesLine);
        holder.mNutritionDietLabelTextView.setText(dietLabelLine);
    }

    @Override
    public int getItemCount() {
        if (mNutritions == null) return 0;
        return mNutritions.length;
    }

    public class NutritionAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mNutritionFoodTextView;
        public final TextView mNutritionCaloriesTextView;
        public final TextView mNutritionDietLabelTextView;

        public NutritionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mNutritionFoodTextView = itemView.findViewById(R.id.tv_nutrition_list_item_food);
            mNutritionCaloriesTextView = itemView.findViewById(R.id.tv_nutrition_list_item_calories);
            mNutritionDietLabelTextView = itemView.findViewById(R.id.tv_nutrition_list_item_diet_label);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Nutrition clickedNutrition = mNutritions[adapterPosition];
            String foodToSearch = clickedNutrition.getFood();

            mOnClickListener.onListItemClick(foodToSearch);
        }
    }

    public void setNutritionData(Nutrition[] nutritions) {
        if (nutritions != null) {
            mNutritions = nutritions;
            notifyDataSetChanged();
        }
    }
}
