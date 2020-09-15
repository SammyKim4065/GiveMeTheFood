package com.example.givemethefood.Data

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table",indices = [Index(value = ["userId"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("isRestaurant")
    val isRestaurant: Boolean?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?
)