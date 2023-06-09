package com.abhijith.socialnetworkapp.featureactivity.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.abhijith.socialnetworkapp.featureactivity.domain.repository.ActivityRepository
import com.abhijith.socialnetworkapp.featureactivity.domain.use_case.GetActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityUseCase: GetActivityUseCase
):ViewModel() {



    private val _state = mutableStateOf(ActivityState())
    val state: State<ActivityState> = _state
    val activities = getActivityUseCase().cachedIn(viewModelScope)


    fun onEvent(event: ActivityEvent){
        when(event){
           is ActivityEvent.ClickedOnParent -> {

           }

            is ActivityEvent.ClickedOnUser -> {

            }
        }
    }


}