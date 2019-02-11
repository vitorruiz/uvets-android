package br.com.uvets.uvetsandroid

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.uvets.uvetsandroid.ui.petlist.PetListFragment
import br.com.uvets.uvetsandroid.ui.profile.ProfileFragment
import br.com.uvets.uvetsandroid.ui.vetlist.VetListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_pets -> {
                //message.setText(R.string.title_home)
                fab_create_pet.show()
                loadFragment(PetListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_vets -> {
                //message.setText(R.string.title_dashboard)
                fab_create_pet.hide()
                loadFragment(VetListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                //message.setText(R.string.title_notifications)
                fab_create_pet.hide()
                loadFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadFragment(PetListFragment())

        fab_create_pet.setOnClickListener {
            startActivity(ContainerActivity.createPetView(this))
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}
