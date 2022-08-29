package com.example.kidquest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.objects.Auth;
import com.example.kidquest.objects.ConfirmEmail;
import com.example.kidquest.objects.Entry;
import com.example.kidquest.adapters.LoginAdapter;
import com.example.kidquest.NetworkCheck;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.objects.NewPassword;
import com.example.kidquest.objects.ResetPass;
import com.example.kidquest.sign.Signin;
import com.example.kidquest.sign.Signup;
import com.google.android.material.tabs.TabLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TabLayout tabLayout;
    private ImageView logo;
    private ViewPager viewPager;
    private Auth auth;
    private Entry entry;
    private Toast toast;
    private TextView tv1;
    String mailre,namere,passre;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout layout;
    private SharedPreferences preferences;
    public int schet, schetchik;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_lay);
        schet = 0;
        schetchik = 0;

        tabLayout = findViewById(R.id.tablay);
        logo = findViewById(R.id.logo);
        tv1 = findViewById(R.id.entry_tablay);
        lottieAnimationView = findViewById(R.id.lottie_load);
        viewPager = findViewById(R.id.vwpgr);
        layout = findViewById(R.id.constraintLayout);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        lottieAnimationView.setTranslationY(-6000);
        lottieAnimationView.pauseAnimation();
        lottieAnimationView.loop(true);
        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(0);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();

    }

    public void ForgetPass(View view) {
        schetchik++;
        if(schetchik==1){
            Signin.FirstMail();
        }
        else if(schetchik==2){
            Signin.ForgetPass();
        }
    }

    public void regClick(View view) {
        loaderOn();
        if (NetworkCheck.check(getApplicationContext()) == true) {
            mailre = ((EditText) findViewById(R.id.regEmail)).getText().toString().trim();
            namere= ((EditText) findViewById(R.id.regName)).getText().toString().trim();
            passre = ((EditText) findViewById(R.id.regPass)).getText().toString().trim();
            if (schet == 0) {
                if (!mailre.isEmpty() && !passre.isEmpty() && !namere.isEmpty()) {
                    ApiServise api = RetroClient.getApiServise();
                    auth = new Auth(mailre, namere, passre);
                    Call<Auth> call = api.createAuth(auth);
                    call.enqueue(new Callback<Auth>() {
                        @Override
                        public void onResponse(Call<Auth> call, Response<Auth> response) {
                            if (response.isSuccessful()) {
                                loaderOff();
                                if (response.body().getStatus().equals("Ok")) {
                                    Signup.CheckCode();
                                    schet++;
                                }
                                else if (response.body().getStatus().equals("UserExists")) {
                                    toast = Toast.makeText(Login.this, "Такой пользователь уже есть!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else if (response.body().getStatus().equals("InvalidEmail")) {
                                    toast = Toast.makeText(Login.this, "Ложный Email!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Auth> call, Throwable t) {
                            loaderOff();
                            toast = Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                }
                else {
                    loaderOff();
                    toast = Toast.makeText(Login.this, "Заполните поля!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else {
                String code = ((EditText) findViewById(R.id.regcode)).getText().toString().trim();
                if (!code.isEmpty()) {
                    ApiServise api = RetroClient.getApiServise();
                    ConfirmEmail confirmEmail = new ConfirmEmail(mailre, code);
                    Call<ConfirmEmail> call = api.confirm(confirmEmail);
                    call.enqueue(new Callback<ConfirmEmail>() {
                        @Override
                        public void onResponse(Call<ConfirmEmail> call, Response<ConfirmEmail> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("Ok")) {
                                    AttemptLogin(mailre,passre);
                                } else if (response.body().getStatus().equals("UserNotExists")) {
                                    loaderOff();
                                    toast = Toast.makeText(Login.this, "Пользователь не создан", Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (response.body().getStatus().equals("UserAlreadyActive")) {
                                    loaderOff();
                                    toast = Toast.makeText(Login.this, "Пользователь уже подтверждён", Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    loaderOff();
                                    toast = Toast.makeText(Login.this, "Неверный код!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ConfirmEmail> call, Throwable t) {
                            loaderOff();
                            toast = Toast.makeText(Login.this, "Попробуйте позже", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                }
                else {
                    loaderOff();
                    toast = Toast.makeText(Login.this, "Введите код!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
        else {
            loaderOff();
            toast = Toast.makeText(Login.this, "Нет подключения к сети!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void enterClick(View view){
        loaderOn();
        String mail =((EditText)findViewById(R.id.signnum)).getText().toString().trim();
        String pass = ((EditText)findViewById(R.id.signpass)).getText().toString().trim();
        if(schetchik == 0){
            AttemptLogin(mail,pass);
        }
        else if(schetchik == 1){
            if (!mail.isEmpty() ) {
                ApiServise api = RetroClient.getApiServise();
                ResetPass resetPass = new ResetPass(mail);
                Call<ResetPass> call = api.resetWithMail(resetPass);
                call.enqueue(new Callback<ResetPass>() {
                    @Override
                    public void onResponse(Call<ResetPass> call, Response<ResetPass> response) {
                        if (response.isSuccessful()) {
                            loaderOff();
                            if (response.body().getStatus().equals("Ok")) {
                                ForgetPass(view);
                            } else if (response.body().getStatus().equals("UserNotExists")) {
                                toast = Toast.makeText(Login.this, "Ложный Email!", Toast.LENGTH_LONG);
                                toast.show();
                            } else if (response.body().getStatus().equals("UserNotActive")) {
                                toast = Toast.makeText(Login.this, "Пользователь не активен!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetPass> call, Throwable t) {
                        loaderOff();
                        toast = Toast.makeText(Login.this, "Попробуйте позже", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
            else {
                loaderOff();
                toast = Toast.makeText(Login.this, "Заполните поле!", Toast.LENGTH_LONG);
                toast.show();}
        }
        else if(schetchik == 2){
            String code = ((EditText)findViewById(R.id.fcode)).getText().toString().trim();
            String newpass = ((EditText)findViewById(R.id.fPass)).getText().toString().trim();
            if (!mail.isEmpty() && !code.isEmpty()) {
                ApiServise api = RetroClient.getApiServise();
                NewPassword newPassword = new NewPassword(mail,newpass,code);
                Call<NewPassword> call = api.resetPass(newPassword);
                call.enqueue(new Callback<NewPassword>() {
                    @Override
                    public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                        if (response.isSuccessful()) {
                            loaderOff();
                            if (response.body().getStatus().equals("Ok")) {
                                toast = Toast.makeText(Login.this, "Пароль изменён", Toast.LENGTH_LONG);
                                toast.show();
                                Back_To_Log_Click(view);
                            }
                            else if (response.body().getStatus().equals("UserNotExists")) {
                                toast = Toast.makeText(Login.this, "Ложный Email!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if (response.body().getStatus().equals("UserNotActive")) {
                                toast = Toast.makeText(Login.this, "Пользователь не активен!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if (response.body().getStatus().equals("InvalidCode")) {
                                toast = Toast.makeText(Login.this, "Неверный код!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<NewPassword> call, Throwable t) {
                        loaderOff();
                        toast = Toast.makeText(Login.this, "Попробуйте позже", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
            else {
                loaderOff();
                toast = Toast.makeText(Login.this, "Заполните все поля!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
public void Back_To_Log_Click(View view){
    Signin.BackToSignin();
    schetchik=0;
}
    public void AttemptLogin(String mail, String pass){
        if(NetworkCheck.check(getApplicationContext()) == true){
            if (!mail.isEmpty() && !pass.isEmpty()) {
                ApiServise api = RetroClient.getApiServise();
                entry = new Entry(mail,pass);
                Call<Entry> call = api.createEntry(entry);
                call.enqueue(new Callback<Entry>() {
                    @Override
                    public void onResponse(Call<Entry> call, Response<Entry> response) {
                        if (response.isSuccessful()) {
                            loaderOff();
                            if(response.body().getStatus().equals("Ok")){
                                preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(getString(R.string.token), response.body().getToken());
                                editor.commit();
                                OkLogin();
                            }
                            else if(response.body().getStatus().equals("UserNotExists")){
                                toast = Toast.makeText(Login.this, "Пользователь не найден.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if (response.body().getStatus().equals("InvalidPassword")) {
                                toast = Toast.makeText(Login.this, "Неверный пароль!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if(response.body().getStatus().equals("UserNotActive")){
                                toast = Toast.makeText(Login.this, "Завершите регистрацию!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Entry> call, Throwable t) {
                        loaderOff();
                        toast = Toast.makeText(Login.this, "Попробуйте позже", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
            else{
                loaderOff();
                toast = Toast.makeText(Login.this, "Заполните поля!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{
            loaderOff();
            toast = Toast.makeText(Login.this, "Нет подключения к сети!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void OkLogin()
    {
        Intent intent = new Intent(Login.this, ChoiceQuest.class);
        startActivity(intent);
    }
    public void loaderOn()
    {
        logo.animate().translationY(-6000).setDuration(1000).setStartDelay(400);
        lottieAnimationView.playAnimation();
        lottieAnimationView.animate().translationY(30).setDuration(1000).setStartDelay(400);
    }
    public void loaderOff()
    {
        logo.animate().translationY(0).setDuration(1000).setStartDelay(400);
        lottieAnimationView.animate().translationY(-6000).setDuration(1000).setStartDelay(400);
        lottieAnimationView.pauseAnimation();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }
}

