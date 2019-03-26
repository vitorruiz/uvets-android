package br.com.uvets.uvetsandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.petlist.PetListFragment
import br.com.uvets.uvetsandroid.ui.profile.ProfileFragment
import br.com.uvets.uvetsandroid.ui.vetlist.VetListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragNavController: FragNavController

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_pets -> {
                fragNavController.switchTab(FragNavController.TAB1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_vets -> {
                fragNavController.switchTab(FragNavController.TAB2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
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

        val builder = FragNavController.newBuilder(
            savedInstanceState, supportFragmentManager,
            R.id.main_container
        )
        builder.rootFragments(listOf(PetListFragment(), VetListFragment(), ProfileFragment()))
        fragNavController = builder.build()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }
}
