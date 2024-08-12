package com.example.favoriteplaces

object DataManager {
    val places = mutableListOf<FavoritePlaces>()

    init {
        createMockData()

    }

    fun createMockData() {
        places.add(FavoritePlaces("Book Store","Many alternativs of books","Bookstreet 1 ", "10-18", R.drawable.bookstore))
        places.add(FavoritePlaces("Fika Store","Many alternativs of fikas","Fikastreet 2 ", "10-18", R.drawable.fikastore ))
        places.add(FavoritePlaces("Shoes Store","Many alternativs of shoes","Shoestreet 3 ", "10-18", R.drawable.shoestore ))
        places.add(FavoritePlaces("Bowling Place ","Many alternativs of games","Gamestreet 4 ", "10-18", R.drawable.bowllingstore ))
        places.add(FavoritePlaces("Ice Cream Store","Many alternativs of ice cream","Icestreet 5", "10-18", R.drawable.icecreamstore ))
        places.add(FavoritePlaces("Toys Store","Many alternativs of toys","Toysstreet 6 ", "10-18", R.drawable.toysstore ))
        places.add(FavoritePlaces("Super Market","Many alternativs of food","Marketstreet 7 ", "10-18", R.drawable.supermarket ))
        places.add(FavoritePlaces("Clothes Store","Many alternativs of clothes","Clothesstreet 8 ", "10-18", R.drawable.clothesstore ))
        places.add(FavoritePlaces("Flower Store","Many alternativs of flowers","Flowerstreet 9 ", "10-18", R.drawable.flowersstore ))
        places.add(FavoritePlaces("Restaurant ","Many alternativs of food","Foodstreet 10 ", "10-18", R.drawable.resturang))
    }
}