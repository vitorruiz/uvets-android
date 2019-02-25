package br.com.uvets.uvetsandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.uvets.uvetsandroid.ui.createpet.CreatePetFragment
import br.com.uvets.uvetsandroid.ui.login.LoginFragment
import br.com.uvets.uvetsandroid.ui.signup.SignUpFragment

enum class ContainerView {
    SIGN_UP, CREATE_PET, LOGIN
}

class ContainerActivity : AppCompatActivity() {

    companion object {

        const val VIEW_ID_PARAM = "view_id"
        const val VIEW_NO_ACTION_BAR = "view_no_action_bar"

        private fun basicContainerIntent(context: Context): Intent {
            return Intent(context, ContainerActivity::class.java)
        }

        fun createPetView(context: Context): Intent {
            return basicContainerIntent(context).apply {
                putExtra(VIEW_ID_PARAM, ContainerView.CREATE_PET)
            }
        }

        fun createSignUpView(context: Context): Intent {
            return basicContainerIntent(context).apply {
                putExtra(VIEW_ID_PARAM, ContainerView.SIGN_UP)
                //putExtra(VIEW_NO_ACTION_BAR, true)
            }
        }

        fun createLoginView(context: Context): Intent {
            return basicContainerIntent(context).apply {
                putExtra(VIEW_ID_PARAM, ContainerView.LOGIN)
                //putExtra(VIEW_NO_ACTION_BAR, true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewNoActionBar = intent?.extras?.getBoolean(VIEW_NO_ACTION_BAR)
        if (viewNoActionBar != null && viewNoActionBar) {
            setTheme(R.style.Theme_UVets_NoActionBar)
        }

        (intent?.extras?.getSerializable(VIEW_ID_PARAM) as ContainerView?).apply {
            var fragmentToLoad: Fragment = LoginFragment()
            when (this) {
                ContainerView.SIGN_UP -> fragmentToLoad = SignUpFragment()
                ContainerView.CREATE_PET -> fragmentToLoad = CreatePetFragment()
                ContainerView.LOGIN -> fragmentToLoad = LoginFragment()
                null -> {
                    setTheme(R.style.Theme_UVets_NoActionBar)
                    fragmentToLoad = LoginFragment()
                }
            }

            setContentView(R.layout.activity_container)
            loadFragment(fragmentToLoad)
        }


    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commitNow()
    }
}