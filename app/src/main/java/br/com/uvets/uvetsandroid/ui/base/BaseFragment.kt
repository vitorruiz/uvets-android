package br.com.uvets.uvetsandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.uvets.uvetsandroid.business.network.RestError
import br.com.uvets.uvetsandroid.showErrorToast
import br.com.uvets.uvetsandroid.showSuccessToast
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import kotlinx.android.synthetic.main.include_loading_container.*

abstract class BaseFragment : Fragment(), BaseNavigator {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initComponents(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showSuccess(message: String) {
        showSuccessToast(message)
    }

    override fun showError(message: String) {
        showErrorToast(message)
    }

    override fun onRequestError(restError: RestError) {
        showErrorToast(restError.errorMessage)
    }

    override fun onRequestFail(responseCode: Int) {
        showErrorToast("Ocorreu um erro na requisição. Código: $responseCode")
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

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initComponents(rootView: View)
}