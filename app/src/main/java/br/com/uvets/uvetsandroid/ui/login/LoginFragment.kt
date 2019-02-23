package br.com.uvets.uvetsandroid.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.*
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        tvPassword.transformationMethod = PasswordTransformationMethod()

        btLogin.setOnClickListener {
            mViewModel.login(tvEmail.text.toString(), tvPassword.text.toString()) {
                startActivity(Intent(context!!, MainActivity::class.java))
                activity?.finish()
            }
        }

        btSignUp.setOnClickListener {
            startActivity(ContainerActivity.createSignUpView(context!!))
        }
    }

    private fun setUpObservers() {
        mViewModel.isLoadingLiveData.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading(it)
                btLogin.isEnabled = !it
                btSignUp.isEnabled = !it
            }
        })

        mViewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                showError(it)
            }
        })
    }

}
