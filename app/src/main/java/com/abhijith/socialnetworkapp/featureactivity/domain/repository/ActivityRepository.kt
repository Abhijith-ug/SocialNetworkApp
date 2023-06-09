package com.abhijith.socialnetworkapp.featureactivity.domain.repository

import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {

    val activities:Flow<PagingData<Activity>>
}