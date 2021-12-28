package com.zulfikar.studentportal.api;

public class URL {
//    public static final String baseURL = "http://192.168.9.48:8000/";
//    public static final String baseURL = "http://10.0.2.2:8000/";
//    public static final String baseURL = "https://fa6817fac791c7.localhost.run/";
    public static final String baseURL = "https://68bd-223-29-215-10.ngrok.io/";
    public static final String auth = "user/auth/";
    public static final String identifyUser = "user/identify/";
    public static final String loginUser = "user/login/";
    public static final String registerUser = "user/register/";
    public static final String getUser = "user/profile-info/";
    public static final String getUserById = "user/profile-info/{bracu_id}";
    public static final String getInfoById = "user/info-by-id/{bracu_id}";

    public static final String getEnrolledCourses = "user/enrolled-courses/";
    public static final String addEnrollCourse = "user/enroll-course/";
    public static final String deleteEnrolledCourse = "user/delete-course/";

    // Forum
    public static final String createPost = "forum/post/create/";
    public static final String getPost = "forum/post/get/";
    public static final String getPostByUser = "forum/post/get/{bracu_id}";
    public static final String getPostById = "forum/post/{post_id}";
    public static final String createComment = "forum/post/comment/create/";

    // Forum - Post
    public static final String getComments = "forum/post/comment/get/{post_id}";
    public static final String getReactions = "forum/post/reaction/get/{post_id}";

    // Swap - Offer
    public static final String getUserOffers = "swap/section/offer/get";
    public static final String postUserOffers = "swap/section/offer/post";
    public static final String deleteUserOffers = "swap/section/offer/delete";
    public static final String getCourse = "swap/course/get";
    public static final String postCourse = "swap/course/create";
    public static final String deleteCourse = "swap/course/delete";
    public static final String getCourseSection = "swap/course/{course_code}/section/get";
    public static final String postCourseSection = "swap/course/{course_code}/section/create";
    public static final String deleteCourseSection = "swap/course/{course_code}/section/delete";

    // Swap - Prefer
    public static final String getUserPrefers = "swap/section/prefer/get";
    public static final String postUserPrefers = "swap/section/prefer/post";
    public static final String deleteUserPrefers = "swap/section/prefer/delete";

    // Swap - Teach
    public static final String getUserTeaches = "swap/study/teach/get";
    public static final String postUserTeaches = "swap/study/teach/post";
    public static final String deleteUserTeaches = "swap/study/teach/delete";

    // Swap - Learn
    public static final String getUserLearns = "swap/study/learn/get";
    public static final String postUserLearns = "swap/study/learn/post";
    public static final String deleteUserLearns = "swap/study/learn/delete";

    // Swap - StudySlot
    public static final String getUserStudySlots = "swap/study/slot/get";
    public static final String postUserStudySlots = "swap/study/slot/post";
    public static final String deleteUserStudySlots = "swap/study/slot/delete";
    public static final String getSlotDays = "swap/slot/day/get";
    public static final String getSlotTimes = "swap/slot/time/get";

    // Swap - Section Swap
    public static final String getSecSwapRequests = "swap/section/get";
    public static final String getSecSwapRequestInfo = "swap/section/get/{sec_swap_request_id}";
    public static final String postSecSwapRequest = "swap/section/post";
    public static final String postSecSwapRequestAction = "swap/section/action/post/" +
            "{sec_swap_request_id}";

    // Swap - Study Swap
    public static final String getStudySwapRequests = "swap/study/get";
    public static final String getStudySwapRequestInfo = "swap/study/get/{study_swap_request_id}";
    public static final String postStudySwapRequest = "swap/study/post";
    public static final String postStudySwapRequestAction = "swap/study/action/post/" +
            "{study_swap_request_id}";

    // Notification
    public static final String getNotifications = "notification/get";

}
