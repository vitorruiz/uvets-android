package br.com.uvets.uvetsandroid.ui.signup

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUpFragment : Fragment() {

    private lateinit var mViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        setUpView()
    }

    private fun setUpView() {
        tvPassword.transformationMethod = PasswordTransformationMethod()
        tvConfirmPassword.transformationMethod = PasswordTransformationMethod()

        btSignUp.setOnClickListener {
            if (isFormValid()) {

            }
        }
    }

    private fun isFormValid(): Boolean {
        var result = true

        if (tvFullName.text.isNullOrEmpty()) {
            tiFullName.error = "Campo obrigatório"
            result = false
        } else {
            tiFullName.error = null
        }

        if (tvDoc.text.isNullOrEmpty()) {
            tiDoc.error = "Campo obrigatório"
            result = false
        } else {
            tiDoc.error = null
        }

        if (tvPhone.text.isNullOrEmpty()) {
            tiPhone.error = "Campo obrigatório"
            result = false
        } else {
            tiPhone.error = null
        }

        if (tvEmail.text.isNullOrEmpty()) {
            tiEmail.error = "Campo obrigatório"
            result = false
        } else {
            tiEmail.error = null
        }

        if (tvAddress.text.isNullOrEmpty()) {
            tiAddress.error = "Campo obrigatório"
            result = false
        } else {
            tiAddress.error = null
        }

        if (tvPassword.text.isNullOrEmpty()) {
            tiPassword.error = "Campo obrigatório"
            result = false
        } else {
            tiPassword.error = null
        }

        if (tvConfirmPassword.text.isNullOrEmpty()) {
            tiConfirmPassword.error = "Campo obrigatório"
            result = false
        } else {
            tiConfirmPassword.error = null
        }

        if (!tvPassword.text.isNullOrEmpty() && !tvConfirmPassword.text.isNullOrEmpty()) {
            if (tvPassword.text.toString() != tvConfirmPassword.text.toString()) {
                tiPassword.error = "As senhas devem ser iguais"
                tiConfirmPassword.error = "As senhas devem ser iguais"
                result = false
            } else {
                tiPassword.error = null
                tiConfirmPassword.error = null
            }
        }

        return result
    }

}
