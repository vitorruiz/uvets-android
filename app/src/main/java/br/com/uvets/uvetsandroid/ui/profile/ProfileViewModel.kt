package br.com.uvets.uvetsandroid.ui.profile

import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class ProfileViewModel : BaseViewModel<BaseNavigator>() {

    val userLiveData = MutableLiveData<User?>()

    init {
        userLiveData.postValue(userRepository.getUserData())
    }
}