package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.data.model.Info
import com.cevdetkilickeser.neyicez.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore
    private lateinit var galleryPermission: String
    private lateinit var kullanici_adi: String
    private lateinit var info: Info
    private var choosenImage : Uri? = null
    private var choosenBitmap : Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        database.collection("NeYicezProfil").document(kullanici_adi).get().addOnSuccessListener {
            if (it.exists()){
                info = it.toObject(Info::class.java)!!
                Glide.with(requireContext()).load(info.image).into(binding.ivProfilePhoto)
                binding.textName.setText(info.name)
                binding.textPhone.setText(info.phone)
            }else{
                info = Info("","","")
            }
        }.addOnFailureListener {
            Log.e("şş",it.localizedMessage)
        }

        binding.buttonUpdate.setOnClickListener {
            val name = binding.textName.text.toString()
            val phone = binding.textPhone.text.toString()
            onClickButtonUpdate(name,phone)
        }

        binding.ivProfilePhoto.setOnClickListener {
            onClickImage()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            Manifest.permission.READ_MEDIA_IMAGES
        }else{
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()

        kullanici_adi = auth.currentUser!!.email.toString()
    }

    fun onClickButtonUpdate(name:String, phone:String){
        val referance = storage.reference
        val imageReferance = referance.child("ProfilNeYicez").child(kullanici_adi)

        if (choosenImage != null){
            imageReferance.putFile(choosenImage!!).addOnSuccessListener { taskSnapshot ->
                val loadedImageReferance = storage.reference.child("ProfilNeYicez").child(kullanici_adi)
                loadedImageReferance.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()

                    info = Info(downloadUrl,name,phone)

                    updateDatabase()

                }.addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            info.name = name
            info.phone = phone
            updateDatabase()
        }
    }

    fun onClickImage(){
        if (ContextCompat.checkSelfPermission(requireContext(), galleryPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(galleryPermission),1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,2)
        }
    }

    fun updateDatabase(){
        database.collection("NeYicezProfil").document(kullanici_adi).set(info).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.e("fatal","database updated")
            }
        }.addOnFailureListener { exception ->
            Log.e("fatal","database updated")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            choosenImage = data.data
            if (choosenImage != null){
                val source = ImageDecoder.createSource(requireActivity().contentResolver,choosenImage!!)
                choosenBitmap = ImageDecoder.decodeBitmap(source)
                binding.ivProfilePhoto.setImageBitmap(choosenBitmap)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}