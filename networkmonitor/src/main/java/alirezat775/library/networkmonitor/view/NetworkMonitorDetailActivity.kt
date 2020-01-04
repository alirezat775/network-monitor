package alirezat775.library.networkmonitor.view

import alirezat775.library.networkmonitor.R
import alirezat775.library.networkmonitor.core.NetworkLogging
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2020-01-04
 */

class NetworkMonitorDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_details)
        val uuid: String = intent.getStringExtra("uuid") ?: ""
        NetworkLogging.list.forEach {
            if (it.uuid == uuid)
                Log.d("TEST", it.request.url)
        }
    }
}