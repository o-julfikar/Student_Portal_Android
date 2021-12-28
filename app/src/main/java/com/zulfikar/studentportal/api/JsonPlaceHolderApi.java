package com.zulfikar.studentportal.api;

import com.zulfikar.studentportal.account.models.EnrollCourse;
import com.zulfikar.studentportal.account.models.IdInfo;
import com.zulfikar.studentportal.account.models.User;
import com.zulfikar.studentportal.account.models.UserLogin;
import com.zulfikar.studentportal.account.models.UserRegister;
import com.zulfikar.studentportal.forum.models.Post;
import com.zulfikar.studentportal.forum.models.PostComments;
import com.zulfikar.studentportal.forum.models.PostReactions;
import com.zulfikar.studentportal.notification.models.NotificationGroup;
import com.zulfikar.studentportal.swap.models.Course;
import com.zulfikar.studentportal.swap.models.CourseSection;
import com.zulfikar.studentportal.swap.models.SecSwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.SecSwapRequest;
import com.zulfikar.studentportal.swap.models.StudySwapCardInfoModel;
import com.zulfikar.studentportal.swap.models.StudySwapRequest;
import com.zulfikar.studentportal.swap.models.SwapActionModel;
import com.zulfikar.studentportal.swap.models.UserLearns;
import com.zulfikar.studentportal.swap.models.UserOffers;
import com.zulfikar.studentportal.swap.models.UserPrefers;
import com.zulfikar.studentportal.swap.models.UserStudySlots;
import com.zulfikar.studentportal.swap.models.UserTeaches;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET(URL.auth)
    Call<Boolean> auth();

    @GET(URL.getUser)
    Call<User> getUser();

    @GET(URL.getUserById)
    Call<User> getUserById(@Path("bracu_id") int bracu_id);

    @GET(URL.getInfoById)
    Call<IdInfo> getInfoById(@Path("bracu_id") int bracu_id);

    @GET(URL.getPost)
    Call<List<Post>> getPost();

    @GET(URL.getEnrolledCourses)
    Call<List<Map<String, String>>> getEnrolledCourses();

    @GET(URL.getPostByUser)
    Call<List<Post>> getPostByUser(@Path("bracu_id") int bracu_id);

    @GET(URL.getPostById)
    Call<Post> getPostById(@Path("post_id") int post_id);

    @GET(URL.getComments)
    Call<PostComments> getComments(@Path("post_id") int post_id);

    @GET(URL.getReactions)
    Call<PostReactions> getReactions(@Path("post_id") int post_id);

    @GET(URL.getCourse)
    Call<List<String>> getCourse();

    @GET(URL.getCourseSection)
    Call<CourseSection> getCourseSection(@Path("course_code") String courseCode);

    @GET(URL.getUserOffers)
    Call<List<String>> getOffers();

    @GET(URL.getUserPrefers)
    Call<List<String>> getPrefers();

    @GET(URL.getUserStudySlots)
    Call<List<String>> getStudySlots();

    @GET(URL.getUserTeaches)
    Call<List<String>> getTeaches();

    @GET(URL.getUserLearns)
    Call<List<String>> getLearns();

    @GET(URL.getSlotDays)
    Call<List<String>> getSlotDays();

    @GET(URL.getSlotTimes)
    Call<List<String>> getSlotTimes();

    @GET(URL.getNotifications)
    Call<List<NotificationGroup>> getNotifications();

    @GET(URL.getSecSwapRequests)
    Call<List<SecSwapRequest>> getSecSwapRequests();

    @GET(URL.getSecSwapRequestInfo)
    Call<SecSwapCardInfoModel> getSecSwapCardInfo(@Path("sec_swap_request_id") int secSwapReqId);

    @GET(URL.getStudySwapRequests)
    Call<List<StudySwapRequest>> getStudySwapRequests();

    @GET(URL.getStudySwapRequestInfo)
    Call<StudySwapCardInfoModel> getStudySwapCardInfo(@Path("study_swap_request_id") int studySwapReqId);

    @POST(URL.identifyUser)
    Call<Boolean> identifyUser(@Body UserLogin userLogin);

    @POST(URL.loginUser)
    Call<Object> loginUser(@Body UserLogin userLogin);

    @POST(URL.registerUser)
    Call<Integer> registerUser(@Body UserRegister userRegister);

    @POST(URL.addEnrollCourse)
    Call<Boolean> addEnrollCourse(@Body EnrollCourse enrollCourse);

    @POST(URL.deleteEnrolledCourse)
    Call<Boolean> deleteEnrolledCourse(@Body EnrollCourse enrollCourse);

    @POST(URL.createPost)
    Call<Boolean> createPost(@Body Post post);

    @POST(URL.createComment)
    Call<Boolean> createComment(@Body PostComments.PostComment postComment);

    @POST(URL.postUserOffers)
    Call<Boolean> postUserOffer(@Body UserOffers userOffer);

    @POST(URL.postUserPrefers)
    Call<Boolean> postUserPrefer(@Body UserPrefers userPrefer);

    @POST(URL.postUserStudySlots)
    Call<Boolean> postUserStudySlot(@Body UserStudySlots userStudySlot);

    @POST(URL.postUserTeaches)
    Call<Boolean> postUserTeach(@Body UserTeaches userTeach);

    @POST(URL.postUserLearns)
    Call<Boolean> postUserLearn(@Body UserLearns userLearn);

    @POST(URL.deleteUserOffers)
    Call<Boolean> deleteUserOffer(@Body UserOffers userOffer);

    @POST(URL.deleteUserPrefers)
    Call<Boolean> deleteUserPrefer(@Body UserPrefers userPrefer);

    @POST(URL.deleteUserStudySlots)
    Call<Boolean> deleteUserStudySlot(@Body UserStudySlots userStudySlot);

    @POST(URL.deleteUserTeaches)
    Call<Boolean> deleteUserTeach(@Body UserTeaches userTeach);

    @POST(URL.deleteUserLearns)
    Call<Boolean> deleteUserLearn(@Body UserLearns userLearn);

    @POST(URL.postSecSwapRequest)
    Call<Object> postSecSwapRequest(@Body UserPrefers userPrefer);

    @POST(URL.postStudySwapRequest)
    Call<Object> postStudySwapRequest(@Body UserLearns userLearn);

    @POST(URL.postSecSwapRequestAction)
    Call<Boolean> postSecSwapRequestAction(@Path("sec_swap_request_id") int secSwapReqId,
                                           @Body SwapActionModel swapActionModel);

    @POST(URL.postStudySwapRequestAction)
    Call<Boolean> postStudySwapRequestAction(@Path("study_swap_request_id") int studSwapReqId,
                                             @Body SwapActionModel swapActionModel);
}