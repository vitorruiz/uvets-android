package br.com.uvets.uvetsandroid

import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions

fun FragNavController.pushFragmentWithAnim(fragment: Fragment?) {
    pushFragment(
        fragment, FragNavTransactionOptions.newBuilder()
            .withAnimation(
                enterAnimation = android.R.anim.fade_in,
                exitAnimation = android.R.anim.fade_out
            ).build()
    )
}

fun FragNavController.popFragmentWithAnim() {
    popFragment(
        FragNavTransactionOptions.newBuilder()
            .withAnimation(
                popEnterAnimation = android.R.anim.fade_in,
                popExitAnimation = android.R.anim.fade_out
            ).build()
    )
}

fun FragNavTransactionOptions.Builder.withAnimation(
    enterAnimation: Int = 0,
    exitAnimation: Int = 0,
    popEnterAnimation: Int = 0,
    popExitAnimation: Int = 0
): FragNavTransactionOptions.Builder {
    return customAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
}