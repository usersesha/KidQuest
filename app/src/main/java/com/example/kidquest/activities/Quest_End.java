package com.example.kidquest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.objects.AttemptCreate;
import com.example.kidquest.objects.RewAll_assembly;
import com.example.kidquest.objects.RewardAll;
import com.example.kidquest.objects.RewardUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quest_End extends QuestStartPage {
    LottieAnimationView conf,rew1,rew2,rew3;
    TextView answers,who;
    Integer exist=0, rewId=0;
    Button awards_bott;
    static List<RewAll_assembly> userRewards= new ArrayList<>();
    static List<RewAll_assembly> rewardsList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_end);
        conf = (LottieAnimationView) findViewById(R.id.endQlottie);
        QuestId = getIntent().getIntExtra("QuestId", 0);
        rew1 = (LottieAnimationView) findViewById(R.id.plase1);
        rew2 = (LottieAnimationView) findViewById(R.id.plase2);
        rew3 = (LottieAnimationView) findViewById(R.id.plase3);
        awards_bott= findViewById(R.id.awards_bott);
        awards_bott.setEnabled(false);
        rew1.pauseAnimation();
        rew2.pauseAnimation();
        rew3.pauseAnimation();
        conf.playAnimation();
        who = (TextView) findViewById(R.id.title_qe);
        answers = (TextView) findViewById(R.id.count_ans_end);
        whatsUdo();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void onGeneralfromend(View view){
        counter=0;
        truest=0;
        intent=new Intent(this, ChoiceQuest.class);
        startActivity(intent);
        this.finish();
    }
    public void whatsUdo(){
        if(truest==counter){
            who.setText("Молодец!");
            answers.setText("Все ответы верны!");
            rew2.setVisibility(View.GONE);
            rew3.setVisibility(View.GONE);
            rew1.playAnimation();
            DownUser downUser = new DownUser();
            DownAll downAll = new DownAll();
            GO go = new GO();
            final Handler mainThread = new Handler();
            final QuestStartPage.CountingListener listener = new QuestStartPage.CountingListener() {
                @Override
                public void countingDone() {
                    if(userRewards.size()==0){
                        for (RewAll_assembly rewall: rewardsList){
                            if(rewall.getQuestId().equals(QuestId)){
                                rewId=rewall.getId();
                            }
                        }
                        if (rewId!=0){
                            ApiServise api = RetroClient.getApiServise();
                            SetReward setReward = new SetReward(rewId);
                            Call<SetReward> calll = api.createReward(setReward,token);
                            calll.enqueue(new Callback<SetReward>() {
                                @Override
                                public void onResponse(Call<SetReward> call, Response<SetReward> response) {
                                }
                                @Override
                                public void onFailure(Call<SetReward> call, Throwable t) {
                                }
                            });
                        }
                    }else{
                        for (RewAll_assembly rew: userRewards){
                            if(rew.getQuestId().equals(QuestId)){
                                exist++;
                            }
                        }
                        if (exist<1){
                            for (RewAll_assembly rewall: rewardsList){
                                if(rewall.getQuestId().equals(QuestId)){
                                    rewId=rewall.getId();
                                }
                            }
                        }
                        if (rewId!=0){
                            ApiServise api = RetroClient.getApiServise();
                            SetReward setReward = new SetReward(rewId);
                            Call<SetReward> calll = api.createReward(setReward,token);
                            calll.enqueue(new Callback<SetReward>() {
                                @Override
                                public void onResponse(Call<SetReward> call, Response<SetReward> response) {
                                }
                                @Override
                                public void onFailure(Call<SetReward> call, Throwable t) {

                                }
                            });
                        }
                    }
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
                    }downUser.start();
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
                    },10);
                }
            });
            t_thread.start();

        }else if(truest>(counter/2)){
            who.setText("Хорошая работа!");
            answers.setText(truest+" из "+counter+"\nответов верны!");
            rew2.playAnimation();
            rew1.setVisibility(View.GONE);
            rew3.setVisibility(View.GONE);
        }else{
            who.setText("Вы можете лучше");
            answers.setText(truest+" из "+counter+"\nответов верны!");
            rew3.playAnimation();
            rew2.setVisibility(View.GONE);
            rew1.setVisibility(View.GONE);
        }
        ApiServise apiServise = RetroClient.getApiServise();
        AttemptCreate attempt = new AttemptCreate(QuestId,truest);
        Call<AttemptCreate> call = apiServise.createAttempt(attempt,token);
        call.enqueue(new Callback<AttemptCreate>() {
            @Override
            public void onResponse(Call<AttemptCreate> call, Response<AttemptCreate> response) {
                awards_bott.setEnabled(true);
            }

            @Override
            public void onFailure(Call<AttemptCreate> call, Throwable t) {

            }
        });
        quanList.clear();
        questionsList.clear();
        answersList.clear();
    }
    public class DownUser extends Thread{
        @Override
        public void run() {
            ApiServise api = RetroClient.getApiServise();
            Call<RewardUser> call = api.getUserRewards(token);
            try {
                userRewards = call.execute().body().getUserAwards();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    }
    class GO extends Thread {
        @Override
        public void run() {
        }
    }
    public class SetReward{
        Integer awardTypeId;
        public SetReward(Integer awardTypeId) {
            this.awardTypeId = awardTypeId;
        }
        public Integer getAwardTypeId() {
            return awardTypeId;
        }
       public void setAwardTypeId(Integer awardTypeId) {
            this.awardTypeId = awardTypeId;
        }
    }
}

