package br.com.uvets.uvetsandroid.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private val mViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
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
