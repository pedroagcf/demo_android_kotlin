package com.ufc.appdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufc.appdemo.Cart
import com.ufc.appdemo.R
import com.ufc.appdemo.data.Datasource
import com.ufc.appdemo.model.Product

class CartAdapter (private val context: Cart, private val dataset: Datasource) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = dataset.products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return dataset.products.size
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productTitle: TextView = itemView.findViewById(R.id.product_title)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productDescription: TextView = itemView.findViewById(R.id.product_description)

        fun bind(product: Product) {
            productTitle.text = product.name
            productDescription.text = product.description
            productPrice.text = product.price
        }
    }

}