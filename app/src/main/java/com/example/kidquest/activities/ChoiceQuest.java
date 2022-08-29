package com.example.kidquest.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.NetworkCheck;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.adapters.CategoryAd;
import com.example.kidquest.adapters.QuestsListAd;
import com.example.kidquest.objects.Category;
import com.example.kidquest.objects.GetCategory;
import com.example.kidquest.objects.Quest_item;
import com.example.kidquest.objects.QuestsAr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoiceQuest extends AppCompatActivity {
    private RecyclerView categoryRecycler,questRecycler;
    private ImageView SucAct, SearchAct, UserAct;
    private Button all_q;
    private ConstraintLayout menue;
    private SharedPreferences preferences;
    private String token;
    private Handler handler = new Handler();
    static QuestsListAd questsListAd;
    static List<QuestsAr> questsAr = new ArrayList<>();
    public static List<QuestsAr> Save_quests = new ArrayList<>();
    private Category[] Categories;
    private CategoryAd categoryAd;
    private LottieAnimationView failnet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        menue = findViewById(R.id.menueG);
        preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
        token  = preferences.getString(getString(R.string.token),"null_token");
        all_q= findViewById(R.id.show_all_q);
        SucAct = findViewById(R.id.reward);
        SearchAct = findViewById(R.id.questSearch);
        UserAct = findViewById(R.id.userImg);
        failnet = findViewById(R.id.failnetLottie);
        failnet.pauseAnimation();
        if(NetworkCheck.check(getApplicationContext()) == true){
            Save_quests.clear();
            questsAr.clear();
            setCategoryRecycler();
            setQuestRecycler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    all_q.setVisibility(View.VISIBLE);
                    failnet.setVisibility(View.GONE);
                }
            }, 450);
        }
        else {
            failnet.setVisibility(View.VISIBLE);
            failnet.animate().alpha(1).setDuration(500);
            failnet.loop(true);
            failnet.playAnimation();
            all_q.setVisibility(View.INVISIBLE);
        }
    }
    public void setQuestRecycler(){
        ApiServise api = RetroClient.getApiServise();
        Call<Quest_item> call = api.getQuests(token);
        call.enqueue(new Callback<Quest_item>() {
            @Override
            public void onResponse(Call<Quest_item> call, Response<Quest_item> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("Ok")) {
                        questsAr.addAll(Arrays.asList(response.body().getQuestsAr()));
                        Save_quests.addAll(questsAr);
                        setQuests(questsAr);
                    }
                }
            }
            @Override
            public void onFailure(Call<Quest_item> call, Throwable t) {
                Toast.makeText(ChoiceQuest.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setCategoryRecycler(){
        ApiServise api = RetroClient.getApiServise();
        Call<GetCategory> call = api.getCategories(token);
        call.enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("Ok")) {
                        Categories = response.body().getQuestCategories();
                        setCategory(Categories);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {
                Toast.makeText(ChoiceQuest.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        all_q.setVisibility(View.INVISIBLE);
        Intent intent=new Intent(this, ChoiceQuest.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }
    public void SuccessClick(View view){
        Intent intent = new Intent(this, SuccessPage.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) this,
                new Pair<View, String>(SucAct, "success_trans"),
                new Pair<View, String>(SearchAct, "search_trans"),
                new Pair<View, String>(menue, "menue_trans")
        );
        this.startActivity(intent, options.toBundle());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                all_q.setVisibility(View.INVISIBLE);
            }
        }, 350);
    }
    public void UserClick(View view){
        Intent intent = new Intent(this, User_Profile.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) this,
                new Pair<View, String>(UserAct, "user_trans"),
                new Pair<View, String>(SearchAct, "search_trans"),
                new Pair<View, String>(menue, "menue_trans")
        );
        this.startActivity(intent, options.toBundle());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                all_q.setVisibility(View.INVISIBLE);
            }
        }, 350);
    }
    public void ShowAllQ(View view){
        questsAr.clear();
        questsAr.addAll(Save_quests);
        questsListAd.notifyDataSetChanged();
    }
    public static void ShowByCategory(int category){
        questsAr.clear();
        List<QuestsAr> filter = new ArrayList<>();
        for (QuestsAr q: Save_quests){
            if(q.getCategoryId()==category){
                filter.add(q);
            }
        }
        questsAr.addAll(filter);
        questsListAd.notifyDataSetChanged();
    }
    private void setQuests(List<QuestsAr> questsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        questRecycler = findViewById(R.id.Q_items);
        questRecycler.setLayoutManager(layoutManager);
        questsListAd = new QuestsListAd(this,questsList,token);
        questRecycler.setAdapter(questsListAd);
    }
    private void setCategory(Category[] questCategories){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoryRecycler = findViewById(R.id.category);
        categoryRecycler.setLayoutManager(layoutManager);
        categoryAd = new CategoryAd(this,questCategories);
        categoryRecycler.setAdapter(categoryAd);
    }
    public void SearchQuests(View view){
        ShowAllQ(view);
    }
}
