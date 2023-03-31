package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CateringAdapter extends RecyclerView.Adapter<CateringAdapter.ViewHolder> {

    private List<CateringItem> cateringItems;
    private Context context;
    private OnCateringItemClickListener onCateringItemClickListener;

    public CateringAdapter(Context context, List<CateringItem> cateringItems, OnCateringItemClickListener onCateringItemClickListener) {
        this.context = context;
        this.cateringItems = cateringItems;
        this.onCateringItemClickListener = onCateringItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catering_item, parent, false);
        return new ViewHolder(view, onCateringItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CateringItem item = cateringItems.get(position);
        holder.foodItemName.setText(item.getFoodItemName());
        holder.catererName.setText(item.getCatererName());
        holder.catererCharge.setText(String.valueOf(item.getCatererCharge()));
    }

    @Override
    public int getItemCount() {
        return cateringItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView foodItemName, catererName, catererCharge;
        ImageView deleteCateringItemButton;
        OnCateringItemClickListener onCateringItemClickListener;

        public ViewHolder(@NonNull View itemView, OnCateringItemClickListener onCateringItemClickListener) {
            super(itemView);

            foodItemName = itemView.findViewById(R.id.food_item_name);
            catererName = itemView.findViewById(R.id.caterer_name);
            catererCharge = itemView.findViewById(R.id.caterer_charge);
            deleteCateringItemButton = itemView.findViewById(R.id.delete_catering_item_button);
            deleteCateringItemButton.setOnClickListener(this);
            this.onCateringItemClickListener = onCateringItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onCateringItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnCateringItemClickListener {
        void onItemClick(int position);
    }
}
