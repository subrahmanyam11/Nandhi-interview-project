package com.arion.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.arion.nandi.R
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class product_details : Fragment() {

    var countQuantity = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        // For Click Anim..
        val clickAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.click_anim)

        // Getting product Id..
        val args = arguments
        val id = args?.getString("productId")
        Log.e("Check", "onCreateView: $id", )

        // For counter Quantity..
        val countQuantityText: TextView = view.findViewById(R.id.countQuantityProductDetails)
        view.findViewById<TextView>(R.id.minusQuantityProductDetails).setOnClickListener {
            if (countQuantity>1){
                countQuantity -=1
                countQuantityText.text = countQuantity.toString()
            }
        }
        view.findViewById<TextView>(R.id.plusQuantityProductDetails).setOnClickListener {
            countQuantity +=1
            countQuantityText.text = countQuantity.toString()
        }

        // For Add to Cart..
        val addCart = view.findViewById<LinearLayout>(R.id.addCartLLProductDetails)
        addCart.setOnClickListener {
            addCart.startAnimation(clickAnim)
        }

        // For Wishlist..
        val wishlist = view.findViewById<LinearLayout>(R.id.wishListLLProductDetails)
        wishlist.setOnClickListener {
            wishlist.startAnimation(clickAnim)
        }

        // For share..
        val share = view.findViewById<LinearLayout>(R.id.shareLLProductDetails)
        share.setOnClickListener {
            share.startAnimation(clickAnim)
        }

        // For slider images..
        fetchSliderImages(id, view)


        return  view
    }

    private fun fetchSliderImages(id: String?, view: View) {

        val sliderImages = view.findViewById<ImageSlider>(R.id.sliderImagesProductDetails)
        val imgList = ArrayList<SlideModel>()

        for (links in 0..3){
            imgList.add(SlideModel(R.drawable.img_product))
        }
        sliderImages.setImageList(imgList, ScaleTypes.FIT)
    }

}