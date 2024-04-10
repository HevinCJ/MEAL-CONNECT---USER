package com.example.mealconnectuser.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mealconnectuser.R
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.activity.StartActivity
import com.example.mealconnectuser.databinding.FragmentProfileBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.utils.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Profile : Fragment() {

    private var profile:FragmentProfileBinding?=null
    private val binding get() = profile!!

    private lateinit var auth:FirebaseAuth
    private lateinit var preferences: AppPreferences
    private lateinit var storageref:StorageReference
    private lateinit var id:String
    private lateinit var databaseref:DatabaseReference

    private lateinit var imageuri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseref=FirebaseDatabase.getInstance().getReference("Users")
        auth= FirebaseAuth.getInstance()
        setUserName()
        preferences = AppPreferences(requireContext())
        storageref=FirebaseStorage.getInstance().getReference("Images")
        id=databaseref.push().key.toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profile= FragmentProfileBinding.inflate(layoutInflater,container,false)




        binding.savebtn.setOnClickListener {
            val name = binding.edttextname.text.toString()
            val email = binding.edttextemail.text.toString()
            val phoneno = binding.edttextphoneno.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty() && phoneno.isNotEmpty()){
                saveDetailsToFirebase(name, email, phoneno)
            }else{
                Toast.makeText(requireContext(),"Please Fill The Fields",Toast.LENGTH_SHORT).show()
            }
        }

        val pickimage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imageView.setImageURI(uri)
            if (uri != null) {
                imageuri = uri
            }
        }

        binding.imageView.setOnClickListener {
            pickimage.launch("image/*")
        }



        return binding.root
    }

    private fun saveDetailsToFirebase(name: String, email: String, phoneno: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                imageuri.let { uri ->
                    val uploadTask = storageref.child(id).putFile(uri).await()
                    val imageUrl = uploadTask.metadata?.reference?.downloadUrl?.await().toString()

                    withContext(Dispatchers.Main) {
                        databaseref.child(auth.currentUser.uid).apply {
                            child("username").setValue(name)
                            child("email").setValue(email)
                            child("phoneno").setValue(phoneno)
                            child("profileimage").setValue(imageUrl)
                        }
                        preferences.email=email
                        preferences.phoneno=phoneno
                        preferences.username=name
                        preferences.profileimage=imageUrl
                    }
                    intentToMainActivity()
                }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "No Image Found",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Firebase Error", "Error uploading data to Firebase: ${e.message}", e)
                }
            }
        }
    }



    private fun intentToMainActivity() {
        val intent = Intent(requireActivity(), StartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }


    private fun setUserName(){
        databaseref.child(auth.currentUser.uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user  = snapshot.getValue(UserData::class.java)
                binding.txtviewname.setText(user?.username)
                binding.edttextname.setText(user?.username)
                binding.edttextemail.setText(user?.email)
                binding.edttextphoneno.setText(user?.phoneno)
                binding.imageView.setImageURI(user?.profileimage?.toUri())
                Glide.with(requireContext()).load(user?.profileimage).into(binding.imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}


