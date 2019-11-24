package com.rajkumarrajan.latesttechexample.RoomDB.Model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["title"])
data class MyModel(

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("title")
	val title: String = "",

	@field:SerializedName("userId")
	val userId: Int = 0
)