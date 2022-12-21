package com.example.firestorerepetion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

        val uid = "askjehf3847"

//        val user = User(name = "David")
//        db.collection("users").document(uid).set(user)
//
        val mushroom1 = MushroomPlace(name = "näst Bästa stället", mushroom = "karl-johan", place="bredvid stora stenen", user="askdfjh378")
//        val mushroom2 = MushroomPlace(name = "tredje Bästa stället", mushroom = "flugsvamp", place="bredvid stora stenen", user="sakdfjh83")
//        val mushroom3 = MushroomPlace(name = "fjärde Bästa stället", mushroom = "mörkla", place="bredvid stora stenen", user=uid)
//
//        db.collection("mushrooms").add(mushroom1)
//        db.collection("mushrooms").add(mushroom2)
//        db.collection("mushrooms").add(mushroom3)

       // db.collection("users").document(uid).collection("mushroom").add(mushroom)

        var mushroomPlaces = mutableListOf<MushroomPlace>();

       // val userUID = mushroom1.user

        // 1
        db.collection("mushrooms")//.whereEqualTo("user", userUID )
            .addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            // 2
            if (snapshot != null ) {
                mushroomPlaces.clear()
                for( document in snapshot.documents ) {
                    val mushroom = document.toObject<MushroomPlace>()
                    if (mushroom != null) {
                        mushroomPlaces.add(mushroom);
                    }
                }
            }
            // 3
            for( mushroom in mushroomPlaces) {
                if (mushroom.user != null) {
                    db.collection("users").document(mushroom.user!!).get().addOnSuccessListener { snapshot ->
                        val user = snapshot.toObject<User>()
                        Log.d("!!!", "${mushroom.mushroom} ${user?.name}")
                        //4
                    }
                }
            }
            //5
        }
        //6

        // 1 6 2 3 5 4


    }
}

// skapa
// 1. ladda upp bild till storage
// 2. när upladdningen är klar kan vi få tag på URL till bilden
// 3. Skapa eller uppdatera ett mushroomPLace objekt med rätt imageURL
// 4. Ladda upp mushrromPlace obketet till Firestore

// läsa
// 1. ladda ner mushroomplace från firestore
// 2. ladda ner bilden med hjälp av länken (Glide eller picaso är bibliotek som kan hjälpa oss med det)


data class MushroomPlace(@DocumentId var documentId : String? = null,
                         var name : String? = null,
                         var mushroom: String? = null,
                         var place : String? = null,
                         var user: String? = null)
                        //var imageURL: String? = null )

data class User(@DocumentId var documentId: String? =null,
                var name: String? = null,
                var email: String? = null)
