package com.example.favoriteplaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent

class MainActivity : AppCompatActivity(), OnPlaceClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter(DataManager.places, this)
        recyclerView.adapter = favoriteAdapter

        val addButton: FloatingActionButton = findViewById(R.id.addButton)
        addButton.setOnClickListener{
            openLoginActivity()
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

  //  private fun openLoginFragment(){
    //    val transaction = supportFragmentManager.beginTransaction()
   //     transaction.replace(R.id.fragmentContainer, LoginFragment())
   //     transaction.addToBackStack(null)
   //     transaction.commit()
  //  }
    override fun onPlaceClick(position: Int) {
       val selectedPlace = DataManager.places[position]
        val fragment = DetailsFragment.newInstance(selectedPlace)
         supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer,fragment)
        .addToBackStack("detailFragment")
        .commit()


    }

}

