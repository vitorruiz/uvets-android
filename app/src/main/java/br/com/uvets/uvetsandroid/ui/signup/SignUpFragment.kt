package br.com.uvets.uvetsandroid.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import br.com.concrete.canarinho.formatador.Formatador
import br.com.concrete.canarinho.validator.Validador
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.sign_up_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpFragment : BaseFragment(), SignUpNavigator {

    private val mViewModel: SignUpViewModel by viewModel()

    override fun getLayoutResource(): Int {
        return R.layout.sign_up_fragment
    }

    override fun initComponents(rootView: View, savedInstanceState: Bundle?) {
        mViewModel.attachNavigator(this)

        setUpView()
    }

    private fun setUpView() {
        tvPassword.transformationMethod = PasswordTransformationMethod()
        tvConfirmPassword.transformationMethod = PasswordTransformationMethod()

        tvDoc.addTextChangedListener(
            MascaraNumericaTextWatcher("###.###.###-##")
        )

        tvPhone.addTextChangedListener(
            MascaraNumericaTextWatcher("(##) #####-####")
        )

        btSignUp.setOnClickListener {
            if (isFormValid()) {
                mViewModel.register(
                    tvFullName.text.toString(),
                    Formatador.CPF.desformata(tvDoc.text.toString()),
                    Formatador.TELEFONE.desformata(tvPhone.text.toString()),
                    tvEmail.text.toString(),
                    tvAddress.text.toString(),
                    tvPassword.text.toString()
                )
            }
        }
    }

    private fun isFormValid(): Boolean {
        var result = true

        if (tvFullName.text.isNullOrEmpty()) {
            tiFullName.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiFullName.isErrorEnabled = false
        }

        if (tvDoc.text.isNullOrEmpty()) {
            tiDoc.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            if (Validador.CPF.ehValido(tvDoc.text.toString())) {
                tiDoc.isErrorEnabled = false
            } else {
                tiDoc.error = getString(R.string.error_invalid_cpf)
                result = false
            }

        }

        if (tvPhone.text.isNullOrEmpty()) {
            tiPhone.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiPhone.isErrorEnabled = false
        }

        if (tvEmail.text.isNullOrEmpty()) {
            tiEmail.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiEmail.isErrorEnabled = false
        }

        if (tvAddress.text.isNullOrEmpty()) {
            tiAddress.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiAddress.isErrorEnabled = false
        }

        if (tvPassword.text.isNullOrEmpty()) {
            tiPassword.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiPassword.isErrorEnabled = false
        }

        if (tvConfirmPassword.text.isNullOrEmpty()) {
            tiConfirmPassword.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiConfirmPassword.isErrorEnabled = false
        }

        if (!tvPassword.text.isNullOrEmpty() && !tvConfirmPassword.text.isNullOrEmpty()) {
            if (tvPassword.text.toString() != tvConfirmPassword.text.toString()) {
                tiPassword.error = getString(R.string.error_password_dont_match)
                tiConfirmPassword.error = getString(R.string.error_password_dont_match)
                result = false
            } else {
                tiPassword.isErrorEnabled = false
                tiConfirmPassword.isErrorEnabled = false
            }
        }

        return result
    }

    override fun onSignUpSuccess() {
        val returnIntent = Intent().apply {
            putExtra("user_email", tvEmail.text.toString())
            putExtra("user_pwd", tvPassword.text.toString())
        }
        activity?.setResult(Activity.RESULT_OK, returnIntent)
        activity?.finish()
    }

}
