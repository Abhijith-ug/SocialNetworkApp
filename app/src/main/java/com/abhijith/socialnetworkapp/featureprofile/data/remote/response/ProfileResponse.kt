package com.abhijith.socialnetworkapp.featureprofile.data.remote.response

import com.abhijith.socialnetworkapp.featureprofile.domain.model.Profile
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill

data class ProfileResponse(
    val userId:String,
    val username:String,
    val bio :String,
    val followerCount:Int,
    val followingCount:Int,
    val postCount:Int,
    val profilePictureUrl: String,
    val bannerUrl: String?,
    val topSkills : List<SkillDto>?,
    val gitHubUrl: String?,
    val instagramUrl:String?,
    val linkedInUrl :String?,
    val isOwnProfile: Boolean,
    val isFollowing : Boolean

){
    fun toProfile(): Profile {
        return Profile(
            userId =userId,
            username = username,
            bio = bio,
            followingCount = followingCount,
            followerCount = followerCount,
            postCount = postCount,
            profilePictureUrl = profilePictureUrl,
            bannerUrl = bannerUrl,
            topSkills = topSkills?.map { it.toSkill() },
            gitHubUrl = gitHubUrl,
            instagramUrl = instagramUrl,
            linkedInUrl = linkedInUrl,
            isOwnProfile = isOwnProfile,
            isFollowing = isFollowing
        )
    }
}
