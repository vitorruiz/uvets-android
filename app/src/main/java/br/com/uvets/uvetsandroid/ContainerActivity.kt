package br.com.uvets.uvetsandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.uvets.uvetsandroid.ui.createpet.CreatePetFragment

class ContainerActivity : AppCompatActivity() {

    companion object {

        const val VIEW_ID_PARAM = "view_id"

        fun createPetView(context: Context) : Intent{
            val intent = Intent(context, ContainerActivity::class.java)
            intent.putExtra(VIEW_ID_PARAM, 0)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        val viewId = intent?.extras?.getInt(VIEW_ID_PARAM) ?: -1

        when (viewId) {
            0 -> loadFragment(CreatePetFragment())
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commitNow()
    }
}