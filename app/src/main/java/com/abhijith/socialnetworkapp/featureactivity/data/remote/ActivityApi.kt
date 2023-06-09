package com.abhijith.socialnetworkapp.featureactivity.data.remote

import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.featureactivity.data.remote.dto.ActivityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityApi {

    @GET("/api/activity/get")
    suspend fun getActivities(
      @Query("page") page:Int = 0,
      @Query("pageSize") pageSize:Int = Constants.DEFAULT_PAGE_SIZE
    ):List<ActivityDto>
}