package br.com.uvets.uvetsandroid.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.ContainerActivity
import br.com.uvets.uvetsandroid.ui.MainActivity
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(), LoginNavigator {

    private val REQUEST_SIGN_UP = 1

    private val mViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        mViewModel.attachNavigator(this)

        setUpView()
        setUpObservers()

        if (mViewModel.isUserAuthenticated()) {
            goToMainView()
        }
    }

    private fun setUpView() {
        tvPassword.transformationMethod = PasswordTransformationMethod()

        btLogin.setOnClickListener {
            if (isFormValid()) {
                mViewModel.login(tvEmail.text.toString(), tvPassword.text.toString())
            }
        }

        btSignUp.setOnClickListener {
            startActivityForResult(ContainerActivity.signUpView(context!!), REQUEST_SIGN_UP)
        }
    }

    private fun setUpObservers() {
    }

    private fun goToMainView() {
        startActivity(Intent(context!!, MainActivity::class.java))
        activity?.finish()
    }

    override fun onLoginSucceeded() {
        goToMainView()
    }

    private fun isFormValid(): Boolean {
        var result = true

        if (tvEmail.text.isNullOrEmpty()) {
            tiEmail.error = "Campo obrigatório"
            result = false
        } else {
            tiEmail.isErrorEnabled = false
        }

        if (tvPassword.text.isNullOrEmpty()) {
            tiPassword.error = "Campo obrigatório"
            result = false
        } else {
            tiPassword.isErrorEnabled = false
        }

        return result
    }

    override fun showLoader(isLoading: Boolean) {
        super.showLoader(isLoading)
        btLogin.isEnabled = !isLoading
        btSignUp.isEnabled = !isLoading
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SIGN_UP) {
            tvEmail.setText(data?.getStringExtra("user_email"))
            tvPassword.setText((data?.getStringExtra("user_pwd")))
            btLogin.performClick()
        }
    }

}
