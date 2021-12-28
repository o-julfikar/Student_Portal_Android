package com.zulfikar.studentportal.swap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.SecSwapHistoryFragment;
import com.zulfikar.studentportal.swap.SectionSwapCard;
import com.zulfikar.studentportal.swap.SectionSwapCardAdapter;
import com.zulfikar.studentportal.swap.SwapResultFragment;
import com.zulfikar.studentportal.swap.models.SecSwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.SecSwapRequest;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecSwapHistoryRowAdapter extends RecyclerView.Adapter<SecSwapHistoryRowAdapter.SecSwapHistoryRowViewHolder> {

    boolean loading;
    ArrayList<SecSwapRequest> secSwapRequests;
    Context context;
    SecSwapHistoryFragment secSwapHistoryFragment;

    public SecSwapHistoryRowAdapter(Context context,
                                    ArrayList<SecSwapRequest> secSwapRequests,
                                    SecSwapHistoryFragment secSwapHistoryFragment) {
        this.context = context;
        this.secSwapRequests = secSwapRequests;
        this.secSwapHistoryFragment = secSwapHistoryFragment;
    }

    @NonNull
    @Override
    public SecSwapHistoryRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View secSwapHistoryRow = LayoutInflater.from(context).inflate(R.layout.row_section_swap_history, parent, false);
        return new SecSwapHistoryRowViewHolder(secSwapHistoryRow);
    }

    @Override
    public void onBindViewHolder(@NonNull SecSwapHistoryRowViewHolder holder, int position) {
        holder.txtSwapDate.setText(Utility.simpleDateFormat(secSwapRequests.get(position).getDateCreated()));
        switch (secSwapRequests.get(position).getIsApproved()) {
            case SecSwapRequest.DECLINED:
                holder.imgStatus.setImageResource(R.drawable.ic_cancel);
                break;
            case SecSwapRequest.APPROVED:
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
            Call<SecSwapCardInfoModel> secSwapCardInfoModelCall = jsonPlaceHolderApi
                    .getSecSwapCardInfo(secSwapRequests.get(position).getId());
            secSwapCardInfoModelCall.enqueue(new Callback<SecSwapCardInfoModel>() {
                @Override
                public void onResponse(Call<SecSwapCardInfoModel> call, Response<SecSwapCardInfoModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SecSwapCardInfoModel secSwapCardInfoModel = response.body();
                        ArrayList<SectionSwapCard> sectionSwapCards = new ArrayList<>();
                        for (SecSwapCardInfoModel.SecSwapCardModel cardModel : secSwapCardInfoModel.getCards()) {
                            SectionSwapCard sectionSwapCard = new SectionSwapCard(
                                    cardModel.getProviderBracuId(),
                                    cardModel.getRecipientBracuId(),
                                    -1,
                                    -1,
                                    cardModel.getProviderName(),
                                    cardModel.getProviderPhoto(),
                                    cardModel.getRecipientName(),
                                    cardModel.getRecipientPhoto(),
                                    cardModel.getCourseCode(),
                                    cardModel.getSectionNumber()
                            );
                            sectionSwapCards.add(sectionSwapCard);
                        }
                        SectionSwapCardAdapter sectionSwapCardAdapter = new SectionSwapCardAdapter(
                                context,
                                sectionSwapCards
                        );
                        Fragment swapResultFragment = SwapResultFragment.newInstance(
                                sectionSwapCardAdapter,
                                secSwapCardInfoModel.getRequestId(),
                                secSwapCardInfoModel.getCreatorBracuId(),
                                secSwapCardInfoModel.getCreatorName(),
                                secSwapCardInfoModel.getCreatorPhoto(),
                                secSwapCardInfoModel.getDateCreated(),
                                secSwapCardInfoModel.getTotalSwaps(),
                                secSwapCardInfoModel.getUserAccepted(),
                                secSwapCardInfoModel.getRequestStatus(),
                                SwapResultFragment.SECTION_SWAP
                        );
                        secSwapHistoryFragment.requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainFragmentContainer, swapResultFragment)
                                .commit();
                        loading = false;
                    }
                }

                @Override
                public void onFailure(Call<SecSwapCardInfoModel> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return secSwapRequests.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class SecSwapHistoryRowViewHolder extends RecyclerView.ViewHolder {

        TextView txtSwapDate;
        ImageView imgStatus;

        public SecSwapHistoryRowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSwapDate = itemView.findViewById(R.id.txtSwapDate);
            imgStatus = itemView.findViewById(R.id.imgStatus);

        }
    }

}
