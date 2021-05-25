package com.playdate.app.data.api;

import com.playdate.app.model.CommonModel;
import com.playdate.app.model.FriendsListModel;
import com.playdate.app.model.GetCommentModel;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.InterestsMain;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.MatchListModel;
import com.playdate.app.model.NotificationModel;
import com.playdate.app.model.RegisterResult;
import com.playdate.app.model.RestMain;
import com.playdate.app.model.SavedPostModel;
import com.playdate.app.ui.social.model.PostHistory;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {

    @FormUrlEncoded
    @POST("user/register")
    Call<RegisterResult> registerUser(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> login(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/verify-otp")
    Call<LoginResponse> verifyOtp(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/update-profile")
    Call<LoginResponse> updateProfile(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/reset-password")
    Call<LoginResponse> resetPassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/forgot-password-sent-otp")
    Call<LoginResponse> forgotPasswordSentOtp(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/resend-verify-otp")
    Call<LoginResponse> resendVerifyOtp(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-restaurants")
    Call<RestMain> restaurants(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-interested")
    Call<InterestsMain> interested(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @Multipart
    @POST("user/update-profile-image")
    Call<LoginResponse> uploadImage(@Header("Authorization") String token, @Part MultipartBody.Part filePart);

    @Multipart
    @POST("user/update-profile-video")
    Call<LoginResponse> uploadProfileVideo(@Header("Authorization") String token, @Part MultipartBody.Part filePart);

    @FormUrlEncoded
    @POST("user/social-signin")
    Call<LoginResponse> sociallogin(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-users-suggestions")
    Call<GetUserSuggestion> getUserSuggestion(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/add-friend-request")
    Call<CommonModel> addFriendRequest(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-notifications")
    Call<NotificationModel> getNotification(@Header("Authorization") String token, @FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST("user/get-user-match-list")
    Call<MatchListModel> getUserMatchList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-friends-list")
    Call<FriendsListModel> getFriendsList(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/update-username")
    Call<LoginResponse> updateUsername(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/update-notification")
    Call<CommonModel> updateNotification(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/friend-request-status-update")
    Call<CommonModel> friendRequestStatus(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/add-user-match-request")
    Call<CommonModel> addUserMatchRequest(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/match-request-status-update")
    Call<CommonModel> matchRequestStatusUpdate(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @Multipart
    @POST("user/add-media?section=feed&mediaType=image")
    Call<LoginResponse> uploadImageToFeed(@Header("Authorization") String token, @Part MultipartBody.Part filePart);

    @Multipart
    @POST("user/add-media?section=feed&mediaType=video")
    Call<LoginResponse> uploadVideoToFeed(@Header("Authorization") String token, @Part MultipartBody.Part filePart);

    @FormUrlEncoded
    @POST("user/add-post-feed")
    Call<LoginResponse> addPostFeed(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-post-feed")
    Call<PostHistory> getPostFeed(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/add-post-like-unlike")
    Call<LoginResponse> addPostLikeUnlike(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/post-notification-on-off")
    Call<LoginResponse> notificationOnOff(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/post-file-save-gallery")
    Call<CommonModel> postFileSaveGallery(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-post-save-gallery")
    Call<SavedPostModel> getPostSaveGallery(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/get-profile-details")
    Call<GetProfileDetails> getProfileDetails(@Header("Authorization") String token, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/add-post-comment")
    Call<CommonModel> addPostComment(@Header("Authorization") String token, @FieldMap Map<String, String> param);
    @FormUrlEncoded
    @POST("user/get-post-comments")
    Call<GetCommentModel> getPostComment(@Header("Authorization") String token, @FieldMap Map<String, String> param);



}
