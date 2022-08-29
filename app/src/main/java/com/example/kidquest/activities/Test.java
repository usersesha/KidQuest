package com.example.kidquest.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.objects.Answer_assembly;
import com.example.kidquest.objects.Fact;
import com.example.kidquest.objects.Fact_assembly;
import com.example.kidquest.objects.GetPrew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test extends QuestStartPage {
    ConstraintLayout c1,c2,c3,c4;
    TextView t1,t2,t3,t4,text,count_q;
    ImageView iv;
    Intent intent;
    Integer qID;
    private TextView Check, factText;
    private ImageView factImage;
    private ConstraintLayout acl;
    private LottieAnimationView suc, fail;
    byte[] byteArr;
    static Bitmap bmp, factBMP;
    ArrayList<Answer_assembly> testList= new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Check=(TextView) findViewById(R.id.titleAnsCheck);
        factText =(TextView) findViewById(R.id.factText);
        factImage = (ImageView)findViewById(R.id.factImage);
        acl = (ConstraintLayout) findViewById(R.id.layAnswerCheck);
        suc = (LottieAnimationView) findViewById(R.id.lottiesuc);
        fail = (LottieAnimationView) findViewById(R.id.lottiefail);
        acl.setTranslationY(3500);
        suc.pauseAnimation();
        fail.pauseAnimation();

        text = (TextView) findViewById(R.id.question_test);
        iv = (ImageView)findViewById(R.id.test_img);
        count_q = (TextView) findViewById(R.id.count_q);

        c1 = (ConstraintLayout) findViewById(R.id.cv_test_1);
        c2 = (ConstraintLayout) findViewById(R.id.cv_test_2);
        c3 = (ConstraintLayout) findViewById(R.id.cv_test_3);
        c4 = (ConstraintLayout) findViewById(R.id.cv_test_4);

        t1 = (TextView) findViewById(R.id.first_test_item);
        t2 = (TextView) findViewById(R.id.second_test_item);
        t3 = (TextView) findViewById(R.id.third_test_item);
        t4 = (TextView) findViewById(R.id.fourth_test_item);

        factText.setVisibility(View.GONE);
        factImage.setVisibility(View.GONE);
        qID = questionsList.get(counter).getId();
        count_q.setText(questionsList.get(counter).getNumber()+" вопрос из "+questionsList.size());
        text.setText(questionsList.get(counter).getQuestion());

        if(quanList.size()>0){
            List<GetPrew> Questionmage = new ArrayList<>();
            Questionmage = quanList;
            try{
            byteArr = Questionmage.stream().filter(x -> x.getId() == questionsList.get(counter).getPreviewId()).findFirst().get().getImage();//quanList.get(1).getImage();
            bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            iv.setImageBitmap(bmp);
            iv.setVisibility(View.VISIBLE);
            }
            catch (Exception e){
                iv.setVisibility(View.GONE);
            }
            }
        else{
                iv.setVisibility(View.GONE);
        }
       for (Answer_assembly answer_assembly:answersList){
            if (answer_assembly.getQuestionId().equals(qID)) {
                testList.add(answer_assembly);
            }
        }
        t1.setText(testList.get(0).getAnswer());
        t2.setText(testList.get(1).getAnswer());
        t3.setText(testList.get(2).getAnswer());
        t4.setText(testList.get(3).getAnswer());

       ApiServise factApi = RetroClient.getApiServise();
       Call<Fact> call = factApi.getFact(qID, token);
       call.enqueue(new Callback<Fact>() {
            @Override
            public void onResponse(Call<Fact> call, Response<Fact> response) {
                if(response.body().getStatus().equals("Ok")){
                    factText.setText(response.body().getFact_assemblies().getText());
                    factText.setVisibility(View.VISIBLE);
                    if (response.body().getFact_assemblies().getPreviewId()!=null){
                    Call<ResponseBody> callimg = api.retrieveImageData(response.body().getFact_assemblies().getPreviewId(),token);
                    callimg.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseImg) {
                            if (responseImg.isSuccessful()) {
                                if (responseImg.code() == 200) {
                                    try {
                                        byte[] byteArray = responseImg.body().bytes();
                                        factBMP = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                        factImage.setImageBitmap(factBMP);
                                        factImage.setVisibility(View.VISIBLE);
                                    }catch (Exception e){}
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<Fact> call, Throwable t) {

            }
        });
    }
    public void onBackPressed() {
        Toast toast = Toast.makeText(this, "Завершите квест", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void test_Click1(View view){
        TransitionDrawable transition = (TransitionDrawable) c1.getBackground();
        transition.startTransition(80);
        EndQuestoin(0);
    }
    public void test_Click2(View view){
        TransitionDrawable transition = (TransitionDrawable) c2.getBackground();
        transition.startTransition(80);
        EndQuestoin(1);
    }
    public void test_Click3(View view){
        TransitionDrawable transition = (TransitionDrawable) c3.getBackground();
        transition.startTransition(80);
        EndQuestoin(2);
    }
    public void test_Click4(View view){
        TransitionDrawable transition = (TransitionDrawable) c4.getBackground();
        transition.startTransition(80);
        EndQuestoin(3);
    }private boolean CheckTrue(int i){
        if(testList.get(i).getRelev().equals(true)){
            truest++;
            return true;
        }
        else {
            return false;
        }
    }
    private void EndQuestoin(int i){
        CheckPage(CheckTrue(i));
    }
    @SuppressLint("ResourceType")
    public void CheckPage(Boolean b){

        if (b.equals(true)){
            suc.setSpeed(2);
            fail.setVisibility(View.GONE);
            Check.setText("Верно!");
            Check.setTextColor(Color.parseColor("#EB08771B"));}
        else {
            fail.setSpeed(2);
            suc.setVisibility(View.GONE);
            Check.setText("Неверно!");
            Check.setTextColor(Color.parseColor("#DAA81C1C"));
        }
        acl.animate().translationY(0).setDuration(900).setStartDelay(0);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                suc.playAnimation();
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fail.playAnimation();
            }
        }, 900);
    }
    public void NextQuestion(View view){
        counter++;
        if (counter<questionsList.size()){
            switch (questionsList.get(counter).getTypeId()) {
                case 1:
                    intent = new Intent(this, Test.class);
                    break;
                case 2:
                    intent = new Intent(this, ChooseImg.class);
                    break;
                case 3:
                    intent = new Intent(this, InsertAnswer.class);
                    break;
                default:
                    Log.i("E",String.valueOf(counter));
                    break;
                }
            try {
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                intent = new Intent(this, QuestStartPage.class);
                quanList.clear();
                questionsList.clear();
                answersList.clear();
                startActivity(intent);
            }
        }
        else{
            intent = new Intent(this, Quest_End.class);
            intent.putExtra("QuestId",questionsList.get(0).getQuestId());
            startActivity(intent);
            finish();
        }
    }
}