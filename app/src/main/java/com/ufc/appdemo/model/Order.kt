package com.ufc.appdemo.model

import android.os.Parcel
import android.os.Parcelable

class Order() : Parcelable {
    lateinit var id: String
    lateinit var name: String
    lateinit var price: String
    lateinit var description: String
    lateinit var cartItems: List<Product> // List of products


    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        name = parcel.readString().toString()
        price = parcel.readString().toString()
        description = parcel.readString().toString()
        cartItems = parcel.createTypedArrayList(Product.CREATOR) ?: emptyList() // Read the list of products from the parcel

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(description)
        parcel.writeTypedList(cartItems) // Write the list of products to the parcel

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}