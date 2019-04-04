package br.com.uvets.uvetsandroid.ui.profile


import android.view.View
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private val mViewModel: ProfileViewModel by viewModel()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_profile
    }

    override fun initComponents(rootView: View) {
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
        activity?.title = "Perfil"

        llLogout.setOnClickListener {
            mViewModel.doLogout()
        }
    }

}
