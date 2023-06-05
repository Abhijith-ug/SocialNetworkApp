package com.abhijith.socialnetworkapp.featureprofile.presentation.editprofile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureprofile.domain.model.UpdateProfileData
import com.abhijith.socialnetworkapp.featureprofile.domain.use_case.ProfileUseCases
import com.abhijith.socialnetworkapp.featureprofile.presentation.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _githubTextFieldState = mutableStateOf(StandardTextFieldState())
    val githubTextFieldState: State<StandardTextFieldState> = _githubTextFieldState

    private val _instagramTextFieldState = mutableStateOf(StandardTextFieldState())
    val instagramTextFieldState: State<StandardTextFieldState> = _instagramTextFieldState

    private val _linkedInTextFieldState = mutableStateOf(StandardTextFieldState())
    val linkedInTextFieldState: State<StandardTextFieldState> = _linkedInTextFieldState

    private val _bioState = mutableStateOf(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _skills = mutableStateOf(SkillState())
    val skills: State<SkillState> = _skills

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri:State<Uri?> = _bannerUri

    private val _profilePictureUri = mutableStateOf<Uri?>(null)
    val profilePictureUri:State<Uri?> = _profilePictureUri


    init {
        savedStateHandle.get<String>("userId")?.let { userId ->

            getSkills()
            getProfile(userId)


        }
    }

    private fun getSkills() {
        viewModelScope.launch {
            val result = profileUseCases.getSkillsUseCase()
            when (result) {
                is Resource.Success -> {
                    println("skills: ${result.data}")
                    _skills.value = _skills.value.copy(
                        skills = result.data ?: kotlin.run {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar
                                    (UiText.StringResource(R.string.error_couldnt_load_skills))
                            )
                            return@launch
                        }
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            result.uiText ?: UiText.unKnownError()
                        )
                    )
                    return@launch
                }
            }

        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(isLoading = true)
            val result = profileUseCases.getProfileUseCase(userId)
            when (result) {
                is Resource.Success -> {
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_couldnt_load_profile)
                            )
                        )
                        return@launch
                    }
                    _usernameState.value = usernameState.value.copy(
                        text = profile.username
                    )
                    _githubTextFieldState.value = _githubTextFieldState.value.copy(
                        text = profile.gitHubUrl ?: ""
                    )
                    _instagramTextFieldState.value = _instagramTextFieldState.value.copy(
                        text = profile.instagramUrl ?: ""
                    )
                    _linkedInTextFieldState.value = _linkedInTextFieldState.value.copy(
                        text = profile.linkedInUrl ?: ""
                    )
                    _bioState.value = bioState.value.copy(
                        text = profile.bio
                    )
                    _skills.value = _skills.value.copy(
                        selectedSkills = profile.topSkills ?: emptyList()
                    )
                    _profileState.value = profileState.value.copy(
                        profile = profile,
                        isLoading = false
                    )

                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            result.uiText ?: UiText.unKnownError()
                        )
                    )
                    return@launch
                }
            }
        }
    }

    private fun updateProfile(){
        viewModelScope.launch {
            val result = profileUseCases.updateProfileUseCase(
                updateProfileData = UpdateProfileData(
                    username = usernameState.value.text,
                    bio = bioState.value.text,
                    gitHubUrl = githubTextFieldState.value.text,
                    instagramUrl = instagramTextFieldState.value.text,
                    linkedInUrl = linkedInTextFieldState.value.text,
                    skills = skills.value.selectedSkills
                ),
                profilePictureUri = profilePictureUri.value,
                bannerUri = bannerUri.value
            )
            when(result){
                is Resource.Success ->{
                    _eventFlow.emit(UiEvent.ShowSnackBar(
                            UiText.StringResource(R.string.updated_profile)
                    ))
                    _eventFlow.emit(UiEvent.NavigateUp)

                }
                is Resource.Error -> {
                   _eventFlow.emit(UiEvent.ShowSnackBar(result.uiText ?:UiText.unKnownError()))
                }
            }
        }
    }



    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredGitHubUrl -> {
                _githubTextFieldState.value = _githubTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredInstagramUrl -> {
                _instagramTextFieldState.value = _instagramTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredLinkedInUrl -> {
                _linkedInTextFieldState.value = _linkedInTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredBio -> {
               _bioState.value = bioState.value.copy(
                    text = event.value
                )
            }

            is EditProfileEvent.CropProfilePicture -> {
               _profilePictureUri.value = event.uri
            }

            is EditProfileEvent.CropBannerImage -> {
                _bannerUri.value = event.uri
            }

            is EditProfileEvent.SetSkillSelected -> {
                val result = profileUseCases.setSkillSelectedUseCase(
                    selectedSkills = skills.value.selectedSkills,
                    event.skill
                )
                viewModelScope.launch {
                    when(result){
                        is Resource.Success -> {
                            _skills.value = skills.value.copy(
                                selectedSkills = result.data?:kotlin.run {
                                    _eventFlow.emit(UiEvent.ShowSnackBar(UiText.unKnownError()))
                                    return@launch
                                }
                            )
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackBar(
                                uiText = result.uiText ?: UiText.unKnownError()
                            ))
                        }
                    }
                }
                }


            is EditProfileEvent.UpdateProfile -> {
                updateProfile()


            }
        }
    }

}