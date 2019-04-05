package br.com.uvets.uvetsandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.popFragmentWithAnim
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import br.com.uvets.uvetsandroid.ui.petlist.PetListFragment
import br.com.uvets.uvetsandroid.ui.profile.ProfileFragment
import br.com.uvets.uvetsandroid.ui.vetlist.VetListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fragNavController = FragNavController(supportFragmentManager, R.id.main_container)

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

    private val mOnNavigationItemReselectedListener =
        BottomNavigationView.OnNavigationItemReselectedListener { fragNavController.clearStack() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener)

        fragNavController.rootFragments = listOf(PetListFragment(), VetListFragment(), ProfileFragment())
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        fragNavController.popFragmentWithAnim()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val currentFragment = fragNavController.currentFrag as? BaseFragment
        currentFragment?.onBackPressed()

        if (fragNavController.isRootFragment) {
            super.onBackPressed()
        } else {
            fragNavController.popFragmentWithAnim()
        }
    }
}
