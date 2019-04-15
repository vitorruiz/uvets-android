package br.com.uvets.uvetsandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.ui.createpet.CreatePetFragment
import br.com.uvets.uvetsandroid.ui.login.LoginFragment
import br.com.uvets.uvetsandroid.ui.signup.SignUpFragment
import br.com.uvets.uvetsandroid.ui.splash.SplashFragment

enum class ContainerView {
    SIGN_UP, CREATE_PET, UPDATE_PET, LOGIN
}

class ContainerActivity : AppCompatActivity() {

    companion object {

        const val VIEW_ID_PARAM = "view_id"
        const val VIEW_NO_ACTION_BAR = "view_no_action_bar"
        const val PET_PARAM = "pet"

        private fun basicContainerIntent(context: Context): Intent {
            return Intent(context, ContainerActivity::class.java)
        }

        fun createPetView(context: Context): Intent {
            return basicContainerIntent(context).apply {
                putExtra(
                    VIEW_ID_PARAM,
                    ContainerView.CREATE_PET
                )
            }
        }

        fun updatePetView(context: Context, pet: Pet): Intent {
            return basicContainerIntent(context).apply {
                putExtra(VIEW_ID_PARAM, ContainerView.UPDATE_PET)
                putExtra(PET_PARAM, pet)
            }
        }

        fun signUpView(context: Context): Intent {
            return basicContainerIntent(context).apply {
                putExtra(
                    VIEW_ID_PARAM,
                    ContainerView.SIGN_UP
                )
                putExtra(VIEW_NO_ACTION_BAR, true)
            }
        }

        fun loginView(context: Context): Intent {
            return basicContainerIntent(context).apply {
                putExtra(
                    VIEW_ID_PARAM,
                    ContainerView.LOGIN
                )
                putExtra(VIEW_NO_ACTION_BAR, true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        (intent?.extras?.getSerializable(VIEW_ID_PARAM) as ContainerView?).apply {
            var fragmentToLoad: androidx.fragment.app.Fragment = LoginFragment()
            when (this) {
                ContainerView.SIGN_UP -> fragmentToLoad = SignUpFragment()
                ContainerView.CREATE_PET -> fragmentToLoad = CreatePetFragment()
                ContainerView.UPDATE_PET -> fragmentToLoad =
                    CreatePetFragment.updateInstance(intent!!.extras!!.getParcelable(PET_PARAM)!!)
                ContainerView.LOGIN -> fragmentToLoad = LoginFragment()
                null -> {
                    fragmentToLoad = SplashFragment()
                }
            }

            loadFragment(fragmentToLoad)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commitNow()
    }
}