package com.example.kidquest.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.ApiServise;
import com.example.kidquest.NetworkCheck;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.adapters.AvatarAd;
import com.example.kidquest.objects.AttemptsSt;
import com.example.kidquest.objects.CangePassOld;
import com.example.kidquest.objects.UserNE;
import com.example.kidquest.objects.UserStat;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Profile extends AppCompatActivity{
    private RecyclerView recycler;
    private ImageView SucAct, SearchAct, UserAct;
    private TextView uSQ, uSA, pSq, pSa, UsName, UsMail;
    private int sch;
    private EditText oldP, newP;
    private Button change, next;
    public ImageView avatar;
    public ConstraintLayout menue, Avaset;
    private SharedPreferences preferences;
    private String token, prefAva;
    static List<String> avList = new ArrayList<>();
    static List<AttemptsSt> Anstrue = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        menue = findViewById(R.id.menueG);
        preferences = getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        token = preferences.getString(getString(R.string.token), "null_token");
        prefAva = preferences.getString(getString(R.string.avatar), "ava0");
        Avaset = findViewById(R.id.set_av_panel);
        UsMail = findViewById(R.id.UserMailtext);
        UsName = findViewById(R.id.UserNametext);
        uSQ = findViewById(R.id.questsCount2);
        uSA = findViewById(R.id.score2);
        pSq = findViewById(R.id.questsCount);
        pSa = findViewById(R.id.score);
        Avaset.setTranslationY(3000);
        int dr = getResources().getIdentifier(prefAva, "drawable", getPackageName());
        oldP = findViewById(R.id.oldpass);
        newP = findViewById(R.id.newpass);
        oldP.setTranslationX(-1000);
        newP.setTranslationX(-1000);
        oldP.setEnabled(false);
        newP.setEnabled(false);
        change = findViewById(R.id.change_pass);
        next = findViewById(R.id.changeP);
        next.setTranslationX(300);
        avatar = findViewById(R.id.userImage);
        avatar.setImageResource(dr);
        SucAct = findViewById(R.id.reward);
        SearchAct = findViewById(R.id.questSearch);
        UserAct = findViewById(R.id.userImg);
        if (NetworkCheck.check(getApplicationContext()) == true) {
            ApiServise api = RetroClient.getApiServise();
            Call<UserNE> call = api.getUserInfo(token);
            call.enqueue(new Callback<UserNE>() {
                @Override
                public void onResponse(Call<UserNE> call, Response<UserNE> response) {
                    if (response.isSuccessful()) {
                        UsMail.setText(response.body().getUserInfo().getEmail());
                        UsName.setText(response.body().getUserInfo().getName());
                    }
                }
                @Override
                public void onFailure(Call<UserNE> call, Throwable t) {
                    Toast.makeText(User_Profile.this, "Попробуйте позже", Toast.LENGTH_SHORT).show();
                }
            });
            Call<UserStat> statCall = api.getUserStat(token);
            statCall.enqueue(new Callback<UserStat>() {
                @Override
                public void onResponse(Call<UserStat> statCall, Response<UserStat> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getAttempts().size() != 0) {

                            Anstrue.addAll(response.body().getAttempts());
                            int i = 0, o = 0;
                            for (AttemptsSt at : response.body().getAttempts()) {
                                i += at.getCorrectAnswersCount();
                                o++;
                            }
                            uSQ.setText(String.valueOf(o));
                            uSA.setText(String.valueOf(i));
                            pSa.setText(String.valueOf(i));
                            pSq.setText(String.valueOf(o));
                        } else {
                            uSQ.setText("0");
                            uSA.setText("0");
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserStat> statCall, Throwable t) {
                    Toast.makeText(User_Profile.this, "Попробуйте позже", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_SHORT).show();
        }
    }


    public void SuccessClick(View view){
        Intent intent = new Intent(this, SuccessPage.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) this,
                new Pair<View, String>(SucAct, "success_trans"),
                new Pair<View, String>(UserAct, "user_trans"),
                new Pair<View, String>(menue, "menue_trans")
        );
        this.startActivity(intent, options.toBundle());
    }
    public void SearchQuests(View view){
        Intent intent = new Intent(this, ChoiceQuest.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) this,
                new Pair<View, String>(SearchAct, "search_trans"),
                new Pair<View, String>(UserAct, "user_trans"),
                new Pair<View, String>(menue, "menue_trans")
        );
        this.startActivity(intent, options.toBundle());
    }
    public void ChangeAvatar(View view){
        avList.clear();
        int tar=1;
        while (tar<9){
            avList.add(new String("ava"+tar));
            tar++;}
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            recycler = findViewById(R.id.avRW);
            recycler.setLayoutManager(layoutManager);
            AvatarAd avatarAd= new AvatarAd(this,avList,avatar,Avaset);
            recycler.setAdapter(avatarAd);
        Avaset.animate().translationY(0).setDuration(800);
    }
    public void ChangePass(View view){
        change.animate().translationX(-1000).setDuration(800);
        oldP.animate().translationX(0).setDuration(800).setStartDelay(500);
        next.animate().translationX(0).setDuration(800).setStartDelay(400);
        oldP.setEnabled(true);
        newP.setEnabled(false);
    }
    public void nextPass(View view){
        if(oldP.getText().toString().trim().length()==0){
            oldP.animate().translationX(-1000).setDuration(800).setStartDelay(0);
            next.animate().translationX(1000).setDuration(800).setStartDelay(400);
            oldP.setEnabled(false);
            newP.setEnabled(false);
            sch=0;
            change.animate().translationX(0).setDuration(800).setStartDelay(600);
        }
        else if(sch==0){
            oldP.animate().translationX(-1000).setDuration(800).setStartDelay(0);
            oldP.setEnabled(false);
            newP.setEnabled(true);
            newP.animate().translationX(0).setDuration(800).setStartDelay(400);
            sch++;
        }
        else {
            if(newP.getText().toString().trim().length()==0){
                newP.animate().translationX(-1000).setDuration(800).setStartDelay(0);
                next.animate().translationX(1000).setDuration(800).setStartDelay(400);
                oldP.setEnabled(false);
                newP.setEnabled(false);
                sch=0;
                change.animate().translationX(0).setDuration(800).setStartDelay(600);
                oldP.setText("");
            }
            newP.animate().translationX(-1000).setDuration(800).setStartDelay(0);
            next.animate().translationX(1000).setDuration(800).setStartDelay(400);
            oldP.setEnabled(false);
            newP.setEnabled(false);
            sch=0;
            change.animate().translationX(0).setDuration(800).setStartDelay(600);
            ApiServise api = RetroClient.getApiServise();
            CangePassOld change = new CangePassOld(UsMail.getText().toString(), oldP.getText().toString(), newP.getText().toString());
            Call<CangePassOld> call = api.changePassByOld(change);
            call.enqueue(new Callback<CangePassOld>() {
                @Override
                public void onResponse(Call<CangePassOld> call, Response<CangePassOld> response) {
                    if(response.code()==200){
                        Toast.makeText(User_Profile.this, "Пароль изменён", Toast.LENGTH_SHORT).show();
                        oldP.setText("");
                        newP.setText("");
                    }
                    else {
                        Toast.makeText(User_Profile.this, "Данные введены некорректно", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CangePassOld> call, Throwable t) {
                    Toast.makeText(User_Profile.this, "Проверьте подключение к сети!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void ExitUser(View view){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.token), "null_token");
        editor.commit();
        super.onBackPressed();
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
    public void HideAvas(View view){
        Avaset.animate().translationY(3000).setDuration(1000);
    }
}

