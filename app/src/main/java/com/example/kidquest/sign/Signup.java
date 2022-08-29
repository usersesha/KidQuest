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

public class Signup extends Fragment{
  public static EditText Email;
    public static EditText Name;
    public static EditText Password, Code;
   public static Button Btn;
   public static TextView tv1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.signup_tab, container, false);
        Email = (EditText)root.findViewById(R.id.regEmail);
        Name = (EditText)root.findViewById(R.id.regName);
        Password = (EditText)root.findViewById(R.id.regPass);
        Btn = (Button) root.findViewById(R.id.regButton);
        Code = (EditText)root.findViewById(R.id.regcode);
        Code.setEnabled(false);
        tv1 = (TextView) root.findViewById(R.id.regcodemess);

        Email.setTranslationX(800);
        Name.setTranslationX(800);
        Password.setTranslationX(800);
        Btn.setTranslationX(800);
        tv1.setTranslationX(800);
        Code.setTranslationX(800);

        Email.setAlpha(0);
        Name.setAlpha(0);
        Password.setAlpha(0);
        Btn.setAlpha(0);
        Code.setAlpha(0);
        tv1.setAlpha(0);

        Email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        Btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        return root;
    }
    public static void CheckCode(){
        Email.animate().translationY(800).alpha(0).setDuration(800).setStartDelay(400).start();
        Name.animate().translationY(800).alpha(0).setDuration(800).setStartDelay(300).start();
        Password.animate().translationY(800).alpha(0).setDuration(800).setStartDelay(200).start();

        Code.setEnabled(true);
        tv1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Code.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        Email.setEnabled(false);
        Name.setEnabled(false);
        Password.setEnabled(false);

    }
}
