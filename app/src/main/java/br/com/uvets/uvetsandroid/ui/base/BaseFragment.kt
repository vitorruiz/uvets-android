package br.com.uvets.uvetsandroid.ui.base

import android.view.View
import br.com.uvets.uvetsandroid.showErrorToast
import br.com.uvets.uvetsandroid.showSuccessToast
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import kotlinx.android.synthetic.main.include_loading_container.*

open class BaseFragment : androidx.fragment.app.Fragment(), BaseNavigator {

    override fun showSuccess(message: String) {
        showSuccessToast(message)
    }

    override fun showError(message: String) {
        showErrorToast(message)
    }

    override fun showLoader(isLoading: Boolean) {
        if (loading_container != null) {
            loading_container.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onLogoutSucceeded() {
        startActivity(ContainerActivity.loginView(context!!))
        activity?.finish()
    }
}