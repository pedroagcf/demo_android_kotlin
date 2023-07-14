package com.ufc.appdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterProduct.newInstance] factory method to
 * create an instance of this fragment.
 */

class RegisterProduct : Fragment() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rootView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_register_product, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()

        view?.findViewById<Button>(R.id.registerButton)?.setOnClickListener{
            val name = view?.findViewById<EditText>(R.id.nameText)?.text.toString()
            val description = view?.findViewById<EditText>(R.id.descriptionText)?.text.toString()
            val price = view?.findViewById<EditText>(R.id.priceText)?.text.toString()

            val registerData = hashMapOf(
                    "name" to name,
                    "description" to description,
                    "price" to price
                )

            firestore.collection("catalog")
                .add(registerData)
                .addOnSuccessListener {
                    // Register created successfully
//                        clearFields()
//                    navigateToHome()
                    // Show success message or perform other actions
                    println("SUCCESSSS!!!!!")
                }
                .addOnFailureListener { e ->
                    println("FAAAAAAAAIL!!!")
                    // Handle any errors that occur during register creation
                }


        }
    }

//    fun navigateToHome() {
//        val fragmentManager: FragmentManager = childFragmentManager
//        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.frame_layout, Home())
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

}