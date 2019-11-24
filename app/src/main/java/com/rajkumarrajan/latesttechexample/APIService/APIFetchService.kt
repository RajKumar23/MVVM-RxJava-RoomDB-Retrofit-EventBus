package com.rajkumarrajan.latesttechexample.APIService

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.rajkumarrajan.latesttechexample.EventBusConnection.IntentServiceResult
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel
import com.rajkumarrajan.latesttechexample.R
import com.rajkumarrajan.latesttechexample.Retrofit.RetrofitInstance
import com.rajkumarrajan.latesttechexample.Retrofit.RetrofitInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class APIFetchService : IntentService("ApiHit") {
    private var apiName: String = ""
    private var disposable: CompositeDisposable? = null
    private var responseToSend: String = "failed"

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?) {
        val apiService = RetrofitInstance.createService(RetrofitInterface::class.java)
        if (intent?.hasExtra(getString(R.string.api_hit_key))!!) {
            apiName = intent.getStringExtra(getString(R.string.api_hit_key))!!
        }


        if (apiName == getString(R.string.albums_api)) {
            disposable!!.add(
                apiService.MyPojoCall()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<MyModel>>() {
                        override fun onSuccess(myModel: List<MyModel>) {
                            EventBus.getDefault().post(
                                myModel
                            )
                        }

                        override fun onError(e: Throwable) {
                            EventBus.getDefault().post(
                                IntentServiceResult(
                                    Activity.RESULT_OK,
                                    responseToSend,
                                    getString(R.string.albums_api)
                                )
                            )
                            Log.e("Error", "onError: " + e.message)
                        }
                    })
            )

        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        disposable = CompositeDisposable()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        disposable = CompositeDisposable()
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        disposable!!.dispose();
    }

}