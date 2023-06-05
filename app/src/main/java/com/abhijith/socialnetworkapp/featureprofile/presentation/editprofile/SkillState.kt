package com.abhijith.socialnetworkapp.featureprofile.presentation.editprofile

import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill

data class SkillState(
    val skills:List<Skill> = emptyList(),
    val selectedSkills:List<Skill> = emptyList()
)
