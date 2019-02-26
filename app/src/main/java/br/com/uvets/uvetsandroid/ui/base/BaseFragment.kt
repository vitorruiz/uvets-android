package br.com.uvets.uvetsandroid.ui.base

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.include_loading_container.*
import kotlinx.android.synthetic.main.login_fragment.*

open class BaseFragment : Fragment(), BaseNavigator {

    override fun showErrorMessage(errorMessage: String) {
        if (coordinator != null) {
            Snackbar.make(coordinator, errorMessage, Snackbar.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun showLoader(isLoading: Boolean) {
        if (loading_container != null) {
            loading_container.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}