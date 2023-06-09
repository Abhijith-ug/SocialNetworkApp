package com.abhijith.socialnetworkapp.featureactivity.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Activity
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.featureactivity.data.paging.ActivitySource
import com.abhijith.socialnetworkapp.featureactivity.data.remote.ActivityApi
import com.abhijith.socialnetworkapp.featureactivity.domain.repository.ActivityRepository
import com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource.PostSource
import kotlinx.coroutines.flow.Flow

class ActivityRepositoryImpl(
    private val api:ActivityApi
):ActivityRepository{
    override val activities: Flow<PagingData<Activity>>
        get() =  Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)) {
            ActivitySource(api)
        }.flow

}