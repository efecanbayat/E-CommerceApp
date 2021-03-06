package com.efecanbayat.e_commerceapp.ui

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.efecanbayat.e_commerceapp.R
import com.efecanbayat.e_commerceapp.base.BaseFragment
import com.efecanbayat.e_commerceapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSplash()
    }

    private fun initSplash() {
        binding.splashAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
                Log.v("Animation", "Started")
            }

            override fun onAnimationEnd(p0: Animator?) {
                findNavController().navigate(R.id.action_splashFragment_to_productListFragment)
            }

            override fun onAnimationCancel(p0: Animator?) {
                Log.v("Animation", "Canceled")
            }

            override fun onAnimationRepeat(p0: Animator?) {
                Log.v("Animation", "Repeated")
            }

        })
    }
}