package br.com.uvets.uvetsandroid.ui.profile


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.pushFragmentWithAnim
import br.com.uvets.uvetsandroid.ui.about.AboutFragment
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private val mViewModel: ProfileViewModel by viewModel()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_profile
    }

    override fun initComponents(rootView: View, savedInstanceState: Bundle?) {
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()
    }

    private fun setUpObservers() {
        mViewModel.userLiveData.observe(this, Observer {
            tvUserName.text = it?.name
            tvUserEmail.text = it?.email
        })
    }

    private fun setUpView() {
        llLogout.setOnClickListener {
            mViewModel.doLogout()
        }

        llAbout.setOnClickListener {
            getNavigationController()?.pushFragmentWithAnim(AboutFragment())
        }
    }

}
