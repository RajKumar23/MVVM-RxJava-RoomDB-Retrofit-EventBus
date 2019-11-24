package com.rajkumarrajan.latesttechexample.RoomDB.Room

import android.content.Context
import androidx.room.Room

class MyRoomDBClient(mCtx: Context) {

    private var mInstance: MyRoomDBClient? = null
    private var myRoomDB: MyRoomDB =
        Room.databaseBuilder(mCtx, MyRoomDB::class.java, "Secret").build()

    @Synchronized
    fun getInstance(context: Context): MyRoomDBClient {
        if (mInstance == null) {
            mInstance = MyRoomDBClient(context)
        }
        return mInstance!!
    }

    fun getAppDatabase(): MyRoomDB {
        return myRoomDB
    }

    fun destroyInstance() {
        mInstance = null
    }
}
