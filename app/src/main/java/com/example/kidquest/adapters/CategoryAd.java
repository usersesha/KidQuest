package com.example.kidquest.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.R;
import com.example.kidquest.activities.ChoiceQuest;
import com.example.kidquest.objects.Category;

import java.util.Arrays;

public class CategoryAd extends RecyclerView.Adapter<CategoryAd.CategotyViewHolder>{
    Context context;
    Category[] Categories;

    public CategoryAd(Context context, Category[] categories) {
        this.context = context;
        Categories = categories;
    }

    @NonNull
    @Override
    public CategotyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View CategoryItems = LayoutInflater.from(context).inflate(R.layout.category_item, parent,false);
        return new CategotyViewHolder(CategoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategotyViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.b.setText( Categories[position].getName());
    holder.b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ChoiceQuest.ShowByCategory(Categories[position].getId());
        }
    });
    }

    @Override
    public int getItemCount() {
        return (int) Arrays.stream(Categories).count();
    }

    public static final class CategotyViewHolder extends RecyclerView.ViewHolder{
        Button b;

        public CategotyViewHolder(@NonNull View itemView) {
            super(itemView);
            b = itemView.findViewById(R.id.ctitle);
        }
    }

}
