package br.com.uvets.uvetsandroid.ui.base

import androidx.lifecycle.ViewModel
import br.com.uvets.uvetsandroid.data.repository.UserRepository

open class BaseViewModel<N : BaseNavigator>(val userRepository: UserRepository) : ViewModel() {
    protected var mNavigator: N? = null

    fun attachNavigator(navigator: N) {
        mNavigator = navigator
    }

    fun doLogout() {
        userRepository.logoutUser()
        mNavigator?.onLogoutSucceeded()
    }
}