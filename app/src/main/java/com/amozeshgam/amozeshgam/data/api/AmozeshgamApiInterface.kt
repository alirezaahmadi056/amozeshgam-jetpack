package com.amozeshgam.amozeshgam.data.api

import com.amozeshgam.amozeshgam.BuildConfig
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddPodcastToFavorite
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddToCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestArrayString
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCheckCode
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCheckHash
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCreateComment
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDeActiveDevice
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDeleteFavorite
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDiscount
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestGetCourse
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestIdAndUserId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestPhone
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestRegister
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestReportBug
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSendSupportMessage
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSetName
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestUpdateUser
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAADS
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiPlanningQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllCourses
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllOrders
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllPodcasts
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCheckCode
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCheckVersion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseDiscount
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseExplorer
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseFaq
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourse
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetDevices
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFields
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessages
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyFavorites
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyTransactions
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPodcast
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPost
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetProfile
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetRoadMap
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetStory
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTicketSubjects
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTickets
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTour
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseHomeData
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMessage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyCourses
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyNotification
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyRoadMap
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponsePayment
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseRoadMapQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleField
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleSubField
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AmozeshgamApiInterface {
    @GET("/api/home")
    fun apiGetHomeDate(@Header("x-api-key") apiKey: String = BuildConfig.APIKEY): Call<ApiResponseHomeData>

    @POST("/api/login")
    fun apiLogin(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestPhone,
    ): Call<Any>

    @POST("/api/check_code")
    fun apiCheckCode(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestCheckCode,
    ): Call<ApiResponseCheckCode>

    @GET("/api/all/courses")
    fun apiGetAllCourses(@Header("x-api-key") apiKey: String = BuildConfig.APIKEY): Call<ApiResponseAllCourses>

    @GET("/api/all/podcasts")
    fun apiGetAllPodcasts(@Header("x-api-key") apiKey: String = BuildConfig.APIKEY): Call<ApiResponseAllPodcasts>

    @GET("/api/get/fields")
    fun apiGetFields(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseGetFields>

    @POST("/api/update/user")
    fun apiSetUserName(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestSetName,
    ): Call<Any>


    @POST("/api/get/course")
    fun apiGetCourse(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestGetCourse,
    ): Call<ApiResponseGetCourse>

    @POST("/api/get/podcast")
    fun apiGetPodcast(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestId,
    ): Call<ApiResponseGetPodcast>

    @POST("/api/get/story")
    fun apiGetStory(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestId,
    ): Call<ApiResponseGetStory>


    @POST("/api/update/user")
    fun apiUpdateUser(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestUpdateUser,
    ): Call<Any>

    @POST("/api/get/cart")
    fun apiGetCart(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestId,
    ): Call<ApiResponseCart>

    @POST("/api/course/create/cart")
    fun apiAddCourseToCart(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestAddToCart,
    ): Call<ApiResponseMessage>

    @POST("/api/roadmap/create/cart")
    fun apiAddRoadMapToCart(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestAddToCart,
    ): Call<ApiResponseMessage>

    @POST("/api/create/comment")
    fun apiAddComment(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestCreateComment,
    ): Call<ApiResponseMessage>

    @POST("/api/device/deactive")
    fun apiDeActiveDevice(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestDeActiveDevice,
    ): Call<ApiResponseMessage>

    @POST("/api/course/delete/order")
    fun apiDeleteCourseFromCart(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestIdAndUserId,
    ): Call<ApiResponseMessage>

    @POST("/api/roadmap/delete/order")
    fun apiDeleteRoadMapFromCart(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestIdAndUserId,
    ): Call<ApiResponseMessage>

    @POST("/api/devices")
    fun apiGetAllActiveDevices(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestId,
    ): Call<ApiResponseGetDevices>

    @POST("/api/discount/apply")
    fun apiDiscountApply(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestDiscount,
    ): Call<ApiResponseDiscount>

    @POST("/api/roadmap")
    fun apiGetRoadMap(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestId,
    ): Call<ApiResponseGetRoadMap>

    @POST("/api/store/message")
    fun apiSendMessageWithMqtt(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestSendSupportMessage,
    ): Call<ApiResponseMessage>

    @POST("/api/course/get/mycourse")
    fun apiGetMyCourse(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY, @Body body: ApiRequestId,
    ): Call<ApiResponseMyCourses>

    @GET("/api/version")
    fun apiGetAppVersion(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseCheckVersion>

    @POST("/api/favorite/add")
    fun apiAddCourseToFavorite(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestIdAndUserId,
    ): Call<ApiResponseMessage>

    @GET("/api/tour")
    fun apiGetTour(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseGetTour>



    @GET("/api/explorer")
    fun apiGetExplorerData(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseExplorer>

    @GET("/api/ai/question")
    fun apiGetAiQuestion(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseAiQuestion>

    @POST("/api/request/register")
    fun apiRequestRegister(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestRegister,
    ): Call<ApiResponseMessage>

    @POST("/api/support/messages")
    fun apiGetSupportMessages(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseGetMessages>

    @POST("/api/profile")
    fun apiGetProfileData(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseGetProfile>

    @GET("/api/get/aads")
    fun apiGetAADSData(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseAADS>

    @POST("/api/single/field")
    fun apiGetSingleField(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseSingleField>

    @POST("/api/single/subfield")
    fun apiGetSingleSubField(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseSingleSubField>

    @GET("/api/get/faq")
    fun apiGetFaq(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseFaq>

    @POST("/api/roadmap/get/myroadmap")
    fun apiGetMyRoadMap(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseMyRoadMap>

    @POST("/api/get/notifications")
    fun apiGetMyNotification(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseMyNotification>

    @GET("/api/request/get/subjects")
    fun apiGetTicketSubjects(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
    ): Call<ApiResponseGetTicketSubjects>

    @POST("/api/request")
    fun apiGetTickets(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseGetTickets?>

    @POST("/api/favorite")
    fun apiGetMyFavorites(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseGetMyFavorites>

    @POST("/api/order/registration")
    fun apiPayment(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponsePayment>

    @POST("/api/podcast/favorite/add")
    fun apiAddPodcastToFavorite(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestAddPodcastToFavorite,
    ): Call<ApiResponseMessage>

    @POST("/api/device/check")
    fun apiCheckHash(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestCheckHash,
    ): Call<ApiResponseMessage>

    @Multipart
    @POST("/api/upload/avatar")
    fun apiUploadAvatar(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Part hash_login: MultipartBody.Part,
        @Part id: MultipartBody.Part,
        @Part avatar: MultipartBody.Part,
    ): Call<Any>

    @POST("/api/favorite/delete")
    fun apiDeleteFromFavorite(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestDeleteFavorite,
    ): Call<ApiResponseMessage>

    @POST("/api/create/bugfeedback")
    fun apiReportBug(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestReportBug,
    ): Call<ApiResponseMessage>

    @POST("/api/all/order")
    fun apiGetAllOrder(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseAllOrders>

    @POST("/api/get/post")
    fun apiGetPost(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestIdAndUserId,
    ): Call<ApiResponseGetPost>

    @POST("/api/post/favorite/add")
    fun apiAddPostToFavorite(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestIdAndUserId,
    ): Call<ApiResponseMessage>

    @POST("/api/transactions")
    fun apiGetMyTransactions(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseGetMyTransactions>

    @POST("/api/ai/planning")
    fun apiAnswersAiPlanning(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestArrayString,
    ): Call<ApiResponseAiPlanningQuestion>

    @POST("/api/get/survey")
    fun apiGetRoadMapQuestion(
        @Header("x-api-key") apiKey: String = BuildConfig.APIKEY,
        @Body body: ApiRequestId,
    ): Call<ApiResponseRoadMapQuestion>
}
