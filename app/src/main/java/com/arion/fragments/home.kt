package com.arion.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.arion.my_adapters.ProductsAdapter
import com.arion.my_models.ProductsModel
import com.arion.nandi.R
import com.arion.server_calls.ServerCalls
import org.json.JSONObject


class home : Fragment() {

    // For Interface
    internal interface HomeFragmentToActivity {
        fun FragmentRequest(productId: String, fragmentName: String)
    }

    private var handler: HomeFragmentToActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentToActivity) {
            handler = context
        }
    }


    // For products rec view..
    lateinit var productsRecView: RecyclerView
    lateinit var productsList: ArrayList<ProductsModel>
    lateinit var productsAdapter: ProductsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // For product rec view initialize..
        productsList = arrayListOf()
        productsRecView = view.findViewById(R.id.productsRecView)
        productsRecView.layoutManager = LinearLayoutManager(requireContext())

        productsAdapter = ProductsAdapter(
            requireContext(),
            productsList,
            object : ProductsAdapter.ProductClickListener {
                override fun onProductClick(productId: String, fragmentName: String) {
                    handler?.FragmentRequest(productId, fragmentName)
                }

                override fun dataChange() {
                }
            })

        productsRecView.adapter = productsAdapter
        fetchProducts()

        return view
    }

    fun fetchProducts() {

        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, ServerCalls().PRODUCTS_URL,
            Response.Listener { response ->
                if (response != null) {
                    try {
                        val products = JSONObject(response).getJSONArray("results")

                        for (i in 0..products.length() - 1) {
                            val details = products.getJSONObject(i)
                            val book = ProductsModel(
                                ServerCalls().BASE_URL+ details.getJSONArray("images").getJSONObject(0).getString("image"),
                                details.getString("title"),
                                details.getString("description"),
                                details.getJSONObject("size").getString("size")+"MM",
                                "Medium",
                                "A1",
                                "",
                                details.getInt("id").toString()
                            )
                            productsList.add(book)
                            productsAdapter.notifyDataSetChanged()

                        }
//                        Log.e("Your Array Response", products.toString())
                    } catch (e: Exception) {
                        Log.e("Check", "fetchProducts: " + e.message)
                    }
                } else {
                    Log.e("Your Array Response", "Data Null")
                }
            },
            Response.ErrorListener { error ->
                Log.e("error is ", "" + error + error.message)
            }) {

            //This is for Headers If You Needed
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] =
                    "application/json; charset=UTF-8" //application/x-www-form-urlencoded
                params["Authorization"] = "Bearer ${ServerCalls().MY_TOCKEN}"
                return params
            }

            //Pass Your Parameters here
//            override fun getParams(): Map<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                params["access"] = ServerCalls().MY_TOCKEN
////                params["Pass"] = PassWord
//                return params
//            }
        }
        stringRequest.setRetryPolicy(
            DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        // Add the request to the RequestQueue.
        stringRequest.setShouldCache(false)
        queue.add(stringRequest)

    }


}