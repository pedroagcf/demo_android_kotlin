package com.ufc.appdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.ufc.appdemo.adapter.CartAdapter
import com.ufc.appdemo.databinding.ActivityMainBinding
import com.ufc.appdemo.model.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailsFragment : Fragment() {

    private var rootView: View? = null
    private lateinit var product: Product
    private lateinit var firestore: FirebaseFirestore

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product): ProductDetailsFragment =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT, product)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        rootView = view
        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT)!!
            println("PRODUCT" + product.name)

            updateUI()
        }

        rootView?.findViewById<Button>(R.id.add)?.setOnClickListener{

            val productData = hashMapOf(
                "name" to product.name,
                "description" to product.description,
                "price" to product.price
            )

            firestore.collection("cart")
                .add(productData)
                .addOnSuccessListener {
                    println("SUCCESSSS!!!!!")
                }
                .addOnFailureListener { e ->
                    println("FAAAAAAAAIL!!!")
                    // Handle any errors that occur during register creation
                }
//            navigateToCart()
        }

    }

//    private fun navigateToCart() {
//        val cartFragment = Cart.newInstance(product)
//        val fragmentManager = requireActivity().supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//
//        binding.bottomNavigationView.selectedItemId = R.id.cart
//
//        // Replace the current fragment with the product details fragment
//        transaction.replace(R.id.frame_layout, cartFragment)
//
//        // Add the transaction to the back stack (optional)
//        transaction.addToBackStack(null)
//
//        // Commit the transaction
//        transaction.commit()
//    }


    private fun updateUI() {
        rootView?.let{
            it.findViewById<TextView>(R.id.product_name_text).text = product.name
            it.findViewById<TextView>(R.id.product_description_text).text = product.description
            it.findViewById<TextView>(R.id.product_price_text).text = product.price
        }
    }

}