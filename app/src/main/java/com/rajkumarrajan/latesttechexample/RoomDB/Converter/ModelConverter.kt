package com.rajkumarrajan.latesttechexample.RoomDB.Converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel

class ModelConverter {
    @TypeConverter
    fun FromString(value: String): ArrayList<MyModel> {
        val listType = object : TypeToken<ArrayList<MyModel>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun ToString(list: ArrayList<MyModel>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
