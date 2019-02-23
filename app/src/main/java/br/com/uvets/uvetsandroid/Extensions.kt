package br.com.uvets.uvetsandroid

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.include_loading_container.*
import kotlinx.android.synthetic.main.login_fragment.*

fun Fragment.loading(isLoading: Boolean) {
    if (loading_container != null) {
        loading_container.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

fun Fragment.showError(errorMessage: String) {
    if (coordinator != null) {
        Snackbar.make(coordinator, errorMessage, Snackbar.LENGTH_SHORT).show()
    }
}