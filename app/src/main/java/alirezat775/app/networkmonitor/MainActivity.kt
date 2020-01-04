package alirezat775.app.networkmonitor

import alirezat775.library.networkmonitor.NetworkMonitor
import alirezat775.library.networkmonitor.core.NetworkMonitorInterceptor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var networkMonitor = NetworkMonitor(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkMonitor.register()

        for (i in 0..10) {
            okHttpGet(i)
        }
    }

    private fun okHttpGet(i: Int) {
        val client = OkHttpClient.Builder()
            .addInterceptor(NetworkMonitorInterceptor())
            .build()

        val request: Request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com/todos/$i")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.unRegister()
    }
}
