package alirezat775.app.networkmonitor

import alirezat775.library.networkmonitor.NetworkMonitor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var networkMonitor = NetworkMonitor(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkMonitor.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.unRegister()
    }
}
