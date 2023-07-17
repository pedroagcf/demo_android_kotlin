package com.ufc.appdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ufc.appdemo.adapter.CartAdapter
import com.ufc.appdemo.adapter.ProductAdapter
import com.ufc.appdemo.data.Datasource
import com.ufc.appdemo.databinding.ActivityMainBinding
import com.ufc.appdemo.model.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass..
 * Use the [Cart.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cart : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var cartView: View? = null
    var cartDatasource = Datasource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartDatasource.getCart()

        val rootView = inflater.inflate(R.layout.fragment_cart, container, false)
        recyclerView = rootView.findViewById(R.id.product_list_rv)

        val adapter = CartAdapter(this, cartDatasource)

        recyclerView.adapter = adapter
        cartDatasource.cartAdapter = adapter

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartView = view

        cartView?.findViewById<Button>(R.id.finish_order_btn)?.setOnClickListener {
            cartDatasource.saveCart(context!!)
        }
    }


}

