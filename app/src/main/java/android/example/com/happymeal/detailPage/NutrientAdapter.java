package android.example.com.happymeal.detailPage;

import android.content.Context;
import android.example.com.happymeal.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.NutrientAdapterViewHolder>{

    private String[] mNutrients;

    public NutrientAdapter() { }

    @NonNull
    @Override
    public NutrientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.nutrient_list_item;

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NutrientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientAdapterViewHolder holder, int position) {
        String nutrient = mNutrients[position];
        holder.mNutrientTextView.setText(nutrient);
    }

    @Override
    public int getItemCount() {
        if ( mNutrients == null ) return 0;
        return mNutrients.length;
    }

    public static class NutrientAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNutrientTextView;

        public NutrientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mNutrientTextView = itemView.findViewById(R.id.tv_nutrient_list_item);
        }
    }

    public void setNutrientData(String[] nutrients) {
        mNutrients = nutrients;
        notifyDataSetChanged();
    }
}
