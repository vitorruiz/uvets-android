package br.com.uvets.uvetsandroid.ui.profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.prefs.PrefsDataStore
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class ProfileViewModel(application: Application): BaseViewModel<BaseNavigator>(application) {

    val userLiveData = MutableLiveData<User>()

    init {
        userLiveData.postValue(PrefsDataStore.getUserData())
    }
}