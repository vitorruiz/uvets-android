package br.com.uvets.uvetsandroid.ui.base

interface BaseNavigator {
    fun showSuccess(message: String)
    fun showError(message: String)
    //fun showError(@IdRes resId: Int)
    fun showLoader(isLoading: Boolean)
    fun onLogoutSucceeded()
}
