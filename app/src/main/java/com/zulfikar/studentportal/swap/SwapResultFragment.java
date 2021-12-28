package com.zulfikar.studentportal.swap;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.Utility;
import com.zulfikar.studentportal.account.SessionManager;
import com.zulfikar.studentportal.api.Client;
import com.zulfikar.studentportal.api.JsonPlaceHolderApi;
import com.zulfikar.studentportal.swap.models.SecSwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.SecSwapRequest;
import com.zulfikar.studentportal.swap.models.StudySwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.SwapActionModel;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwapResultFragment extends Fragment {

    public static final int SECTION_SWAP = 0, STUDY_SWAP = 1;

    int requestId, creatorBracuId, totalSwaps, userAccepted, requestStatus, swapType;
    String creatorName, creatorPhoto;
    Date dateCreated;

    Button btnAccept, btnDecline;
    ConstraintLayout layoutInfo;
    TextView txtCreator, txtDateCreated, txtTotalSwaps, txtStatus;
    RecyclerView rvSwapResult;
    RecyclerView.Adapter<?> rvAdapter;
    View rootView;

    public SwapResultFragment() {

    }

    public static SwapResultFragment newInstance(RecyclerView.Adapter<?> rvAdapter,
                                                 int requestId, int creatorBracuId,
                                                 String creatorName,
                                                 String creatorPhoto,
                                                 Date dateCreated,
                                                 int totalSwaps,
                                                 int userAccepted,
                                                 int requestStatus,
                                                 int swapType) {

        SwapResultFragment swapResultFragment = new SwapResultFragment();
        swapResultFragment.rvAdapter = rvAdapter;

        swapResultFragment.requestId = requestId;
        swapResultFragment.creatorBracuId = creatorBracuId;
        swapResultFragment.creatorName = creatorName;
        swapResultFragment.creatorPhoto = creatorPhoto;
        swapResultFragment.dateCreated = dateCreated;
        swapResultFragment.totalSwaps = totalSwaps;
        swapResultFragment.userAccepted = userAccepted;
        swapResultFragment.requestStatus = requestStatus;
        swapResultFragment.swapType = swapType;

        return swapResultFragment;
    }

    public static SwapResultFragment newInstance(int requestId, int swapType) {
        SwapResultFragment swapResultFragment = new SwapResultFragment();
        swapResultFragment.requestId = requestId;
        swapResultFragment.swapType = swapType;
        swapResultFragment.refresh();

        return swapResultFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager.auth(getContext());
        rootView = inflater.inflate(R.layout.fragment_swap_result, container, false);

        btnAccept = rootView.findViewById(R.id.btnAccept);
        btnDecline = rootView.findViewById(R.id.btnDecline);
        txtCreator = rootView.findViewById(R.id.txtCreator);
        txtDateCreated = rootView.findViewById(R.id.txtDateCreated);
        txtTotalSwaps = rootView.findViewById(R.id.txtTotalSwaps);
        txtStatus = rootView.findViewById(R.id.txtStatus);
        layoutInfo = rootView.findViewById(R.id.layoutInfo);
        rvSwapResult = rootView.findViewById(R.id.rvSwapResult);

        btnAccept.setOnClickListener(v -> {
            if (requestStatus != SecSwapRequest.PENDING) {
                Toast.makeText(getContext(),
                        "You have either already responded or the request has been" +
                                " cancelled.", Toast.LENGTH_SHORT).show();
            } else if (swapType == SECTION_SWAP ){
                postSecSwapRequestAction(1);
            } else if (swapType == STUDY_SWAP) {
                postStudySwapRequestAction(1);
            }
        });

        btnDecline.setOnClickListener(v -> {
            if (requestStatus != SecSwapRequest.PENDING) {
                Toast.makeText(getContext(),
                        "You have either already responded or the request has been" +
                                " cancelled.", Toast.LENGTH_SHORT).show();
            } else if (swapType == SECTION_SWAP ){
                postSecSwapRequestAction(-1);
            } else if (swapType == STUDY_SWAP) {
                postStudySwapRequestAction(-1);
            }
        });

        if (creatorName != null) loadData();

        return rootView;
    }

    private void postSecSwapRequestAction(int actionCode) {
        if (actionCode == -1 || actionCode == 1) {
            SwapActionModel swapActionModel = new SwapActionModel(actionCode);
            JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
            Call<Boolean> postSecSwapRequestActionCall = jsonPlaceHolderApi
                    .postSecSwapRequestAction(requestId, swapActionModel);
            postSecSwapRequestActionCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()
                            && response.body() != null && response.body()) {
                        refresh();
                    } else {
                        Toast.makeText(getContext(),
                                "Failed to perform the action. " +
                                        "Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(),
                    "Invalid action code.", Toast.LENGTH_SHORT).show();
        }
    }

    private void postStudySwapRequestAction(int actionCode) {
        if (actionCode == -1 || actionCode == 1) {
            SwapActionModel swapActionModel = new SwapActionModel(actionCode);
            JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
            Call<Boolean> postStudySwapRequestActionCall = jsonPlaceHolderApi
                    .postStudySwapRequestAction(requestId, swapActionModel);
            postStudySwapRequestActionCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()
                            && response.body() != null && response.body()) {
                        refresh();
                    } else {
                        Toast.makeText(getContext(),
                                "Failed to perform the action. " +
                                        "Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(),
                    "Invalid action code.", Toast.LENGTH_SHORT).show();
        }
    }

    private void refresh() {
        JsonPlaceHolderApi jsonPlaceHolderApi = Client.getApi(getContext());
        if (swapType == STUDY_SWAP) {
            Call<StudySwapCardInfoModel> studySwapCardInfoModelCall = jsonPlaceHolderApi
                    .getStudySwapCardInfo(requestId);
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
                        StudySwapCardAdapter studySwapCardAdapter = new StudySwapCardAdapter(
                                getContext(),
                                studySwapCards
                        );
                        rvAdapter = studySwapCardAdapter;
                        requestId = studySwapCardInfoModel.getRequestId();
                        creatorBracuId = studySwapCardInfoModel.getCreatorBracuId();
                        creatorName = studySwapCardInfoModel.getCreatorName();
                        creatorPhoto = studySwapCardInfoModel.getCreatorPhoto();
                        dateCreated = studySwapCardInfoModel.getDateCreated();
                        totalSwaps = studySwapCardInfoModel.getTotalSwaps();
                        userAccepted = studySwapCardInfoModel.getUserAccepted();
                        requestStatus = studySwapCardInfoModel.getRequestStatus();
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<StudySwapCardInfoModel> call, Throwable t) {

                }
            });
        } else if (swapType == SECTION_SWAP) {
            Call<SecSwapCardInfoModel> secSwapCardInfoModelCall =
                    jsonPlaceHolderApi.getSecSwapCardInfo(requestId);
            secSwapCardInfoModelCall
                    .enqueue(new Callback<SecSwapCardInfoModel>() {
                        @Override
                        public void onResponse(Call<SecSwapCardInfoModel> call,
                                               Response<SecSwapCardInfoModel> response
                        ) {
                            if (response.isSuccessful() &&
                                    response.body() != null) {
                                SecSwapCardInfoModel secSwapCardInfoModel =
                                        response.body();
                                ArrayList<SectionSwapCard> sectionSwapCards =
                                        new ArrayList<>();
                                for (SecSwapCardInfoModel
                                        .SecSwapCardModel cardModel :
                                        secSwapCardInfoModel.getCards()) {
                                    sectionSwapCards.add(new SectionSwapCard(
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
                                    ));
                                }
                                rvAdapter = new SectionSwapCardAdapter(
                                        getContext(),
                                        sectionSwapCards
                                );
                                requestId = secSwapCardInfoModel.getRequestId();
                                creatorBracuId = secSwapCardInfoModel.getCreatorBracuId();
                                creatorName = secSwapCardInfoModel.getCreatorName();
                                creatorPhoto = secSwapCardInfoModel.getCreatorPhoto();
                                dateCreated = secSwapCardInfoModel.getDateCreated();
                                totalSwaps = secSwapCardInfoModel.getTotalSwaps();
                                userAccepted = secSwapCardInfoModel.getUserAccepted();
                                requestStatus = secSwapCardInfoModel.getRequestStatus();
                                loadData();
                            }
                        }

                        @Override
                        public void onFailure(Call<SecSwapCardInfoModel> call,
                                              Throwable t) {

                        }
                    });
        }
    }

    private void loadData() {
        String strCreator = getResources().getString(R.string.creator) + creatorName;
        String strDateCreated = getResources().getString(R.string.created_on) +
                Utility.simpleDateFormat(dateCreated);
        String strTotalSwaps = getResources().getString(R.string.total_swaps_required) + totalSwaps;
        txtCreator.setText(strCreator);
        txtDateCreated.setText(strDateCreated);
        txtTotalSwaps.setText(strTotalSwaps);

        if (requestStatus == SecSwapCardInfoModel.APPROVED) {
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText(R.string.approved);
            layoutInfo.setBackgroundColor(Color.parseColor("#89FF9C"));
        } else if (requestStatus == SecSwapCardInfoModel.DECLINED) {
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText(R.string.declined);
            layoutInfo.setBackgroundColor(Color.parseColor("#FAA099"));
        } else if (userAccepted == SecSwapCardInfoModel.APPROVED) {
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText(R.string.accepted);
            layoutInfo.setBackgroundColor(Color.parseColor("#C6FFCF"));
        } else if (userAccepted == SecSwapCardInfoModel.DECLINED) {
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            txtStatus.setVisibility(View.VISIBLE);
            txtStatus.setText(R.string.declined);
            layoutInfo.setBackgroundColor(Color.parseColor("#FFB7B2"));
        } else {
            btnAccept.setVisibility(View.VISIBLE);
            btnDecline.setVisibility(View.VISIBLE);
            txtStatus.setVisibility(View.GONE);
        }

        rvSwapResult.setAdapter(rvAdapter);
        rvSwapResult.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}