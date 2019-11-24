package com.rajkumarrajan.latesttechexample.RoomDB.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rajkumarrajan.latesttechexample.RoomDB.Converter.ModelConverter
import com.rajkumarrajan.latesttechexample.RoomDB.DAO.MyModelDAO
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel

@Database(
    entities = [MyModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ModelConverter::class)
abstract class MyRoomDB : RoomDatabase() {
    abstract fun myModelDAO(): MyModelDAO
}
