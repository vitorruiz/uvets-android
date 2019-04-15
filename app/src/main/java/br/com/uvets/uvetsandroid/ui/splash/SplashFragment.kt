package br.com.uvets.uvetsandroid.ui.splash

import android.content.Intent
import android.os.Handler
import android.view.View
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import br.com.uvets.uvetsandroid.ui.MainActivity
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {

    private val mViewModel: SplashViewModel by viewModel()

    override fun getLayoutResource(): Int {
        return R.layout.splash_fragment
    }

    override fun initComponents(rootView: View) {
        Handler().postDelayed({
            if (mViewModel.isUserAuthenticated()) {
                startActivity(Intent(context!!, MainActivity::class.java))
            } else {
                startActivity(ContainerActivity.loginView(context!!))
            }
            activity?.finish()
        }, 1500L)
    }

}