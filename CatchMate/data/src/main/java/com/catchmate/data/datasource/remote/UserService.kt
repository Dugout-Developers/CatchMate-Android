package com.catchmate.data.datasource.remote

import com.catchmate.data.dto.user.GetUserProfileByIdResponseDTO
import com.catchmate.data.dto.user.GetUserProfileResponseDTO
import com.catchmate.data.dto.user.PatchUserAlarmResponseDTO
import com.catchmate.data.dto.user.PatchUserProfileRequestDTO
import com.catchmate.data.dto.user.PatchUserProfileResponseDTO
import com.catchmate.data.dto.user.PostUserAdditionalInfoRequestDTO
import com.catchmate.data.dto.user.PostUserAdditionalInfoResponseDTO
import com.catchmate.domain.model.enumclass.AlarmType
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
    ): Response<PatchUserProfileResponseDTO?>

    // isEnabled : Y/N
    @PATCH("user/alarm")
    suspend fun patchUserAlarm(
        @Query("alarmType") alarmType: AlarmType,
        @Query("isEnabled") isEnabled: String,
    ): Response<PatchUserAlarmResponseDTO?>
}
