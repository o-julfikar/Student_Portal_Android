package com.zulfikar.studentportal.swap.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.SecSwapHistoryFragment;
import com.zulfikar.studentportal.swap.StudySwapCard;
import com.zulfikar.studentportal.swap.StudySwapCardAdapter;
import com.zulfikar.studentportal.swap.StudySwapHistoryFragment;
import com.zulfikar.studentportal.swap.SwapResultFragment;
import com.zulfikar.studentportal.swap.models.StudySwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudySwapHistoryRowAdapter extends RecyclerView.Adapter<StudySwapHistoryRowAdapter.StudySwapHistoryRowViewHolder> {

    boolean loading;
    ArrayList<StudySwapRequest> studySwapRequests;
    Context context;
    StudySwapHistoryFragment studySwapHistoryFragment;

    public StudySwapHistoryRowAdapter(Context context,
                                      ArrayList<StudySwapRequest> studySwapRequests,
                                      StudySwapHistoryFragment studySwapHistoryFragment) {
        this.context = context;
        this.studySwapRequests = studySwapRequests;
        this.studySwapHistoryFragment = studySwapHistoryFragment;
    }

    @NonNull
    @Override
    public StudySwapHistoryRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studySwapHistoryRow = LayoutInflater.from(context).inflate(R.layout.row_study_swap_history, parent, false);
        return new StudySwapHistoryRowViewHolder(studySwapHistoryRow);
    }

    @Override
    public void onBindViewHolder(@NonNull StudySwapHistoryRowViewHolder holder, int position) {
        holder.txtSwapDate.setText(Utility.simpleDateFormat(studySwapRequests.get(position).getDateCreated()));
        switch (studySwapRequests.get(position).getIsApproved()) {
            case StudySwapRequest.DECLINED:
                holder.imgStatus.setImageResource(R.drawable.ic_cancel);
                break;
            case StudySwapRequest.APPROVED:
                holder.imgStatus.setImageResource(R.drawable.ic_round_check_circle);
                break;
            default:
                holder.imgStatus.setImageResource(R.drawable.ic_round_pending);
                break;
        }
        holder.itemView.setOnClickListener(v -> {
            if (loading) return;
            loading = true;
            JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(context);
            Call<StudySwapCardInfoModel> studySwapCardInfoModelCall = jsonPlaceHolderApi
                    .getStudySwapCardInfo(studySwapRequests.get(position).getId());
            studySwapCardInfoModelCall.enqueue(new Callback<StudySwapCardInfoModel>() {
                @Override
                public void onResponse(Call<StudySwapCardInfoModel> call, Response<StudySwapCardInfoModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        StudySwapCardInfoModel studySwapCardInfoModel = response.body();
                        ArrayList<StudySwapCard> studySwapCards = new ArrayList<>();
                        for (StudySwapCardInfoModel.StudySwapCardModel studySwapCardModel : studySwapCardInfoModel.getCards()) {
                            studySwapCards.add(new StudySwapCard(
                                    studySwapCardModel.getTeacherBracuId(),
                                    studySwapCardModel.getLearnerBracuId(),
                                    -1,
                                    -1,
                                    studySwapCardModel.getTeacherName(),
                                    studySwapCardModel.getTeacherPhoto(),
                                    studySwapCardModel.getLearnerName(),
                                    studySwapCardModel.getLearnerPhoto(),
                                    studySwapCardModel.getCourseCode(),
                                    studySwapCardModel.getStudySlot()
                            ));
                        }
                        Log.e("TAG", "onResponse: " + response.body());
                        StudySwapCardAdapter studySwapCardAdapter = new StudySwapCardAdapter(
                                context,
                                studySwapCards
                        );
                        Fragment swapResultFragment = SwapResultFragment.newInstance(
                                studySwapCardAdapter,
                                studySwapCardInfoModel.getRequestId(),
                                studySwapCardInfoModel.getCreatorBracuId(),
                                studySwapCardInfoModel.getCreatorName(),
                                studySwapCardInfoModel.getCreatorPhoto(),
                                studySwapCardInfoModel.getDateCreated(),
                                studySwapCardInfoModel.getTotalSwaps(),
                                studySwapCardInfoModel.getUserAccepted(),
                                studySwapCardInfoModel.getRequestStatus(),
                                SwapResultFragment.STUDY_SWAP
                        );
                        studySwapHistoryFragment.requireActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainFragmentContainer, swapResultFragment)
                                .commit();
                    }
                    loading = false;
                }
                @Override
                public void onFailure(Call<StudySwapCardInfoModel> call, Throwable t) {
                    loading = false;
                    t.printStackTrace();
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return studySwapRequests.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class StudySwapHistoryRowViewHolder extends RecyclerView.ViewHolder {

        TextView txtSwapDate;
        ImageView imgStatus;

        public StudySwapHistoryRowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSwapDate = itemView.findViewById(R.id.txtSwapDate);
            imgStatus = itemView.findViewById(R.id.imgStatus);

        }
    }
}
