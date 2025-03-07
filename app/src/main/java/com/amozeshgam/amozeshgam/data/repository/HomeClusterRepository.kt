package com.amozeshgam.amozeshgam.data.repository

import com.amozeshgam.amozeshgam.data.api.AmozeshgamApiInterface
import com.amozeshgam.amozeshgam.data.api.ApiResponseTypeFace
import com.amozeshgam.amozeshgam.data.api.DaneshjooyarApiInterface
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddPodcastToFavorite
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddToCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestArrayString
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCheckHash
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCreateComment
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDeActiveDevice
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDeleteFavorite
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDiscount
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestGetCourse
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestIdAndHash
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestIdAndUserId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestRegister
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestReportBug
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSendSupportMessage
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestUpdateUser
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiPlanningQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllCourses
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllOrders
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllPodcasts
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseDiscount
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseExplorer
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseFaq
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourse
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetDevices
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFieldPackage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFields
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessages
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyFavorites
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyTransactions
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPodcast
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPost
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetProfile
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetSingleGuidField
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetStory
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTicketSubjects
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTickets
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseHomeData
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseInformation
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMessage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyCourses
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyNotification
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyRoadMap
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponsePayment
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseRoadMapQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleField
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleSubField
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseUserPrivateData
import com.amozeshgam.amozeshgam.di.qualifier.DaneshjooyarApi
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@ViewModelScoped
class HomeClusterRepository @Inject constructor() {
    @Inject
    lateinit var api: AmozeshgamApiInterface

    @DaneshjooyarApi
    @Inject
    lateinit var daneshjooyarApi: DaneshjooyarApiInterface

    @Inject
    lateinit var errorHandler: ErrorHandler
    suspend fun getHomeData(): ApiResponseHomeData? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetHomeDate())
        return response
    }

    suspend fun getPodcastsData(): ApiResponseAllPodcasts? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetAllPodcasts())
        return response
    }

    suspend fun getUserPrivateData(body: ApiRequestIdAndHash): ApiResponseUserPrivateData? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetUserPrivateData(body = body))
        return response
    }

    suspend fun getCoursesData(): ApiResponseAllCourses? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetAllCourses())
        return response
    }

    suspend fun getInformationData(): ApiResponseInformation? {
        val (response, _) = errorHandler.handelRequestApiValue(daneshjooyarApi.apiGetInformation())
        return response
    }

    suspend fun getActiveDevices(body: ApiRequestId): ApiResponseGetDevices? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetAllActiveDevices(body = body))
        return response
    }

    suspend fun getQuestionAiData(): ApiResponseAiQuestion? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetAiQuestion())
        return response
    }

    suspend fun getStoryData(body: ApiRequestId): ApiResponseGetStory? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetStory(body = body))
        return response
    }

    suspend fun getSingleCourseData(body: ApiRequestGetCourse): ApiResponseGetCourse? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetCourse(body = body))
        return response
    }

    suspend fun getSinglePodcastData(body: ApiRequestId): ApiResponseGetPodcast? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetPodcast(body = body))
        return response
    }

    suspend fun addCourseToFavorites(body: ApiRequestIdAndUserId): ApiResponseMessage? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiAddCourseToFavorite(body = body))
        return response
    }

    suspend fun getNewData(): ApiResponseExplorer? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetExplorerData())
        return response
    }

    suspend fun updateUser(body: ApiRequestUpdateUser): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiUpdateUser(body = body))
        return code == 200
    }

    suspend fun getAllMessages(body: ApiRequestId): ApiResponseGetMessages? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetSupportMessages(body = body))
        return response
    }

    suspend fun getUserData(body: ApiRequestId): ApiResponseGetProfile? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetProfileData(body = body))
        return response
    }


    suspend fun sendMessageWithMqtt(body: ApiRequestSendSupportMessage): Boolean {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiSendMessageWithMqtt(body = body))
        return response != null
    }

    suspend fun getFieldData(body: ApiRequestId): ApiResponseSingleField? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetSingleField(body = body))
        return response
    }

    suspend fun getSubFieldData(body: ApiRequestId): ApiResponseSingleSubField? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetSingleSubField(body = body))
        return response
    }

    suspend fun getFaqData(): ApiResponseFaq? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetFaq())
        return response
    }

    suspend fun getMyCourse(body: ApiRequestId): ApiResponseMyCourses? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetMyCourse(body = body))
        return response
    }

    suspend fun getOrders(body: ApiRequestId): ApiResponseAllOrders? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetAllOrder(body = body))
        return response
    }

    suspend fun getMyRoadMap(body: ApiRequestId): ApiResponseMyRoadMap? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetMyRoadMap(body = body))
        return response
    }

    suspend fun getMyNotification(body: ApiRequestId): ApiResponseMyNotification? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetMyNotification(body = body))
        return response
    }

    suspend fun getMyCart(body: ApiRequestId): ApiResponseCart? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetCart(body = body))
        return response
    }

    suspend fun addCourseToMyCart(body: ApiRequestAddToCart): Boolean {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiAddCourseToCart(body = body))
        return response != null
    }

    suspend fun createCourseComment(body: ApiRequestCreateComment): Boolean {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiAddComment(body = body))
        return response != null
    }

    suspend fun getMyFavorites(body: ApiRequestId): ApiResponseGetMyFavorites? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetMyFavorites(body = body))
        return response
    }

    suspend fun getTicketSubjects(): ApiResponseGetTicketSubjects? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetTicketSubjects())
        return response
    }

    suspend fun getTickets(body: ApiRequestId): ApiResponseGetTickets? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetTickets(body = body))
        return response
    }

    suspend fun requestRegister(body: ApiRequestRegister): ApiResponseMessage? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiRequestRegister(body = body))
        return response
    }

    suspend fun getPaymentData(body: ApiRequestId): ApiResponsePayment? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiPayment(body = body))
        return response
    }

    suspend fun discountPayment(body: ApiRequestDiscount): ApiResponseDiscount? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiDiscountApply(body = body))
        return response
    }


    suspend fun addPodcastToFavorite(body: ApiRequestAddPodcastToFavorite): ApiResponseMessage? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiAddPodcastToFavorite(body = body))
        return response
    }

    suspend fun checkHash(body: ApiRequestCheckHash): Int? {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiCheckHash(body = body))
        return code
    }

    suspend fun addRoadMapToMyCart(body: ApiRequestAddToCart): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiAddRoadMapToCart(body = body))
        return code == 200
    }

    suspend fun getFields(): ApiResponseTypeFace<ApiResponseGetFields> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiGetFields())
        return ApiResponseTypeFace(response, code)
    }

    suspend fun getFieldPackage(body: ApiRequestId): ApiResponseTypeFace<ApiResponseGetFieldPackage> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiGetFieldPackage(body = body))
        return ApiResponseTypeFace(response, code)
    }

    suspend fun getAiQuestion(): ApiResponseTypeFace<ApiResponseAiQuestion> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiGetAiQuestion())
        return ApiResponseTypeFace(response, code)
    }

    suspend fun sendAiAnswer(body: ApiRequestArrayString): ApiResponseTypeFace<ApiResponseAiPlanningQuestion> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiAnswersAiPlanning(body = body))
        return ApiResponseTypeFace(response, code)
    }

    suspend fun getRoadMapQuestion(body: ApiRequestId): ApiResponseTypeFace<ApiResponseRoadMapQuestion> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiGetRoadMapQuestion(body = body))
        return ApiResponseTypeFace(response, code)
    }

    suspend fun uploadAvatar(id: Int, hash: String, avatar: File?): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(
            api.apiUploadAvatar(
                id = MultipartBody.Part.createFormData("id", id.toString()),
                hash_login = MultipartBody.Part.createFormData("hash_login", hash),
                avatar = MultipartBody.Part.createFormData(
                    "avatar",
                    avatar!!.name,
                    avatar.asRequestBody("image/*".toMediaTypeOrNull())
                )
            )
        )
        return code == 200
    }

    suspend fun getGuidFiledData(body: ApiRequestId): ApiResponseTypeFace<ApiResponseGetSingleGuidField> {
        return errorHandler.handelRequestApiValue(api.apiGetGuidField(body = body))
    }

    suspend fun getPostDate(body: ApiRequestIdAndUserId): ApiResponseGetPost? {
        val (response, _) = errorHandler.handelRequestApiValue(
            api.apiGetPost(
                body = body
            )
        )
        return response
    }

    suspend fun addPostToFavorite(body: ApiRequestIdAndUserId): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiAddPostToFavorite(body = body))
        return code == 200
    }

    suspend fun deleteFromFavorite(body: ApiRequestDeleteFavorite): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiDeleteFromFavorite(body = body))
        return code == 200
    }

    suspend fun getMyTransactions(body: ApiRequestId): ApiResponseGetMyTransactions? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetMyTransactions(body = body))
        return response
    }

    suspend fun deActiveDevice(body: ApiRequestDeActiveDevice): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiDeActiveDevice(body = body))
        return code == 200
    }

    suspend fun reportBug(body: ApiRequestReportBug): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiReportBug(body = body))
        return code == 200
    }

    suspend fun removeCourseFromCart(body: ApiRequestIdAndUserId): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiDeleteCourseFromCart(body = body))
        return code == 200
    }

    suspend fun removeRoadMapFromCart(body: ApiRequestIdAndUserId): Boolean {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiDeleteRoadMapFromCart(body = body))
        return code == 200
    }
}