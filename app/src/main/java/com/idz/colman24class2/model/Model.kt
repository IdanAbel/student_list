package com.idz.colman24class2.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.idz.colman24class2.base.EmptyCallback
import com.idz.colman24class2.base.StudentsCallback
import com.idz.colman24class2.model.dao.AppLocalDb
import com.idz.colman24class2.model.dao.AppLocalDbRepository
import java.util.concurrent.Executors

interface GetAllStudentsListener {
    fun onCompletion(students: List<Student>)
}

class Model private constructor() {

    private val database: AppLocalDbRepository = AppLocalDb.database
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    private val firebaseModel = FirebaseModel()

    companion object {
        val shared = Model()
    }

    fun getAllStudents(callback: StudentsCallback) {
        firebaseModel.getAllStudents(callback)
//
//        executor.execute {
//            val students = database.studentDao().getAllStudents()
//
//            Thread.sleep(4000)
//
//            mainHandler.post {
//                callback(students)
//            }
//        }
    }

    fun add(student: Student, callback: EmptyCallback) {
        firebaseModel.add(student, callback)
//
//        executor.execute {
//            database.studentDao().insertStudents(student)
//
//            Thread.sleep(4000)
//
//            mainHandler.post {
//                callback()
//            }
//        }
    }
}