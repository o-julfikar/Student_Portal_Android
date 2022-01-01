package com.zulfikar.studentportal.review;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zulfikar.studentportal.FetchImage;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.review.models.SneakCardModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SneakReviewCardAdapter extends RecyclerView.Adapter<SneakReviewCardAdapter.SneakReviewCardViewHolder> {

    Context context;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    List<SneakReviewCard> sneakReviewCards;
    Handler sneakRCHandler;

    public SneakReviewCardAdapter(Context context, List<SneakReviewCard> sneakReviewCards) {
        this.context = context;
        this.sneakReviewCards = sneakReviewCards;
        sneakRCHandler = new Handler();
        jsonPlaceHolderApi = Client.getApi(context);
    }

    @NonNull
    @Override
    public SneakReviewCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sneakReviewCard = LayoutInflater.from(context).inflate(R.layout.card_sneak, parent, false);
        return new SneakReviewCardViewHolder(sneakReviewCard);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return sneakReviewCards.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SneakReviewCardViewHolder holder, int position) {
        Call<Object> getInstructorSneakCardCall = jsonPlaceHolderApi
                .getInstructorSneakCard(sneakReviewCards.get(position).instructorInitial);
        getInstructorSneakCardCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() instanceof Integer) {

                    } else {
                        SneakCardModel sneakCardModel = new SneakCardModel(response.body());
                        new FetchImage(
                                holder.imgSneakRCInstructorPhoto,
                                sneakCardModel.getInstructorPhoto(),
                                sneakRCHandler
                        ).start();
                        holder.txtSneakRCInstructorName.setText(
                                sneakCardModel.getInstructorFullname()
                        );
                        holder.txtSneakRCInstructorInitial.setText(
                                String.join(", ", sneakCardModel.getInstructorInitials())
                        );

                        for (SneakCardModel.SneakReviewModel
                                sneakReviewModel : sneakCardModel.getInstructorReviews()
                        ) {
                            holder.linLayoutSneakRCSneakCards.addView(
                                    new SneakCard(
                                            sneakReviewModel.getReviewId(),
                                            sneakReviewModel.getCourseCode(),
                                            sneakReviewModel.getReviewText()
                                    ).getView(
                                            context,
                                            holder.linLayoutSneakRCSneakCards
                                    )
                            );
                        }
                        Log.e("onResponse(...):", response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    protected static class SneakReviewCardViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgSneakRCInstructorPhoto;
        ImageView sneakRCStar1, sneakRCStar2, sneakRCStar3, sneakRCStar4, sneakRCStar5;
        Button btnSneakRCReview, btnSneakRCMore;
        LinearLayout linLayoutSneakRCSneakCards;
        TextView txtSneakRCInstructorName, txtSneakRCInstructorInitial;

        public SneakReviewCardViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSneakRCInstructorPhoto = itemView.findViewById(R.id.imgSneakRCInstructorPhoto);
            sneakRCStar1 = itemView.findViewById(R.id.sneakRCStar1);
            sneakRCStar2 = itemView.findViewById(R.id.sneakRCStar2);
            sneakRCStar3 = itemView.findViewById(R.id.sneakRCStar3);
            sneakRCStar4 = itemView.findViewById(R.id.sneakRCStar4);
            sneakRCStar5 = itemView.findViewById(R.id.sneakRCStar5);
            btnSneakRCReview = itemView.findViewById(R.id.btnSneakRCReview);
            btnSneakRCMore = itemView.findViewById(R.id.btnSneakRCMore);
            linLayoutSneakRCSneakCards = itemView.findViewById(R.id.linLayoutSneakRCSneakCards);
            txtSneakRCInstructorName = itemView.findViewById(R.id.txtSneakRCInstructorName);
            txtSneakRCInstructorInitial = itemView.findViewById(R.id.txtSneakRCInstructorInitial);
        }
    }
}
