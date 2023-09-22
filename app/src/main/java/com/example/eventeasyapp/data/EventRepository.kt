package com.example.eventeasyapp.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
//import android.media.metrics.Event
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.eventeasyapp.navigation.ROUTE_LOGIN
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.eventeasyapp.models.Event
import com.google.firebase.storage.FirebaseStorage

class EventRepository(var navController: NavHostController, var context: Context){
//    var authRepository: AuthRepository
    var progress: ProgressDialog
//    var products: ArrayList<Event>

    init {
//        authRepository = AuthRepository(navController, context)
//        if (!authRepository.isLoggedIn()) {
//            navController.navigate(ROUTE_LOGIN)
//        }
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

//        products = mutableListOf<Event>() as ArrayList<Event>
    }


    fun savePosts(title:String, time:String, location:String, price:String, desc:String,filePath:Uri){
        var id = System.currentTimeMillis().toString()
        var storageReference = FirebaseStorage.getInstance().getReference().child("Events/$id")
        progress.show()

        storageReference.putFile(filePath).addOnCompleteListener{
            progress.dismiss()
            if (it.isSuccessful){
                // Proceed to store other data into the db
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageUrl = it.toString()
                    var houseData = Event(title, time, location, price, desc, imageUrl, id)
                    var dbRef = FirebaseDatabase.getInstance()
                        .getReference().child("Events/$id")
                    dbRef.setValue(houseData)
                    Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

//    fun viewPosts(event: MutableState<Event>, myEvent: SnapshotStateList<Event>): SnapshotStateList<Event> {
//        var ref = FirebaseDatabase.getInstance().getReference().child("Events")
//
//        progress.show()
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                progress.dismiss()
//                myEvent.clear()
//                for (snap in snapshot.children){
//                    val value = snap.getValue(Event::class.java)
//                    event.value = value!!
//                    myEvent.add(value)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//        return myEvent
//    }


    fun viewPosts(post:MutableState<Event>, posts:SnapshotStateList<Event>): SnapshotStateList<Event> {
        var ref = FirebaseDatabase.getInstance().getReference().child("Events")

        progress.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.dismiss()
                posts.clear()
                for (snap in snapshot.children){
                    val value = snap.getValue(Event::class.java)
                    post.value = value!!
                    posts.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return posts
    }


    fun deletePost(id:String){
        var delRef = FirebaseDatabase.getInstance().getReference().child("Events/$id")
        progress.show()
        delRef.removeValue().addOnCompleteListener {
            progress.dismiss()
            if(it.isSuccessful){
                Toast.makeText(context, "Post deleted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updatePosts(title:String, time:String, location:String, price:String, desc:String,id:String, filePath: Uri){
        var storageReference = FirebaseStorage.getInstance().getReference().child("Events/$id")
        progress.show()

        storageReference.putFile(filePath).addOnCompleteListener{
            progress.dismiss()
            if (it.isSuccessful){
                // Proceed to store other data into the db
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageUrl = it.toString()
                    var houseData = Event(title, time, location, price, desc, imageUrl, id)
                    var dbRef = FirebaseDatabase.getInstance()
                        .getReference().child("Events/$id")
                    dbRef.setValue(houseData)
                    Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }


    }



}

