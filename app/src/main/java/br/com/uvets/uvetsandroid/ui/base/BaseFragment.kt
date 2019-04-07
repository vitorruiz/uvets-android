package br.com.uvets.uvetsandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.uvets.uvetsandroid.business.network.RestError
import br.com.uvets.uvetsandroid.showErrorToast
import br.com.uvets.uvetsandroid.showSuccessToast
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import br.com.uvets.uvetsandroid.ui.MainActivity
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.include_loading_container.*
import kotlinx.android.synthetic.main.include_toolbar.*

abstract class BaseFragment : Fragment(), BaseNavigator, IOnBackPressed {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpToolbar()
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
        activity?.finish()
        startActivity(ContainerActivity.loginView(context!!))
    }

    protected fun getNavigationController(): FragNavController? {
        return (activity as? MainActivity)?.fragNavController
    }

    protected fun setUpToolbar() {
        val parentActivity = (activity as? AppCompatActivity)
        if (toolbar != null) {
            setTitle(getTile())
            parentActivity?.setSupportActionBar(toolbar)
            parentActivity?.supportActionBar?.setDisplayShowTitleEnabled(false)
            getNavigationController()?.let {
                if (!it.isRootFragment) {
                    parentActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }

    protected open fun setTitle(title: String?) {
        if (toolbarTitle != null) {
            toolbarTitle.text = title
        }
    }

    override fun onBackPressed() {

    }

    protected open fun getTile(): String? = null

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initComponents(rootView: View)
}

interface IOnBackPressed {
    fun onBackPressed()
}