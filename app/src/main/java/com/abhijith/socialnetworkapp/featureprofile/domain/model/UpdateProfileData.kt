package com.abhijith.socialnetworkapp.featureprofile.domain.model

import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill

data class UpdateProfileData(
    val username:String,
    val bio:String,
    val gitHubUrl:String,
    val instagramUrl:String,
    val linkedInUrl:String,
    val skills:List<Skill>
)
