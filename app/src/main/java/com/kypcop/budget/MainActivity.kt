package com.kypcop.budget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Tools.initPref(this)
        setContentView(R.layout.activity_main)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment).navController
        bottomNavView.setupWithNavController(navController)

    }

    fun changeFragment(frag: Fragment, saveInBackStack: Boolean, animate: Boolean) {
        val backStateName = (frag as Any).javaClass.name
        try {
            val manager: FragmentManager = supportFragmentManager
            val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
            if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
                //fragment not in back stack, create it.
                val transaction: FragmentTransaction = manager.beginTransaction()
                if (animate) {
                    Log.d(TAG, "Change Fragment: animate")
                    //transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                }
                transaction.replace(R.id.wrapper_frame_layout, frag, backStateName)
                if (saveInBackStack) {
                    Log.d(TAG, "Change Fragment: addToBackTack $backStateName")
                    transaction.addToBackStack(backStateName)
                } else {
                    Log.d(TAG, "Change Fragment: NO addToBackTack")
                }
                transaction.commit()
            } else {
                // custom effect if fragment is already instanciated
            }
        } catch (exception: IllegalStateException) {
            Log.w(
                TAG,
                "Unable to commit fragment, could be activity as been killed in background. $exception"
            )
        }
    }
}