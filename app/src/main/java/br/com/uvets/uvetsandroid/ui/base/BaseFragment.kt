package br.com.uvets.uvetsandroid.ui.base

import android.view.View
import br.com.uvets.uvetsandroid.showError
import kotlinx.android.synthetic.main.include_loading_container.*

open class BaseFragment : androidx.fragment.app.Fragment(), BaseNavigator {

    override fun showErrorMessage(errorMessage: String) {
        showError(errorMessage)
    }

    override fun showLoader(isLoading: Boolean) {
        if (loading_container != null) {
            loading_container.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}