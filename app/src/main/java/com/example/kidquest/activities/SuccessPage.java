package com.example.kidquest.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.adapters.RewardAd;
import com.example.kidquest.objects.RewAll_assembly;
import com.example.kidquest.objects.RewardAll;
import com.example.kidquest.objects.RewardUser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SuccessPage extends AppCompatActivity {
    RecyclerView rewardsRecycler;
    ImageView SucAct;
    ImageView SearchAct;
    ImageView UserAct;
    static ImageView rewardDescImg;
    RewardAd rewardAd;
    static TextView rewardDescTitle;
    static TextView rewardDesctext;
    ConstraintLayout menue;
    static ConstraintLayout rewardDesc,hideRew;
    SharedPreferences preferences;
    static String token;
    static List<RewAll_assembly> rewardsList = new ArrayList<>();
    static List<RewAll_assembly> userewardsList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_lay);
        preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
        token = preferences.getString(getString(R.string.token),"null_token");
        SucAct = findViewById(R.id.successButton);
        SearchAct = findViewById(R.id.all_quests);
        UserAct = findViewById(R.id.userImg);
        menue = findViewById(R.id.menueG);
        DownAll downAll = new DownAll();
        DownUser downUser = new DownUser();
        GO go = new GO();
        final Handler mainThread = new Handler();
        final QuestStartPage.CountingListener listener = new QuestStartPage.CountingListener() {
            @Override
            public void countingDone() {
                setRewards(rewardsList,userewardsList, token);
            }

        };
        Thread t_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                downAll.start();
                try {
                    downAll.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                downUser.start();
                try {
                    downUser.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }go.start();
                mainThread.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.countingDone();
                    }
                },100);
            }
        });
        t_thread.start();
        rewardDesc = findViewById(R.id.rewardDesc);
        rewardDescImg = findViewById(R.id.rewardDescImg);
        rewardDescTitle = findViewById(R.id.TitleRew);
        rewardDesctext = findViewById(R.id.DescRew);
        rewardDesc.setTranslationY(3000);
        hideRew = findViewById(R.id.HiderRew);
        hideRew.setVisibility(View.GONE);
    }
    public static void showReward(Bitmap bmp, String title, String text){
        hideRew.setVisibility(View.VISIBLE);
        rewardDescImg.setImageBitmap(bmp);
        rewardDescTitle.setText(title);
        rewardDesctext.setText(text);
    rewardDesc.animate().translationY(0).setDuration(500);
    }
    public void HideRewDesc(View view){
        hideRew.setVisibility(View.GONE);
        rewardDesc.animate().translationY(3000).setDuration(500);
    }
    public void SearchQuests(View view){
        Intent intent = new Intent(this, ChoiceQuest.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) this,
                new Pair<View, String>(SucAct, "success_trans"),
                new Pair<View, String>(SearchAct, "search_trans"),
                new Pair<View, String>(menue, "menue_trans")
        );
        this.startActivity(intent, options.toBundle());
    }
    public void UserClick(View view){
        Intent intent = new Intent(this, User_Profile.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) this,
                new Pair<View, String>(UserAct, "user_trans"),
                new Pair<View, String>(SucAct, "success_trans"),
                new Pair<View, String>(menue, "menue_trans")
        );
        this.startActivity(intent, options.toBundle());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,SuccessPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }
    private void setRewards(List<RewAll_assembly> rewardsList,List<RewAll_assembly> userewardsList,String token){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rewardsRecycler = findViewById(R.id.rewardsRW);
        rewardsRecycler.setLayoutManager(layoutManager);
        rewardAd = new RewardAd(this,rewardsList,userewardsList, token);
        rewardsRecycler.setAdapter(rewardAd);
    }
    public class DownAll extends Thread{
        @Override
        public void run() {
            ApiServise api = RetroClient.getApiServise();
            Call<RewardAll> call = api.getAllRewards(token);
            try {
                rewardsList = call.execute().body().getAwardTypes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }public class DownUser extends Thread{
        @Override
        public void run() {
            ApiServise api = RetroClient.getApiServise();
            Call<RewardUser> call = api.getUserRewards(token);
            try {
                userewardsList = call.execute().body().getUserAwards();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }class GO extends Thread {
        @Override
        public void run() {
        }
    }
}
