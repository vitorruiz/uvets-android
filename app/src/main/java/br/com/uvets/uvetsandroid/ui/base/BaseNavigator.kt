package br.com.uvets.uvetsandroid.ui.base

interface BaseNavigator {
    fun showErrorMessage(errorMessage: String)
    //fun showErrorMessage(@IdRes resId: Int)
    fun showLoader(isLoading: Boolean)
}
