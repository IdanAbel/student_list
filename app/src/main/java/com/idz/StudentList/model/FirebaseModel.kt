package com.idz.StudentList.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.idz.StudentList.base.Constants
import com.idz.StudentList.base.EmptyCallback
import com.idz.StudentList.base.StudentsCallback

class FirebaseModel {

    private val database = Firebase.firestore

    init {
        val setting = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { })
        }

        database.firestoreSettings = setting

        val auth = Firebase.auth

//        auth.createUserWithEmailAndPassword("talzi@colman.ac.il", "supperStrong")

        auth.currentUser?.uid?.let {

            Log.i("TAG", auth.currentUser?.uid ?: "No use uuid")

            val json = hashMapOf(
                "name" to "Tal",
                "email" to "talzi@colman.ac.il"
            )
            database.collection("users").document(it).set(json)
                .addOnCompleteListener {
                    Log.i("TAG", auth.currentUser?.uid + "Saved" ?: "No use uuid")
                }
        }
    }

    fun getAllStudents(callback: StudentsCallback) {
        database.collection(Constants.COLLECTIONS.STUDENTS).get()
            .addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val students: MutableList<Student> = mutableListOf()
                        for (json in it.result) {
                            students.add(Student.fromJSON(json.data))
                        }
                        callback(students)
                    }

                    false -> callback(listOf())
                }
            }
    }

    fun add(student: Student, callback: EmptyCallback) {
        database.collection(Constants.COLLECTIONS.STUDENTS).document(student.id)
            .set(student.json)
            .addOnCompleteListener {
                callback()
            }
    }

    fun deleteStudentById(studentId: String, callback: EmptyCallback) {
        database.collection(Constants.COLLECTIONS.STUDENTS).document(studentId)
            .delete()
            .addOnCompleteListener {
                callback()
            }
    }

}

/*
val db = Firebase.firestore

// Create a new user with a first and last name
val user = hashMapOf(
    "first" to "Ada",
    "last" to "Lovelace",
    "born" to 1815,
)

// Add a new document with a generated ID
db.collection("users")
.add(user)
.addOnSuccessListener { documentReference ->
    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
}
.addOnFailureListener { e ->
    Log.w("TAG", "Error adding document", e)
}

 */