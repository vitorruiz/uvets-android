package br.com.uvets.uvetsandroid

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import br.com.uvets.uvetsandroid.ui.petlist.PetListFragment
import br.com.uvets.uvetsandroid.ui.profile.ProfileFragment
import br.com.uvets.uvetsandroid.ui.vetlist.VetListFragment
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragNavController: FragNavController

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_pets -> {
                fab_create_pet.show()
                fragNavController.switchTab(FragNavController.TAB1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_vets -> {
                fab_create_pet.hide()
                fragNavController.switchTab(FragNavController.TAB2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                fab_create_pet.hide()
                fragNavController.switchTab(FragNavController.TAB3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val builder = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, R.id.container)
        builder.rootFragments(listOf(PetListFragment(), VetListFragment(), ProfileFragment()))
        fragNavController = builder.build()

        fab_create_pet.setOnClickListener {
            startActivity(ContainerActivity.createPetView(this))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            fragNavController.onSaveInstanceState(it)
        }
    }
}
