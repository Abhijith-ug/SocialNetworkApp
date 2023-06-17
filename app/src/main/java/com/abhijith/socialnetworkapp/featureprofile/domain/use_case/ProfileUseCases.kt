package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.core.domain.usecase.ToggleFollowUseCaseForUserUseCase

data class ProfileUseCases(
    val getProfileUseCase:GetProfileUseCase,
    val getSkillsUseCase: GetSkillsUseCase,
    val updateProfileUseCase:UpdateProfileUseCase,
    val setSkillSelectedUseCase: SetSkillSelectedUseCase,
    val getPostsForProfileUseCase: GetPostsForProfileUseCase,
    val searchUserUseCase: SearchUserUseCase,
    val toggleFollowUseCaseForUserUseCase: ToggleFollowUseCaseForUserUseCase
)
