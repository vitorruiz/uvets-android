package br.com.uvets.uvetsandroid.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.concrete.canarinho.formatador.Formatador
import br.com.concrete.canarinho.validator.Validador
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.sign_up_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpFragment : BaseFragment(), SignUpNavigator {

    private val mViewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(br.com.uvets.uvetsandroid.R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
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
            tiFullName.error = "Campo obrigatório"
            result = false
        } else {
            tiFullName.isErrorEnabled = false
        }

        if (tvDoc.text.isNullOrEmpty()) {
            tiDoc.error = "Campo obrigatório"
            result = false
        } else {
            if (Validador.CPF.ehValido(tvDoc.text.toString())) {
                tiDoc.isErrorEnabled = false
            } else {
                tiDoc.error = "CPF inválido"
                result = false
            }

        }

        if (tvPhone.text.isNullOrEmpty()) {
            tiPhone.error = "Campo obrigatório"
            result = false
        } else {
            tiPhone.isErrorEnabled = false
        }

        if (tvEmail.text.isNullOrEmpty()) {
            tiEmail.error = "Campo obrigatório"
            result = false
        } else {
            tiEmail.isErrorEnabled = false
        }

        if (tvAddress.text.isNullOrEmpty()) {
            tiAddress.error = "Campo obrigatório"
            result = false
        } else {
            tiAddress.isErrorEnabled = false
        }

        if (tvPassword.text.isNullOrEmpty()) {
            tiPassword.error = "Campo obrigatório"
            result = false
        } else {
            tiPassword.isErrorEnabled = false
        }

        if (tvConfirmPassword.text.isNullOrEmpty()) {
            tiConfirmPassword.error = "Campo obrigatório"
            result = false
        } else {
            tiConfirmPassword.isErrorEnabled = false
        }

        if (!tvPassword.text.isNullOrEmpty() && !tvConfirmPassword.text.isNullOrEmpty()) {
            if (tvPassword.text.toString() != tvConfirmPassword.text.toString()) {
                tiPassword.error = "As senhas devem ser iguais"
                tiConfirmPassword.error = "As senhas devem ser iguais"
                result = false
            } else {
                tiPassword.isErrorEnabled = false
                tiConfirmPassword.isErrorEnabled = false
            }
        }

        return result
    }

    override fun goToLogin() {
        val returnIntent = Intent().apply {
            putExtra("user_email", tvEmail.text.toString())
            putExtra("user_pwd", tvPassword.text.toString())
        }
        activity?.setResult(Activity.RESULT_OK, returnIntent)
        activity?.finish()
    }

}
