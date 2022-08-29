package com.example.kidquest;


import com.example.kidquest.activities.Quest_End;
import com.example.kidquest.objects.CangePassOld;
import com.example.kidquest.objects.UserNE;
import com.example.kidquest.objects.Answers;
import com.example.kidquest.objects.AttemptCreate;
import com.example.kidquest.objects.Auth;
import com.example.kidquest.objects.ConfirmEmail;
import com.example.kidquest.objects.Entry;
import com.example.kidquest.objects.Fact;
import com.example.kidquest.objects.GetCategory;
import com.example.kidquest.objects.Get_cat;
import com.example.kidquest.objects.NewPassword;
import com.example.kidquest.objects.Quest_item;
import com.example.kidquest.objects.Questions;
import com.example.kidquest.objects.ResetPass;
import com.example.kidquest.objects.RewardAll;
import com.example.kidquest.objects.RewardUser;
import com.example.kidquest.objects.TokenLog;
import com.example.kidquest.objects.UserStat;

import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServise {
    @POST("/register")
    Call<Auth> createAuth(@Body Auth auth);

    @POST("/login")
    Call<Entry> createEntry(@Body Entry entry);

    @POST("/confirmEmail")
    Call<ConfirmEmail> confirm(@Body ConfirmEmail confirmEmail);

    @POST("/resetPassword")
    Call<ResetPass> resetWithMail(@Body ResetPass resetPass);

    @POST("/confirmResetPassword")
    Call<NewPassword> resetPass(@Body NewPassword newPassword);

    @GET("/loginWithToken")
    Call<TokenLog> entryToken(@Header("token") String getToken);

    @GET("/Quest/GetAll")
    Call<Quest_item> getQuests(@Header("token") String getToken);

    @GET("/QuestCategory/GetAll")
    Call<GetCategory> getCategories(@Header("token") String getToken);

    @GET("/QuestCategory/GetById")
    Call<Get_cat> getCategoryById(@Header("id") Integer getId, @Header("token") String getToken);

    @GET("/Preview/Download")
    Call<ResponseBody> retrieveImageData(@Query("id") Integer getId, @Header("token") String getToken);

    @GET("/Question/GetAllByQuestId")
    Call<Questions> getQuestionsData(@Query("questId") Integer getQuestId, @Header("token") String getToken);

    @GET("/AnswerVariant/GetByQuestionId")
    Call<Answers> getAnswersData(@Header("questionId") Integer getQuestionId, @Header("token") String getToken);

    @GET("/Fact/GetByQuestionId")
    Call<Fact> getFact(@Query ("questionId") Integer getQuestionId, @Header("token") String getToken);

    @GET("/AwardType/GetAll")
    Call<RewardAll> getAllRewards(@Header("token") String getToken);

    @GET("/UserAward/GetAllByUserToken")
    Call<RewardUser> getUserRewards(@Header("token") String getToken);

    @POST("/UserAward/Create")
    Call<Quest_End.SetReward> createReward(@Body Quest_End.SetReward setReward, @Header("token") String getToken);

    @POST("/Attempt/Create")
    Call<AttemptCreate> createAttempt(@Body AttemptCreate attemptCreate, @Header("token") String getToken);

    @GET("/User/GetUserInfo")
    Call<UserNE> getUserInfo (@Header("token") String getToken);
    @GET("/Attempt/GetAllByUserToken")
    Call<UserStat> getUserStat (@Header("token") String getToken);

    @POST("/resetPasswordByOldPassword")
    Call<CangePassOld> changePassByOld(@Body CangePassOld cangePassOld);

}

