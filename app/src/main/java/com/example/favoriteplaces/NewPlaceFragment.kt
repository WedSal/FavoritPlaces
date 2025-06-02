package com.example.favoriteplaces

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class NewPlaceFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var adressEditText: EditText
    private lateinit var openingHoursEditText: EditText
    private lateinit var selectedImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var saveButton: Button

    private var imageUri: Uri? = null
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    companion object {
        private const val IMAGE_PICK_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_place, container, false)

        nameEditText = view.findViewById(R.id.nameEditText)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        adressEditText = view.findViewById(R.id.adressEditText)
        openingHoursEditText = view.findViewById(R.id.openingHoursEditText)
        selectedImageView = view.findViewById(R.id.selectedImageView)
        selectImageButton = view.findViewById(R.id.selectImageButton)
        saveButton = view.findViewById(R.id.saveButton)

        selectImageButton.setOnClickListener {
            pickImageFromGallery()
        }

        saveButton.setOnClickListener {
            uploadImageAndSavePlace()
        }

        return view
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageUri?.let {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                selectedImageView.setImageBitmap(bitmap)
            }
        }
    }

    private fun uploadImageAndSavePlace() {
        val name = nameEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val adress = adressEditText.text.toString().trim()
        val openingHours = openingHoursEditText.text.toString().trim()

        if (name.isBlank() || description.isBlank() || adress.isBlank() || openingHours.isBlank() || imageUri == null) {
            Toast.makeText(requireContext(), "Fyll i alla fält och välj en bild", Toast.LENGTH_SHORT).show()
            return
        }

        val fileName = UUID.randomUUID().toString()
        val imageRef = storage.reference.child("places_images/$fileName")

        imageUri?.let { uri ->
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        val place = hashMapOf(
                            "name" to name,
                            "description" to description,
                            "adress" to adress,
                            "openingsHours" to openingHours,
                            "imageUrl" to downloadUrl.toString()
                        )

                        firestore.collection("Places")
                            .add(place)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Platsen är sparad!", Toast.LENGTH_SHORT).show()


                                requireActivity().supportFragmentManager.popBackStack()
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), "Kunde inte spara platsen", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Misslyckades att ladda upp bilden", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
