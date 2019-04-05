package br.com.uvets.uvetsandroid.ui.about

import android.view.View
import br.com.uvets.uvetsandroid.BuildConfig
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.about_fragment.*

class AboutFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.about_fragment
    }

    override fun getTile(): String? {
        return "Sobre"
    }

    override fun initComponents(rootView: View) {
        tvAppVersion.text = "${BuildConfig.VERSION_NAME} ${BuildConfig.ENV_NAME}"
    }
}
