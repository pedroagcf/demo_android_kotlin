package com.ufc.appdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufc.appdemo.Home
import com.ufc.appdemo.R
import com.ufc.appdemo.data.Datasource
import com.ufc.appdemo.model.Product

class ProductAdapter(private val context: Home, private val dataset: Datasource,
                     private val clickListener: (Product) -> Unit
)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View, clickAtPosition: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        val productTitle: TextView = itemView.findViewById(R.id.product_title)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val viewHolder = ProductViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)) {
                clickListener(dataset.products[it])
            }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = dataset.products[position]
        holder.productTitle.text = product.name
        holder.productPrice.text = product.price
    }

    override fun getItemCount(): Int {
        return dataset.products.size
    }
}