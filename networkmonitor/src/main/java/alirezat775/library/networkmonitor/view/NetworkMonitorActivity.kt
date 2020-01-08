package alirezat775.library.networkmonitor.view

import alirezat775.library.networkmonitor.R
import alirezat775.library.networkmonitor.core.NetworkLogging
import alirezat775.library.networkmonitor.core.NetworkModel
import alirezat775.library.networkmonitor.core.OnAddItemListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.network_monitor_activity.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.network_monitor_activity)

        val adapter = NetworkLoggingAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recyclerView.adapter = adapter
        adapter.addItems(NetworkLogging.list)
        adapter.notifyDataSetChanged()
        adapter.clickItem = object : NetworkLoggingAdapter.ClickItem {
            override fun onClick(uuid: String) {
                val myIntent =
                    Intent(this@NetworkMonitorActivity, NetworkMonitorDetailActivity::class.java)
                myIntent.putExtra("uuid", uuid)
                startActivity(myIntent)
            }
        }

        NetworkLogging.addItemListener = object : OnAddItemListener {
            override fun addItem(networkModel: NetworkModel) {
                runOnUiThread {
                    adapter.addItem(networkModel)
                    recyclerView.scrollTo(0, 0)
                }
                Log.d("TEST", "MAMMAD")
            }

        }

        network_clear.setOnClickListener {
            NetworkLogging.clear()
            adapter.notifyDataSetChanged()
        }

    }
}