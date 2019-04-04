package br.com.uvets.uvetsandroid.ui.base

import br.com.uvets.uvetsandroid.business.network.RestError

interface BaseNavigator {
    // Request Handlers
    fun onRequestError(restError: RestError)

    fun onRequestFail(responseCode: Int)

    fun showSuccess(message: String)
    fun showError(message: String)
    //fun showError(@IdRes resId: Int)
    fun showLoader(isLoading: Boolean)
    fun onLogoutSucceeded()
}
