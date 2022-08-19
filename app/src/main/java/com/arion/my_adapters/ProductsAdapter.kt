package com.arion.my_adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arion.my_models.ProductsModel
import com.arion.nandi.R
import com.squareup.picasso.Picasso


class ProductsAdapter(val context: Context, val list: ArrayList<ProductsModel>, val changeListener: ProductClickListener): RecyclerView.Adapter<ProductsAdapter.CHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false)
        return CHolder(view)
    }

    override fun onBindViewHolder(holder: CHolder, position: Int) {

        val model = list[position]
        val clickAnim = AnimationUtils.loadAnimation(context, R.anim.click_anim)

        try {
            Picasso.get().load(model.url).into(holder.img)
        }catch (e: Exception){
            Log.e("Picasso", "onBindViewHolder: "+e.message )
        }

        holder.title.text = model.title
        holder.description.text = model.description
        holder.length.text = model.length
        holder.warrentry.text = model.warrentry

        holder.itemView.setOnClickListener{
            changeListener.onProductClick(model.id, "products_details")
        }
        holder.wishListLL.setOnClickListener {
            holder.wishListLL.startAnimation(clickAnim)
        }
        holder.addCartLL.setOnClickListener {
            holder.addCartLL.startAnimation(clickAnim)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val img: ImageView = itemView.findViewById(R.id.imgProductCard)
        val title: TextView = itemView.findViewById(R.id.titleProductCard)
        val description: TextView = itemView.findViewById(R.id.descriptionProductCard)
        val length: TextView = itemView.findViewById(R.id.lengthProductCard)
        val warrentry: TextView = itemView.findViewById(R.id.warrentryProductCard)
        val wishListLL: LinearLayout = itemView.findViewById(R.id.wishlistLLProductCard)
        val addCartLL: LinearLayout = itemView.findViewById(R.id.addCartLLProductCard)

    }

    interface ProductClickListener {
        fun onProductClick(productId: String, fragmentName: String)
        fun dataChange()
    }
}