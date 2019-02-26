package br.com.uvets.uvetsandroid.ui.base

import android.arch.lifecycle.ViewModel

open class BaseViewModel<N> : ViewModel() {
    protected var mNavigator: N? = null

    fun attachNavigator(navigator: N) {
        mNavigator = navigator
    }
}