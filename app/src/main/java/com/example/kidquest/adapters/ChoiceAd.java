package com.example.kidquest.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.activities.ChooseImg;
import com.example.kidquest.objects.Answer_assembly;
import com.example.kidquest.objects.Question_assembly;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoiceAd extends RecyclerView.Adapter<ChoiceAd.ChoiceViewHolder>{
    Context context;
    List<Answer_assembly> answers;
    List<Question_assembly> questionsList;
    String token;
    Bitmap bmp;
    byte[] byteArray;

    public ChoiceAd(Context context, List<Answer_assembly> answers, String token, List<Question_assembly> questionsList) {
        this.context = context;
        this.answers = answers;
        this.token = token;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public ChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ChooseItems = LayoutInflater.from(context).inflate(R.layout.choice_item, parent,false);
        return new ChoiceAd.ChoiceViewHolder(ChooseItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceViewHolder holder, int position) {
        int posit=position;
        ApiServise api = RetroClient.getApiServise();
        Call<ResponseBody> call = api.retrieveImageData(answers.get(position).getPreviewId(),token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code()==200) {
                        try {
                            holder.loader.setVisibility(View.GONE);
                            byteArray = response.body().bytes();
                            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            holder.imageView.setImageBitmap(bmp);
                        }catch (IOException e) {
                            Log.i("E", e.getMessage());
                        }

                    }else {
                        Toast.makeText(context, "Попробуйте позже", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.chooseImg.CheckPage(answers.get(posit).getRelev());
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ChoiceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        GifImageView loader;
        ChooseImg chooseImg;
        public ChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            chooseImg = (ChooseImg)itemView.getContext();
            loader = itemView.findViewById(R.id.loaderChoice);
            imageView = itemView.findViewById(R.id.imageToRec);
        }
    }
}
