package com.rajkumarrajan.latesttechexample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rajkumarrajan.latesttechexample.Adapter.AlbumsDetailsAdapter
import com.rajkumarrajan.latesttechexample.EventBusConnection.IntentServiceResult
import com.rajkumarrajan.latesttechexample.R
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel
import com.rajkumarrajan.latesttechexample.Utils.SessionManager
import com.rajkumarrajan.latesttechexample.ViewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainActivityViewModel
    private var session: SessionManager? = null
    lateinit var albumsDetailsAdapter: AlbumsDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mViewModel.constructor(this)
        session = SessionManager(this)
        session!!.displayLoader()
        mViewModel.getAlbumsDetails()


        RecyclerViewAlbumsDetails.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mViewModel.getalbumsDetailsObserverValue().observe(this, Observer {
            if (it.size > 1) {
                session!!.dismissLoader()
                albumsDetailsAdapter = AlbumsDetailsAdapter(
                    this,
                    it,
                    object : AlbumsDetailsAdapter.AlbumsDetailsInterface {
                        override fun click(position: Int) {
                            session!!.shortToast(it[position].title.toString())
                        }
                    })
                RecyclerViewAlbumsDetails.adapter = albumsDetailsAdapter
            } else {
                mViewModel.apiHit(getString(R.string.albums_api))
            }

        })

        mViewModel.addAlbumsDetailsObserverValue().observe(this, Observer {
            if (it) {
                session!!.displayLoader()
                mViewModel.getAlbumsDetails()
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        val finalresponse: String = intentServiceResult.resultValue
        val apiName: String = intentServiceResult.apiName
        if (apiName.equals(getString(R.string.albums_api))) {
            session!!.dismissLoader()
            session!!.shortToast(getString(R.string.some_thing_wrong))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(myModel: List<MyModel>) {
        mViewModel.apiFunction(myModel)
        session!!.dismissLoader()
        session!!.shortToast(getString(R.string.success))
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        mViewModel.destructor()
    }

}
