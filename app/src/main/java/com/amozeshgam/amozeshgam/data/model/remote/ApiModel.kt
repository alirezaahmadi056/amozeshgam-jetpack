package com.amozeshgam.amozeshgam.data.model.remote


import com.google.gson.annotations.SerializedName

data class ApiRequestPhone(
    @SerializedName("phone") val phone: String,
)

data class ApiRequestCheckCode(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String,
    @SerializedName("device_name") val deviceName: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("secret_key") val secretKey: String,
)

data class ApiResponseGetFields(
    @SerializedName("intro") val intro: String,
    @SerializedName("fields") val field: ArrayList<ApiResponseGetFieldsData>,
    @SerializedName("price") val price: Int,
    @SerializedName("percent") val percent: Int,
    @SerializedName("total") val total: Int,
)

data class ApiResponseGetFieldsData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseCheckCode(
    @SerializedName("account") val account: Boolean,
    @SerializedName("id") val usernameId: Int,
    @SerializedName("hash_login") val hash: String,
    @SerializedName("public_key") val publicKey: String,
)

data class ApiResponseHomeData(
    @SerializedName("Story") val storyBanner: ArrayList<ApiResponseHomeDataStory>,
    @SerializedName("Fields") val fields: ArrayList<ApiResponseHomeDataFields>,
    @SerializedName("Courses") val courses: ArrayList<ApiResponseHomeDataCourses>,
    @SerializedName("Banners") val banners: ArrayList<ApiResponseHomeDataBannerAndSocial>,
    @SerializedName("Podcasts") val podcasts: ArrayList<ApiResponseHomeDataPodcast>,
    @SerializedName("Socials") val socials: ArrayList<ApiResponseHomeDataBannerAndSocial>,
    @SerializedName("Managers") val manager: ApiResponseHomeDataManager,
    @SerializedName("Roadmap") val roadMap: ArrayList<ApiResponseHomeDateRoadMap>,

    )

data class ApiResponseHomeDateRoadMap(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseHomeDataManager(
    @SerializedName("image") val image: String,
)

data class ApiResponseHomeDataStory(
    @SerializedName("id") val id: Int,
    @SerializedName("media") val media: String,
    @SerializedName("title") val title: String,
)

data class ApiResponseHomeDataFields(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseHomeDataCourses(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("time") val time: String,
    @SerializedName("teacher") val teacher: String,
    @SerializedName("status") val status: Int,
    @SerializedName("image") val image: String,
)

data class ApiResponseHomeDataBannerAndSocial(
    @SerializedName("link") val link: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseHomeDataPodcast(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("speaker") val speaker: String,
)

data class ApiRequestId(
    @SerializedName("id") val id: Int,
)

data class ApiResponseVideo(
    @SerializedName("video") val video: String,
)

data class ApiResponseSingleField(
    @SerializedName("videos") val videos: ArrayList<ApiResponseVideo>,
    @SerializedName("subfields") val subField: ArrayList<ApiResponseSinglePageSubFields>,
    @SerializedName("video") val video: String,
    @SerializedName("requirement") val requirement: ApiResponseSingleFieldRequirement?,
)

data class ApiResponseSingleFieldRequirement(
    @SerializedName("video") val video: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("time") val time: String,
    @SerializedName("id") val id: Int,
)

data class ApiResponseSingleSubField(
    @SerializedName("videos") val videos: ArrayList<ApiResponseVideo>,
    @SerializedName("video") val video: String,
    @SerializedName("subfield") val subFieldData: ApiResponseSingleSubFieldData,
)

data class ApiResponseSingleSubFieldData(
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("time") val time: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseSinglePageSubFields(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseAllCourses(
    @SerializedName("courses") var courses: ArrayList<ApiResponseAllCoursesList>,
)

data class ApiResponseAllCoursesList(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("time") val time: String,
    @SerializedName("teacher") val teacher: String,
    @SerializedName("status") val status: Int,
)

data class ApiRequestSendSupportMessage(
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
)

data class ApiResponseGetSupportMessages(
    @SerializedName("messages")
    val messages: ArrayList<ApiResponseGetSupportMessageData>,
)

data class ApiResponseGetSupportMessageData(
    @SerializedName("message")
    val message: String,
    @SerializedName("sender_type")
    val senderType: String,
    @SerializedName("time")
    val time: String,
)

data class ApiResponseAllPodcasts(
    @SerializedName("podcasts") val podcasts: ArrayList<ApiResponseAllPodcastsList>,
)

data class ApiResponseAllPodcastsList(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("speaker") val speaker: String,
)

data class ApiRequestSetName(
    @SerializedName("phone") val phone: String,
    @SerializedName("hash_login") val hash: String,
    @SerializedName("name") val name: String,
)


data class ApiResponseAADS(
    @SerializedName("fields") val fields: ArrayList<ApiResponseAADSFields>,
    @SerializedName("adv") val adv: ArrayList<ApiResponseVideo>,
    @SerializedName("disadv") val disadv: ArrayList<ApiResponseVideo>,
)

data class ApiResponseAADSFields(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String,
)

data class ApiResponseGetMyFavorites(
    @SerializedName("favorites") val favorites: ArrayList<ApiResponseGetMyFavoritesData>,
)

data class ApiResponseGetMyFavoritesData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String,
    @SerializedName("type") val type: String,
)

data class ApiRequestReportBug(
    @SerializedName("id") val id: Int,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("text") val text: String,
)

data class ApiResponseFaq(
    @SerializedName("faq") val faq: ArrayList<ApiResponseFaqData>,
)

data class ApiResponseFaqData(
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String,
)


data class ApiResponseInformation(
    @SerializedName("data") val data: ApiResponseInformationData,
)

data class ApiResponseInformationData(
    @SerializedName("first_name") val fName: String,
    @SerializedName("last_name") val lName: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("course_count") val courseCount: String,
    @SerializedName("student_count") val studentCount: String,
    @SerializedName("total_teach_duration") val totalTeachDuration: String,
    @SerializedName("rate") val rate: Float,
    @SerializedName("courses") val courses: ArrayList<ApiResponseInformationDataCourses>,
    @SerializedName("role") val role: ArrayList<String>,
    @SerializedName("team_members") val teamMembers: ArrayList<ApiResponseInformationDataTeam>,
)

data class ApiResponseInformationDataCourses(
    @SerializedName("Id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("thumbnail") val thumbnail: String,
)

data class ApiResponseInformationDataTeam(
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String,
    @SerializedName("avatar") val avatar: String,
)

data class ApiResponseGetTicketSubjects(
    @SerializedName("subjects") val subjects: ArrayList<String>,
)

data class ApiResponseGetTickets(
    @SerializedName("requests") val tickets: ArrayList<ApiResponseGetTicketsData>,
)

data class ApiResponseGetTicketsData(
    @SerializedName("subject") val subject: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("answer") val answer: String?,
)

data class ApiRequestGetCourse(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("user_id") val userId: Int,
)

data class ApiResponsePayment(
    @SerializedName("courses_count") val courseCount: Int,
    @SerializedName("roadmaps_count") val roadmapCount: Int,
    @SerializedName("roadmaps_price") val roadmapPrice: Int,
    @SerializedName("courses_price") val coursesPrice: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("wallet") val wallet: String,
)

data class ApiResponseGetProfile(
    @SerializedName("avatar") val avatar: String,
    @SerializedName("wallet_inventory") val walletInventory: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("cups") val cups: ArrayList<ApiResponseGetProfileCupsData>,
)

data class ApiResponseGetProfileCupsData(
    @SerializedName("title") val title: String,
    @SerializedName("level") val level: String,
    @SerializedName("image") val image: String,
)

data class ApiResponseGetCourse(
    @SerializedName("course") val course: ApiResponseGetCourseCourseData,
    @SerializedName("seasons") val seasons: ArrayList<ApiResponseGetCourseSeasons>,
    @SerializedName("comment") val comment: ArrayList<ApiResponseGetCourseComment>,
)

data class ApiResponseGetCourseCourseData(
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("time") val time: String,
    @SerializedName("teacher") val teacher: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("like") val like: Boolean,
    @SerializedName("is_buy") val hasBeenPurchased: Boolean,
    @SerializedName("is_cart") val courseInMyCart: Boolean,
    @SerializedName("final_price") val finalPrice: Int,
    @SerializedName("price") val price: String,
)

data class ApiResponseGetCourseSeasons(
    @SerializedName("title") val title: String,
    @SerializedName("sub") val sub: ArrayList<ApiResponseGetCourseSeasonsData>,
)

data class ApiResponseGetCourseSeasonsData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("time") val time: String,
)

data class ApiResponseGetCourseComment(
    @SerializedName("avatar") val avatar: String,
    @SerializedName("name") val name: String,
    @SerializedName("comment") val comment: String,
    @SerializedName("date") val date: String,
)

data class ApiResponseExplorer(
    @SerializedName("data") val data: ArrayList<ApiResponseExplorerData>,
)

data class ApiResponseExplorerData(
    @SerializedName("id") val id: Int,
    @SerializedName("media") val media: String,
    @SerializedName("important") val important: Boolean,
)

data class ApiResponseGetPodcast(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("voice") val voice: String,
    @SerializedName("speaker") val speaker: String,
    @SerializedName("image") val image: String,
    @SerializedName("is_like") val isLike: Boolean,
)

data class ApiResponseGetTour(
    @SerializedName("tours") val tours: ArrayList<ApiResponseGetTourData>,
)

data class ApiResponseGetTourData(
    @SerializedName("image") val image: String,
    @SerializedName("body") val body: String,
    @SerializedName("title") val title: String,
)


data class ApiResponseGetStory(
    @SerializedName("media") val media: String,
    @SerializedName("link") val link: String,
)


data class ApiRequestUpdateUser(
    @SerializedName("phone") val phone: String,
    @SerializedName("hash_login") val hash: String,
    @SerializedName("name") val name: String,
    @SerializedName("birthday") val date: String,
)

data class ApiResponseCart(
    @SerializedName("courses") val courses: ArrayList<ApiResponseCartCoursesAndRoadMapData>,
    @SerializedName("roadmaps") val roadMaps: ArrayList<ApiResponseCartCoursesAndRoadMapData>,
    @SerializedName("final_price") val finalPrice: Int,
)

data class ApiRequestAddPodcastToFavorite(
    @SerializedName("podcast_id") val podcastId: Int,
    @SerializedName("user_id") val userId: Int,
)

data class ApiRequestCheckHash(
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("hash_login") val hash: String,
)

data class ApiRequestDeleteFavorite(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
)

data class ApiResponseCartCoursesAndRoadMapData(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: String,
    @SerializedName("title") val title: String,
)

data class ApiRequestAddToCart(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
)

data class ApiResponseMessage(
    @SerializedName("message") val message: String,
)

data class ApiRequestDeActiveDevice(
    @SerializedName("id") val userId: Int,
    @SerializedName("device_id") val deviceId: String,
)

data class ApiRequestIdAndUserId(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("id") val id: Int,
)

data class ApiResponseGetDevices(
    @SerializedName("devices") val devices: ArrayList<ApiResponseGetDevicesData>,
)

data class ApiResponseGetDevicesData(
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("device_name") val deviceName: String,
)

data class ApiRequestDiscount(
    @SerializedName("code") val code: String,
    @SerializedName("price") val price: Int,
)

data class ApiResponseDiscount(
    @SerializedName("price") val price: Int,
    @SerializedName("total_discount") val total: Int,
)

data class ApiResponseGetRoadMap(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String,
    @SerializedName("levels") val level: ArrayList<ApiResponseGetRoadMapLevels>,

    )

data class ApiResponseGetRoadMapLevels(
    @SerializedName("title") val title: String,
    @SerializedName("progress_percent") val progress: Int,
    @SerializedName("tutorial") val tutorial: ArrayList<ApiResponseGetRoadMapTutorialData>,
    @SerializedName("exam") val exam: ArrayList<ApiResponseGetRoadMapExamData>,
)

data class ApiRequestCheckRoadMapExam(
    @SerializedName("exam_id")
    val examId: Int,
    @SerializedName("roadmap_id")
    val roadMapId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("answer")
    val answer: ArrayList<ApiRequestCheckRoadMapExamAnswer>
)

data class ApiRequestCheckRoadMapExamAnswer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("answer")
    val answer: String
)

data class ApiResponseGetRoadMapTutorialData(
    @SerializedName("title") val title: String,
    @SerializedName("detail") val detail: String?,
    @SerializedName("required_time") val requiredTime: String,
)

data class ApiResponseGetRoadMapExamData(
    @SerializedName("title") val title: String,
)

data class ApiResponseMyCourses(
    @SerializedName("courses") val courses: ArrayList<ApiResponseTitleAndImage>,
)

data class ApiResponseTitleAndImage(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("percent") val percent: String,
)

data class ApiRequestCreateComment(
    @SerializedName("comment") val comment: String,
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("device_id") val deviceId: String,
)

data class ApiResponseCheckVersion(
    @SerializedName("version") val version: Int,
)

data class ApiRequestVerificationEmail(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
)

data class ApiRequestCheckVerificationEmail(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("code") val code: Int,
)

data class ApiResponseAiQuestion(
    @SerializedName("questions") val questions: ArrayList<ApiResponseAiQuestionData>,
)

data class ApiResponseAiQuestionData(
    @SerializedName("id") val id: Int,
    @SerializedName("question") val question: String,
    @SerializedName("options") val options: ArrayList<String>?,
)

data class ApiRequestRegister(
    @SerializedName("id") val id: Int,
    @SerializedName("text") val text: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("subject") val subject: String,
)

data class ApiRequestUserIdAndDeviceId(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("device_id") val deviceId: String,
)

data class ApiResponseGetMessages(
    @SerializedName("messages") val message: ArrayList<ApiResponseGetMessagesData>,
)

data class ApiResponseGetMessagesData(
    @SerializedName("message") val message: String,
    @SerializedName("sender_type") val senderType: String,
    @SerializedName("time") val time: String,
)

data class ApiResponseMyRoadMap(
    @SerializedName("roadmaps") val roadMap: ArrayList<ApiResponseTitleAndImage>,
)

data class ApiResponseMyNotification(
    @SerializedName("notifications") val notifications: ArrayList<ApiResponseMyNotificationData>,
)

data class ApiResponseMyNotificationData(
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: String,
)

data class ApiResponseAllOrders(
    @SerializedName("orders") val orders: ArrayList<ApiResponseAllOrdersData>,
)

data class ApiResponseGetMyTransactions(
    @SerializedName("transactions") val transactions: ArrayList<ApiResponseGetMyTransactionsData>,
)

data class ApiResponseGetMyTransactionsData(
    @SerializedName("courses") val courses: ArrayList<ApiResponseTitleAndImage>,
    @SerializedName("roadmaps") val roadmaps: ArrayList<ApiResponseTitleAndImage>,
    @SerializedName("price") val price: Int,
    @SerializedName("date") val date: String,
    @SerializedName("tracking_serial") val trackingSerial: String,
    @SerializedName("status") val status: String,
)

data class ApiResponseGetPost(
    @SerializedName("is_like") val isLike: Boolean,
    @SerializedName("media") val media: String,
    @SerializedName("post") val role: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("name") val name: String,
)

data class ApiResponseAllOrdersData(
    @SerializedName("courses") val courses: ArrayList<ApiResponseTitleAndImage>,
    @SerializedName("roadmaps") val roadMaps: ArrayList<ApiResponseTitleAndImage>,
    @SerializedName("price") val price: String,
    @SerializedName("date") val date: String,
    @SerializedName("tracking_serial") val trackingCode: String,
    @SerializedName("status") val status: Boolean,
)

data class ApiResponseRoadMapQuestion(
    @SerializedName("questions") val questions: ArrayList<ApiResponseFieldQuestionData>,
)

data class ApiResponseFieldQuestionData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("options") val options: ArrayList<String>,
)

data class ApiResponseAiPlanningQuestion(
    @SerializedName("result") val result: String,
)

data class ApiRequestArrayString(
    @SerializedName("answers")
    val answers: ArrayList<String>,
)