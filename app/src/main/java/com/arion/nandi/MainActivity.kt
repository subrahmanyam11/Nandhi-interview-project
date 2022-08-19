package com.arion.nandi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arion.fragments.home
import com.arion.fragments.product_details
import com.arion.nandi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), home.HomeFragmentToActivity {

    lateinit var fragmentManager: FragmentManager
    lateinit var binding: ActivityMainBinding

    var allowBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, home(), "home").commit()

    }

    override fun onBackPressed() {

        if (isTaskRoot && fragmentManager.backStackEntryCount.equals(0)) {
            if (!allowBack) {
                allowBack = true
                Toast.makeText(this@MainActivity, "Press Again to Exist", Toast.LENGTH_SHORT).show()
            } else super.onBackPressed()
        } else super.onBackPressed()
        val timer = object : CountDownTimer(2000, 2000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                allowBack = false
            }
        }
        timer.start()
    }

    override fun FragmentRequest(productId: String, fragmentName: String) {
        val fragment = product_details()
        val args = Bundle()
        args.putString("productId", productId)
        fragment.setArguments(args)
        replaceFragment(product_details(), fragmentName)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment, tag).addToBackStack(tag)
        fragmentTransaction.commit()

    }
}