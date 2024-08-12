package com.example.favoriteplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailsFragment : Fragment() {
    private var places: FavoritePlaces? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        view.isClickable = true
        view.isFocusable = true
        places = arguments?.getParcelable("places") as? FavoritePlaces
        if (places != null) {

            val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
            nameTextView.text = places?.name

            val placeImageView = view.findViewById<ImageView>(R.id.placeImageView)
            places?.imageResourceId?.let { placeImageView.setImageResource(it) }

            val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
            descriptionTextView.text = places?.description

            val adressTextView = view.findViewById<TextView>(R.id.adressTextView)
            adressTextView.text = places?.adress

            val openingTextView = view.findViewById<TextView>(R.id.openingTextView)
            openingTextView.text = places?.openingsHours

            val backButton = view.findViewById<Button>(R.id.backButton)
            backButton.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        } else {
            Log.e("DetailsFragment", " Inga data tillgängliga för visning ")

        }

        return view
    }

    companion object {
        fun newInstance(favoritePlaces: FavoritePlaces): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("places", favoritePlaces)
            fragment.arguments = args
            return fragment
        }
    }

}


