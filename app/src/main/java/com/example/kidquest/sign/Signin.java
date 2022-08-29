package com.example.kidquest.sign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kidquest.R;

public class Signin extends Fragment {

    public static EditText mail, password, fCode,fPass;
    public static TextView forget, forgetMess,firstmail;
    public static Button button, back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.signin_tab, container, false);
        mail = (EditText)root.findViewById(R.id.signnum);
        password = (EditText)root.findViewById(R.id.signpass);
        fCode = (EditText)root.findViewById(R.id.fcode);
        fPass = (EditText)root.findViewById(R.id.fPass);
        forget = (TextView) root.findViewById(R.id.forget);
        firstmail = (TextView) root.findViewById(R.id.insert_mail);
        forgetMess = (TextView) root.findViewById(R.id.codemess);
        button = (Button) root.findViewById(R.id.button);
        back = (Button) root.findViewById(R.id.backToLog);

        mail.setTranslationX(800);
        password.setTranslationX(800);
        forget.setTranslationX(800);
        fPass.setTranslationX(800);
        fCode.setTranslationX(800);
        forgetMess.setTranslationX(800);
        firstmail.setTranslationX(800);
        button.setTranslationX(800);
        back.setTranslationX(800);

        mail.setAlpha(0);
        password.setAlpha(0);
        forget.setAlpha(0);
        button.setAlpha(0);
        fCode.setAlpha(0);
        firstmail.setAlpha(0);
        back.setAlpha(0);
        fCode.setEnabled(false);
        fPass.setEnabled(false);
        forgetMess.setAlpha(0);
        fPass.setAlpha(0);

        mail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forget.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }
    public static void FirstMail(){
        button.setText("Дальше");
        password.animate().translationY(800).alpha(0).setDuration(800).setStartDelay(300).start();
        forget.animate().translationY(800).alpha(0).setDuration(800).setStartDelay(200).start();
        password.setEnabled(false);
        firstmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        back.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
    }
    public static void ForgetPass(){
        firstmail.animate().translationY(800).alpha(0).setDuration(800).setStartDelay(200).start();
        mail.setEnabled(false);
        fCode.setEnabled(true);
        fPass.setEnabled(true);
        fCode.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        forgetMess.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        fPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

    }
    public static void BackToSignin(){
        button.setText("Войти");
        mail.setEnabled(true);
        password.setEnabled(true);
        firstmail.animate().translationX(800).alpha(0).setDuration(800).setStartDelay(200).start();
        fCode.setEnabled(false);
        fPass.setEnabled(false);
        fCode.animate().translationX(800).alpha(0).setDuration(800).setStartDelay(200).start();
        forgetMess.animate().translationX(800).alpha(0).setDuration(800).setStartDelay(200).start();
        fPass.animate().translationX(800).alpha(0).setDuration(800).setStartDelay(200).start();

        back.animate().translationX(1100).alpha(1).setDuration(800).setStartDelay(0).start();
        password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        forget.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        firstmail.setTranslationY(0);

    }
}
