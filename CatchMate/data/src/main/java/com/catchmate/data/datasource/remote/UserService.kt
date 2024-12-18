package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.GetUserProfileByIdResponseDTO
import com.catchmate.data.dto.GetUserProfileResponseDTO
import com.catchmate.data.dto.PatchUserAlarmResponseDTO
import com.catchmate.data.dto.PatchUserProfileRequestDTO
import com.catchmate.data.dto.PatchUserProfileResponseDTO
import com.catchmate.data.dto.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.PostUserAdditionalInfoResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("user/profile")
    suspend fun getUserProfile(): Response<GetUserProfileResponseDTO?>

    @GET("user/profile/{profileUserId}")
    suspend fun getUserProfileById(
        @Path("profileUserId") profileUserId: Long,
    ): Response<GetUserProfileByIdResponseDTO?>

    @POST("user/additional-info")
    suspend fun postUserAdditionalInfo(
        @Body postUserAdditionalInfoRequestDTO: PostUserAdditionalInfoRequestDTO,
    ): Response<PostUserAdditionalInfoResponseDTO?>

    @PATCH("user/profile")
    suspend fun patchUserProfile(
        @Body patchUserProfileRequestDTO: PatchUserProfileRequestDTO,
    ): Response<PatchUserProfileResponseDTO>

    @PATCH("user/alarm")
    suspend fun patchUserAlarm(
        @Query("pushAgreement") pushAgreement: String,
    ): Response<PatchUserAlarmResponseDTO>
}
