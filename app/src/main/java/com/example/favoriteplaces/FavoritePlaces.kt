package com.example.favoriteplaces

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class FavoritePlaces (
    var name: String,
    var description: String,
    var adress: String,
    var openingsHours: String,
    var imageResourceId: Int,


    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(adress)
        parcel.writeString(openingsHours)
        parcel.writeInt(imageResourceId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoritePlaces> {
        override fun createFromParcel(parcel: Parcel): FavoritePlaces {
            return FavoritePlaces(parcel)
        }

        override fun newArray(size: Int): Array<FavoritePlaces?> {
            return arrayOfNulls(size)
        }
    }

}