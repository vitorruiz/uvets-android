package br.com.uvets.uvetsandroid.ui.base

import androidx.annotation.StringRes
import br.com.uvets.uvetsandroid.business.network.RestError

interface BaseNavigator {
    // Request Handlers
    fun onRequestError(restError: RestError)
    fun onRequestFail(responseCode: Int)

    fun showSuccess(@StringRes resId: Int)
    fun showSuccess(message: String)
    fun showError(message: String)
    fun showError(@StringRes resId: Int)
    fun showLoader(isLoading: Boolean)

    fun onLogoutSucceeded()
}
