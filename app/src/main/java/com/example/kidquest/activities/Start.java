package com.example.kidquest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.NetworkCheck;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.objects.TokenLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Start extends AppCompatActivity {

    private SharedPreferences preferences;
    private ImageView logo, splashImg;
    private TextView appName;
    private Intent intent;
    ConstraintLayout layout;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_lay);
        layout = findViewById(R.id.open_cl);
        logo=findViewById(R.id.logo);
        appName=findViewById(R.id.appname);
        splashImg=findViewById(R.id.img_startBG);
        lottieAnimationView=findViewById(R.id.lottie_start);
        if(NetworkCheck.check(getApplicationContext()) == true){
            true_Net();
        }else{
            Toast toast = Toast.makeText(Start.this, "Нет подключения к сети!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void true_Net(){
        logo.animate().translationY(-2000).alpha(0).setDuration(1000).setStartDelay(1800);
        appName.animate().translationY(-2000).alpha(0).setDuration(1000).setStartDelay(2200);
        lottieAnimationView.animate().translationY(2000).alpha(0).setDuration(1000).setStartDelay(4000);
        preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);

        String introKey = getString(R.string.intro);
        String intro  = preferences.getString(introKey,"true");
        String token  = preferences.getString(getString(R.string.token),"null_token");
        if(intro.equals("true")){
            intent = new Intent(Start.this, Introduce.class);
        }
        else {
            if (!token.equals("null_token")) {
                //вход по токену
                    ApiServise api = RetroClient.getApiServise();
                    Call<TokenLog> call = api.entryToken(token);
                    call.enqueue(new Callback<TokenLog>() {
                        @Override
                        public void onResponse(Call<TokenLog> call, Response<TokenLog> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("Ok")) {
                                    intent = new Intent(Start.this, ChoiceQuest.class);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<TokenLog> call, Throwable t) {
                            setNullToken();
                            intent = new Intent(Start.this, Login.class);
                        }
                    });
            }
            else{
                intent = new Intent(Start.this, Login.class);
            }
        }
    }
    public void setNullToken(){
        preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.token), "null_token");
        editor.commit();
    }
    protected void onResume() {
        super.onResume();
        lottieAnimationView.playAnimation();
        if(NetworkCheck.check(getApplicationContext()) == true){
            true_Net();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(NetworkCheck.check(getApplicationContext()) == true){
                        startActivity(intent);
                    }
                }
            }, 3000);
        }else{
            Toast toast = Toast.makeText(Start.this, "Нет подключения к сети!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
