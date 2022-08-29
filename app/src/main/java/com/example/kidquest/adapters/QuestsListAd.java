package com.example.kidquest.adapters;

import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidquest.ApiServise;
import com.example.kidquest.R;
import com.example.kidquest.RetroClient;
import com.example.kidquest.activities.QuestStartPage;
import com.example.kidquest.objects.GetPrew;
import com.example.kidquest.objects.Get_cat;
import com.example.kidquest.objects.QuestsAr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestsListAd extends RecyclerView.Adapter<QuestsListAd.QuestsViewHolder> {
    Context context;
    List<QuestsAr> items_quest;
    String token;
    Bitmap bmp;
    byte[] byteArray;
    public static List<GetPrew> imageList =  new ArrayList<GetPrew>();

    public QuestsListAd(Context context, List<QuestsAr> items_quest, String token) {
        this.context = context;
        this.items_quest = items_quest;
        this.token = token;
    }

    @NonNull
    @Override
    public QuestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View QuestsItems = LayoutInflater.from(context).inflate(R.layout.quest_item, parent,false);
        return new QuestsListAd.QuestsViewHolder(QuestsItems);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestsViewHolder holder, int position) {
        int posit=position;
        ApiServise api = RetroClient.getApiServise();
        Call<ResponseBody> call = api.retrieveImageData(items_quest.get(position).getImgQ(),token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code()==200) {
                        try {
                            byteArray = response.body().bytes();
                            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            holder.imageView.setImageBitmap(bmp);
                            imageList.add(new GetPrew(items_quest.get(posit).getImgQ(), byteArray));
                        }catch (IOException e) {
                            holder.imageView.setImageResource(context.getResources().getIdentifier
                                    ("globe", "drawable",context.getPackageName()));
                        }

                    }else {
                        Toast.makeText(context, "Попробуйте зайти позже", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        holder.title.setText(items_quest.get(position).getTitleQ());
        int limit = 61;
        String mawa = items_quest.get(position).getTextQ();
        String subStr = mawa.codePointCount(0, mawa.length()) > limit ?
                mawa.substring(0, mawa.offsetByCodePoints(0, limit)) : mawa;

        holder.text.setText(subStr+"...");
        holder.count.setText("Вопросов: " + items_quest.get(position).getCount());
        Call<Get_cat> calll = api.getCategoryById(items_quest.get(position).getCategoryId(),token);
        calll.enqueue(new Callback<Get_cat>() {
            @Override
            public void onResponse(Call<Get_cat> calll, Response<Get_cat> response) {
                if (response.isSuccessful()) {
                        try {
                            holder.categor.setText(response.body().getQuestCategory().getName());
                        } catch (Exception e) {
                            holder.categor.setText("Ошибка");
                        }
                }
            }

            @Override
            public void onFailure(Call<Get_cat> calll, Throwable t) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                Intent intent = new Intent(context, QuestStartPage.class);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) context,
                        new Pair<View, String>(holder.imageView, "questImage"),
                        new Pair<View, String>(holder.card, "cardTrans"),
                        new Pair<View, String>(holder.title, "TitleCardTrans"),
                        new Pair<View, String>(holder.text, "TextCardTrans")

                );
                GetPrew imagefo = imageList.stream().filter(getPrew -> items_quest.get(posit).getImgQ()==getPrew.getId()).findFirst().orElse(null);
                    intent.putExtra("imageQ", imagefo.getImage());
                    intent.putExtra("QuestId", items_quest.get(posit).getId());
                intent.putExtra("TitleQ", items_quest.get(posit).getTitleQ());
                intent.putExtra("textQ", items_quest.get(posit).getTextQ());
                context.startActivity(intent, options.toBundle());
            }catch (Exception e){
                    Toast.makeText(context, "Подождите загруги квеста", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) items_quest.size();
    }

    public static final class QuestsViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ConstraintLayout card;
        TextView title,text,count, categor;
        public QuestsViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.questCard);
            categor = itemView.findViewById(R.id.Q_item_v);
            imageView = itemView.findViewById(R.id.Q_item_img);
            title = itemView.findViewById(R.id.Q_item_title);
            text = itemView.findViewById(R.id.Q_item_text);
            count = itemView.findViewById(R.id.Q_item_count);
        }
    }
}
