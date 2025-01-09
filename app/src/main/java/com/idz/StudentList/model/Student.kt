package com.idz.StudentList.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val address: String,
    val avatarUrl: String,
    var isChecked: Boolean
) {

    companion object {

        private const val ID_KEY = "id"
        private const val NAME_KEY = "name"
        private const val PHONE_KEY = "phone"
        private const val ADDRESS_KEY = "address"
        private const val AVATAR_URL_KEY = "avatarUrl"
        private const val IS_CHECKED_KEY = "isChecked"

        fun fromJSON(json: Map<String, Any>): Student {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val phone = json[PHONE_KEY] as? String ?: ""
            val address = json[ADDRESS_KEY] as? String ?: ""
            val avatarUrl = json[AVATAR_URL_KEY] as? String ?: ""
            val isChecked = json[IS_CHECKED_KEY] as? Boolean ?: false

            return Student(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                phone = phone,
                address = address,
                isChecked = isChecked
            )
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                ID_KEY to id,
                NAME_KEY to name,
                PHONE_KEY to phone,
                ADDRESS_KEY to address,
                AVATAR_URL_KEY to avatarUrl,
                IS_CHECKED_KEY to isChecked)
        }
}
