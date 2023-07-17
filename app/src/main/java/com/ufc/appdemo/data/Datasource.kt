package com.ufc.appdemo.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.appdemo.adapter.CartAdapter
import com.ufc.appdemo.adapter.OrderAdapter
import com.ufc.appdemo.adapter.ProductAdapter
import com.ufc.appdemo.model.Order
import com.ufc.appdemo.model.Product

class Datasource {
    var products: MutableList<Product> = mutableListOf()
    var orders: MutableList<Order> = mutableListOf()
    lateinit var productAdapter: ProductAdapter
    lateinit var cartAdapter: CartAdapter
    lateinit var orderAdapter: OrderAdapter
    private var db = Firebase.firestore;

    fun getCatalog() {
        db.collection("catalog")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var product = document.toObject(Product::class.java)
                    products.add(product)
                    Log.d("Product:","$product")
                }
                productAdapter.notifyDataSetChanged()
            }
    }

    fun getCart() {
        db.collection("cart")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var product = document.toObject(Product::class.java)
                    products.add(product)
                    Log.d("Product:","$product")
                }
                cartAdapter.notifyDataSetChanged()
            }
    }

    fun getOrder() {
        db.collection("order")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var order = document.toObject(Order::class.java)
                    orders.add(order)
                    Log.d("Product:","$order")
                }
                orderAdapter.notifyDataSetChanged()
            }

    }

    fun saveCart(context: Context) {
        val cartProducts = products.map {product ->
            hashMapOf(
                "name" to product.name,
                "description" to product.description,
                "price" to product.price
            )
        }

        val cart = hashMapOf(
            "uid" to FirebaseAuth.getInstance().currentUser?.uid,
            "cartItems" to cartProducts,
        )
        db.collection("order")
            .add(cart)
            .addOnSuccessListener {
                Toast.makeText(context, "Your order has been created", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                println("FAAAAAAAAIL!!!")
                // Handle any errors that occur during register creation
            }
    }

    fun deleteCart(context: Context) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Get the cart document from Firestore
        val docRef = uid?.let { db.collection("cart").document(it) }

        // Delete the cart document
        docRef?.delete()
            ?.addOnSuccessListener {
                Toast.makeText(context, "Cart deleted", Toast.LENGTH_SHORT).show()
            }
            ?.addOnFailureListener { e ->
                println("FAAAAAAAAIL!!!")
                // Handle any errors that occur during cart deletion
            }
    }

}