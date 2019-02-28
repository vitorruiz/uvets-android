package br.com.uvets.uvetsandroid.ui.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel<N>(application: Application) : AndroidViewModel(application) {
    protected var mNavigator: N? = null
    protected var mSharedPreferences = application.getSharedPreferences("uvets", Context.MODE_PRIVATE)

    fun attachNavigator(navigator: N) {
        mNavigator = navigator
    }

    protected fun getUserToken(): String? {
        return mSharedPreferences.getString("user_token", null)
    }
}