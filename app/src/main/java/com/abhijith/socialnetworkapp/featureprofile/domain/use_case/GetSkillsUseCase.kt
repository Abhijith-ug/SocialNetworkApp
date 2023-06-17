package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill
import com.abhijith.socialnetworkapp.core.domain.repository.ProfileRepository

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<List<Skill>>{
          return repository.getSkills()
    }
}