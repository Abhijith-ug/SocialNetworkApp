package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

data class ProfileUseCases(
    val getProfileUseCase:GetProfileUseCase,
    val getSkillsUseCase: GetSkillsUseCase,
    val updateProfileUseCase:UpdateProfileUseCase,
    val setSkillSelectedUseCase: SetSkillSelectedUseCase
)
