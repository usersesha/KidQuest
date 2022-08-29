package com.example.kidquest.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.adapters.ChoiceAd;
import com.example.kidquest.objects.Answer_assembly;
import com.example.kidquest.objects.Fact;
import com.example.kidquest.objects.Fact_assembly;
import com.example.kidquest.objects.GetPrew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseImg extends QuestStartPage {
    private TextView text,count_q,Check,factText;
    private ImageView iv, factImage;
    private ConstraintLayout acl;
    private RecyclerView recyclerChoice;
    private LottieAnimationView suc, fail;
    public Integer qID;
    public ChoiceAd choiceAd;
    private byte[] byteArr;
    static Bitmap bmp;
    static List<Answer_assembly> imgAnswer = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_img);
        text = (TextView) findViewById(R.id.question_choose);
        iv = (ImageView)findViewById(R.id.choose_img);
        qID = questionsList.get(counter).getId();

        count_q = (TextView) findViewById(R.id.count_q);
        count_q.setText(questionsList.get(counter).getNumber()+" вопрос из "+questionsList.size());
        text.setText(questionsList.get(counter).getQuestion());

        Check=(TextView) findViewById(R.id.titleAnsCheck);
        factText =(TextView) findViewById(R.id.factText);
        factImage = (ImageView)findViewById(R.id.factImage);
        acl = (ConstraintLayout) findViewById(R.id.layAnswerCheck);
        suc = (LottieAnimationView) findViewById(R.id.lottiesuc);
        fail = (LottieAnimationView) findViewById(R.id.lottiefail);
        acl.setTranslationY(3500);
        suc.pauseAnimation();
        fail.pauseAnimation();
        setImagesToRec();

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
        factText.setVisibility(View.GONE);
        factImage.setVisibility(View.GONE);
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
                                            Bitmap factBMP = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                            factImage.setImageBitmap(factBMP);
                                            factImage.setVisibility(View.VISIBLE);
                                        }catch (Exception e){
                                            Toast.makeText(ChooseImg.this, "Не удалось получить изображение", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(ChooseImg.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<Fact> call, Throwable t) {
                Toast.makeText(ChooseImg.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setImagesToRec(){
        for (Answer_assembly answer_assembly:answersList){
            if (answer_assembly.getQuestionId().equals(qID)) {
                imgAnswer.add(answer_assembly);
            }
        }
        setRecyclerChoice(imgAnswer);
    }
    private void setRecyclerChoice(List<Answer_assembly> aList){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerChoice = findViewById(R.id.choice_a);
        recyclerChoice.setLayoutManager(layoutManager);
        choiceAd = new ChoiceAd(this,aList,token,questionsList);
        recyclerChoice.setAdapter(choiceAd);
    }
    public void onBackPressed() {
        Toast toast = Toast.makeText(this, "Завершите квест", Toast.LENGTH_SHORT);
        toast.show();
    }
    @SuppressLint("ResourceType")
    public void CheckPage(Boolean b){
        if (b.equals(true)){
            fail.setVisibility(View.GONE);
            suc.setSpeed(2);
            Check.setText("Верно!");
            truest++;
            Check.setTextColor(Color.parseColor("#EB08771B"));
        }
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
