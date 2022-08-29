package com.example.kidquest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.activities.SuccessPage;
import com.example.kidquest.objects.RewAll_assembly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RewardAd extends RecyclerView.Adapter<RewardAd.RewardsViewHolder>{
    Context context;
    String token;
    String descript;
    List<RewAll_assembly> rewardsList = new ArrayList<>();
    List<RewAll_assembly> userewardsList = new ArrayList<>();
    ColorMatrix matrix = new ColorMatrix();

    public RewardAd(Context context,List<RewAll_assembly> rewardsList,List<RewAll_assembly> userewardsList,String token) {
        this.context = context;
        this.rewardsList = rewardsList;
        this.userewardsList = userewardsList;
        this.token = token;
    }

    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View RewardItems = LayoutInflater.from(context).inflate(R.layout.reward_item, parent,false);
        return new RewardAd.RewardsViewHolder(RewardItems);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsViewHolder holder, int position) {
int posit=position;
        ApiServise api = RetroClient.getApiServise();

            Call<ResponseBody> callimg = api.retrieveImageData(rewardsList.get(position).getPreviewId(),token);
            callimg.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.code()==200) {
                            try {
                                byte[] byteArray = response.body().bytes();
                                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                descript = rewardsList.get(posit).getDescription();
                                holder.title.setText(rewardsList.get(posit).getName());
                                holder.rewardImg.setImageBitmap(bmp);
                                if(userewardsList.size()==0){
                                    holder.rewardImg.setAlpha(0.4F);
                                    matrix.setSaturation(0);
                                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                    holder.rewardImg.setColorFilter(filter);
                                    holder.title.setAlpha(0.4F);
                                    holder.lay.setEnabled(false);
                                }else {
                                    int colorF=0;
                                for(RewAll_assembly user: userewardsList){
                                    if(rewardsList.get(posit).getId().equals(user.getId())){
                                        colorF++;
                                        break;
                                    }else {
                                    }
                                }
                                if(colorF==0){
                                    matrix.setSaturation(0);
                                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                    holder.rewardImg.setColorFilter(filter);
                                    holder.rewardImg.setAlpha(0.4F);
                                    holder.title.setAlpha(0.4F);
                                    holder.lay.setEnabled(false);
                                }
                                }
                            }catch (IOException e) {
                                holder.rewardImg.setImageResource(context.getResources().getIdentifier
                                        ("globe", "drawable",context.getPackageName()));
                            }

                        }else {
                            Toast.makeText(context, "Попробуйте зайти позже", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuccessPage.showReward(((BitmapDrawable)holder.rewardImg.getDrawable()).getBitmap(),rewardsList.get(posit).getName(),rewardsList.get(posit).getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardsList.size();
    }

    public class RewardsViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout lay;
        ImageView rewardImg;
        TextView title;
        public RewardsViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardImg = itemView.findViewById(R.id.rewardImg);
            lay = itemView.findViewById(R.id.shadow_lay);
            title = itemView.findViewById(R.id.rewardName);
        }
    }
}
