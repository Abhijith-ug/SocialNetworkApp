package com.abhijith.socialnetworkapp.featureactivity.domain.use_case

import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Activity
import com.abhijith.socialnetworkapp.featureactivity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow

class GetActivityUseCase(
    private val repository: ActivityRepository
) {
     operator fun invoke():Flow<PagingData<Activity>>{
        return repository.activities
    }
}