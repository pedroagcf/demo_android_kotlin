package com.ufc.appdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufc.appdemo.OrderFragment
import com.ufc.appdemo.R
import com.ufc.appdemo.data.Datasource
import com.ufc.appdemo.model.Order
import com.ufc.appdemo.model.Product

class OrderAdapter (private val context: OrderFragment, private val dataset: Datasource) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = dataset.orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return dataset.orders.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderTitle: TextView = itemView.findViewById(R.id.order_title)
        val orderPrice: TextView = itemView.findViewById(R.id.order_price)
        val orderDescription: TextView = itemView.findViewById(R.id.order_description)

        fun bind(order: Order) {
            val cartItems = order.cartItems
            val orderDesc = concatenateProductNames(cartItems)
            val totalPrice = calculateTotalPrice(cartItems)

            orderTitle.text = "Order"
            orderDescription.text = orderDesc
            orderPrice.text = totalPrice
        }

        private fun concatenateProductNames(products: List<Product>): String {
            val names = products.joinToString(", ") { it.name }
            return names
        }
        private fun calculateTotalPrice(products: List<Product>): String {
            var totalPrice = 0.0

            for (product in products) {
                val price = product.price.toDoubleOrNull()
                if (price != null) {
                    totalPrice += price
                }
            }

            return totalPrice.toString()
        }

    }


    

}