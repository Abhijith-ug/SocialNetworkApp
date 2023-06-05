package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill
import com.abhijith.socialnetworkapp.featureprofile.domain.util.ProfileConstants

class SetSkillSelectedUseCase {

    operator fun invoke(
        selectedSkills:List<Skill>,
        skillToToggle:Skill
    ):Resource<List<Skill>>{
        if (skillToToggle in selectedSkills){
            return Resource.Success(selectedSkills - skillToToggle)
        }
      return   if (selectedSkills.size >= ProfileConstants.MAX_SELECTED_SKILL_COUNT){
            Resource.Error(uiText = UiText.StringResource(R.string.error_max_skills))
        } else{
             return Resource.Success(selectedSkills + skillToToggle)
      }

    }
}