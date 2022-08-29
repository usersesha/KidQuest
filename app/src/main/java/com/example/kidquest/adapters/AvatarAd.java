package com.example.kidquest.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.R;
import com.example.kidquest.activities.User_Profile;

import java.util.List;

public class AvatarAd extends RecyclerView.Adapter<AvatarAd.AvaViewHolder>{
    Context context;
    List<String> avalist;
    ImageView avatar;
    ConstraintLayout Avaset;

    public AvatarAd(Context context, List<String> avalist, ImageView avatar,ConstraintLayout Avaset) {
        this.context = context;
        this.avalist = avalist;
        this.avatar = avatar;
        this.Avaset = Avaset;
    }

    @NonNull
    @Override
    public AvaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View avItems = LayoutInflater.from(context).inflate(R.layout.avatar, parent,false);
        return new AvatarAd.AvaViewHolder(avItems);
    }

    @Override
    public void onBindViewHolder(@NonNull AvaViewHolder holder, int position) {
        int posit = position;
        int dr = context.getResources().getIdentifier(avalist.get(position), "drawable", context.getPackageName());
        holder.ava_set.setImageResource(dr);
        holder.ava_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatar.setImageResource(dr);
                Avaset.animate().translationY(3000).setDuration(1000);
                SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preferences),context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(context.getString(R.string.avatar), avalist.get(posit));
                editor.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return avalist.size();
    }

    public final class AvaViewHolder extends RecyclerView.ViewHolder {
        ImageView ava_set;
        public AvaViewHolder(@NonNull View itemView) {
            super(itemView);
            ava_set = itemView.findViewById(R.id.ava_set);
        }
    }
}
