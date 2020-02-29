package alirezat775.lib.networkmonitor.view

import alirezat775.lib.networkmonitor.core.NetworkLogging
import alirezat775.lib.networkmonitor.core.OnAddItemListener
import alirezat775.networkmonitor.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.network_monitor_activity.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitorActivity : AppCompatActivity() {

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.network_monitor_activity)

        val adapter = NetworkLoggingAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

        network_clear.setOnClickListener {
            NetworkLogging.clear()
            adapter.notifyDataSetChanged()
        }

        NetworkLogging.addItemListener = object : OnAddItemListener {
            override fun itemAdded() {
                handler.post {
                    adapter.addItems(NetworkLogging.list)
                    adapter.notifyDataSetChanged()
                    recyclerView.layoutManager?.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

    }
}