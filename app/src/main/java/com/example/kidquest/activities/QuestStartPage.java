package com.example.kidquest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.kidquest.ApiServise;
import com.example.kidquest.CustomScrollView;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.objects.Answer_assembly;
import com.example.kidquest.objects.Answers;
import com.example.kidquest.objects.GetPrew;
import com.example.kidquest.objects.Question_assembly;
import com.example.kidquest.objects.Questions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class QuestStartPage extends AppCompatActivity {
    private ImageView image;
    private ConstraintLayout  loagerContainer;
    private Button start,back;
    CustomScrollView scrollView;
    private TextView Title, text, loadText;
    public Intent intent;
    private Bitmap bmp, saveBit;
    public String token;
    public SharedPreferences preferences;
    private LottieAnimationView loader;
    byte[] byteArray;
    public static List<GetPrew> quanList = new ArrayList<>();
    public static int counter =0,truest=0;
    Integer QuestId;
    ApiServise api;
    public static List<Question_assembly> questionsList = new ArrayList<>();
    public static List<Answer_assembly> answersList = new ArrayList<>();
    DoQuestionPreview doQuestionPreview = new DoQuestionPreview();
    DownAnswers downAnswers = new DownAnswers();
    DownloadQuestions downloadQuestions = new DownloadQuestions();
    GO go = new GO();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_start);
        preferences = getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        token = preferences.getString(getString(R.string.token), "null_token");
        QuestId = getIntent().getIntExtra("QuestId", 0);
        api = RetroClient.getApiServise();
        back = findViewById(R.id.closeCard_Q);
        scrollView = (CustomScrollView) findViewById(R.id.scrollStartQ);
        start = findViewById(R.id.start_Q);
        loadText = findViewById(R.id.loadText);
        loagerContainer = findViewById(R.id.loader_conteiner);
        loader = findViewById(R.id.loader_quest);
        loader.loop(true);
        loagerContainer.setAlpha(0);
        loadText.setText("Загружаем вопросы");
        loadText.setAlpha(0);

        image = findViewById(R.id.imageQuest);
        start = findViewById(R.id.start_Q);
        Title = findViewById(R.id.TitleQuestPage);
        text = findViewById(R.id.TextQuestPage);
        try {
                byte[] byteArr = getIntent().getByteArrayExtra("imageQ");
                bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
                saveBit = bmp;

        } catch (Exception e) {
            bmp = saveBit;
        }
        image.setImageBitmap(bmp);
        Title.setText(getIntent().getStringExtra("TitleQ"));
        text.setText(getIntent().getStringExtra("textQ"));
    }
    public interface CountingListener{
        void countingDone();
    }
    public void StartQuest(View view) {
        loader.playAnimation();
        scrollView.setEnableScrolling(false);
        start.setEnabled(false);
        back.setEnabled(false);
        loagerContainer.animate().alpha(1).setDuration(100);
        loadText.animate().alpha(1).setDuration(100);
        final Handler mainThread = new Handler();
        final CountingListener listener = new CountingListener() {
            @Override
            public void countingDone() {
                loadText.animate().alpha(0).setDuration(20);
                loagerContainer.animate().alpha(0).setDuration(20);
                QuestProgress();
            }
        };
        Thread t_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadQuestions.start();
                try {
                    downloadQuestions.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                downAnswers.start();
                try {
                    downAnswers.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                doQuestionPreview.start();
                try {
                    doQuestionPreview.join();
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
    }
        public void QuestProgress() {
            if(questionsList.size()!=0){
                Collections.sort(questionsList,(o1, o2) -> o1.getNumber().compareTo(o2.getNumber()));
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
                    Log.i("E",String.valueOf(questionsList.get(counter).getTypeId()));
                    break;
            }
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }}
            else{
                scrollView.setEnableScrolling(true);
                start.setEnabled(true);
                back.setEnabled(true);
                Toast.makeText(this, "В этом квесте пока нет вопросов", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

        public void HideCard(View view) {
            onBackPressed();
        }
        class DownloadQuestions extends Thread {
            @Override
            public void run() {
                Call<Questions> call = api.getQuestionsData(QuestId, token);
                try {
                    questionsList = call.execute().body().getQuestions();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        class DoQuestionPreview extends Thread {
            @Override
            public void run() {
                for (Question_assembly q_a : questionsList) {
                    if (q_a.getPreviewId() != null)
                    {
                        Call<ResponseBody> call = api.retrieveImageData(q_a.getPreviewId(), token);

                        try {
                            byteArray = call.execute().body().bytes();
                            quanList.add(new GetPrew(q_a.getPreviewId(), byteArray));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        class DownAnswers extends Thread {
            @Override
            public void run() {
                for (Question_assembly q_a : questionsList) {
                    Call<Answers> call = api.getAnswersData(q_a.getId(), token);
                    try {
                        answersList.addAll(call.execute().body().getAnswerVariants());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class GO extends Thread {
            @Override
            public void run() {
            }
        }
}

