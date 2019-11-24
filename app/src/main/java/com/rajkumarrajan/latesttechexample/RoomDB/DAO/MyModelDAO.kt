package com.rajkumarrajan.latesttechexample.RoomDB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel
import io.reactivex.Maybe

@Dao
abstract class MyModelDAO {

    @Query("SELECT * FROM MyModel")
    abstract fun getAll(): Maybe<List<MyModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(myModel: List<MyModel>)
}
