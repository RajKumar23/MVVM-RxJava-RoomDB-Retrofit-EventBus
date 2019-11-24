package com.rajkumarrajan.latesttechexample.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajkumarrajan.latesttechexample.APIService.APIFetchService
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel
import com.rajkumarrajan.latesttechexample.R
import com.rajkumarrajan.latesttechexample.RoomDB.Room.MyRoomDBClient
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    private val albumsDetailsObserver = MutableLiveData<ArrayList<MyModel>>()
    private val addAlbumsDetailsObserver = MutableLiveData<Boolean>()
    private var disposable: CompositeDisposable? = null
    private lateinit var mContext: Context

    fun constructor(context: Context) {
        disposable = CompositeDisposable()
        mContext = context
    }


    fun apiHit(toHit: String) {
        val serviceClass = APIFetchService::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.api_hit_key), toHit)
        mContext.startService(intent)
    }

    fun apiFunction(myModel: List<MyModel>) {

        Completable.fromAction {
            MyRoomDBClient(mContext).getInstance(mContext)
                .getAppDatabase()
                .myModelDAO().insertAll(
                    myModel
                )
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    // body
                }

                override fun onComplete() {
                    addAlbumsDetailsObserver.value = true

                }

                override fun onError(e: Throwable) {
                    // body
                    e.printStackTrace()
                    addAlbumsDetailsObserver.value = false
                }
            })

    }

    fun getAlbumsDetails() {
        disposable!!.add(
            MyRoomDBClient(mContext).getInstance(mContext).getAppDatabase()
                .myModelDAO().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { myModel ->
                    if (myModel.isNotEmpty()) {
                        val myPojoArrayList = ArrayList<MyModel>()
                        myPojoArrayList.addAll(myModel)
                        albumsDetailsObserver.value = myPojoArrayList
                    }else{
                        albumsDetailsObserver.value = ArrayList<MyModel>()
                    }

                })
        )
    }


    fun getalbumsDetailsObserverValue(): MutableLiveData<ArrayList<MyModel>> {
        return albumsDetailsObserver
    }

    fun addAlbumsDetailsObserverValue(): MutableLiveData<Boolean> {
        return addAlbumsDetailsObserver
    }

    fun destructor() {
        MyRoomDBClient(mContext).destroyInstance()
        disposable!!.dispose()
    }

}