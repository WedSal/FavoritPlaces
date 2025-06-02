package com.example.favoriteplaces

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(), OnPlaceClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val placesList = mutableListOf<FavoritePlaces>()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var fragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        favoriteAdapter = FavoriteAdapter(placesList, this)
        recyclerView.adapter = favoriteAdapter

        fragmentContainer = findViewById(R.id.fragmentContainer)
        fragmentContainer.visibility = View.GONE

        loadPlacesFromFirebase()

        val addButton: FloatingActionButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                fragmentContainer.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, NewPlaceFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                startActivity(android.content.Intent(this, LoginActivity::class.java))
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {

            if (supportFragmentManager.backStackEntryCount == 0) {
                fragmentContainer.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadPlacesFromFirebase()
    }

    private fun loadPlacesFromFirebase() {
        db.collection("Places")
            .get()
            .addOnSuccessListener { documents ->
                placesList.clear()
                for (document in documents) {
                    val place = document.toObject(FirebasePlace::class.java)
                    val converted = FavoritePlaces(
                        name = place.name,
                        description = place.description,
                        adress = place.adress,
                        openingsHours = place.openingsHours,
                        imageUrl = place.imageUrl
                    )
                    placesList.add(converted)
                }
                favoriteAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Kunde inte hämta platser från Firebase", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onPlaceClick(position: Int) {
        val selectedPlace = placesList[position]
        val fragment = DetailsFragment.newInstance(selectedPlace)
        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack("detailFragment")
            .commit()
    }
}
