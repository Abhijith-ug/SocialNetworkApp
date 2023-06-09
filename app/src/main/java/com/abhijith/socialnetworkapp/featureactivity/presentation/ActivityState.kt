package com.abhijith.socialnetworkapp.featureactivity.presentation

import android.app.Activity

data class ActivityState(
    val activities:List<Activity> = emptyList(),
    val isLoading:Boolean = true
)
