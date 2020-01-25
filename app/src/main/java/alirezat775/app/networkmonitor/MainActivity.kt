package alirezat775.app.networkmonitor

import alirezat775.networkmonitor.NetworkMonitor
import alirezat775.networkmonitor.core.NetworkMonitorInterceptor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var networkMonitor = NetworkMonitor(this)
    private val client by lazy { OkHttpClient.Builder()
            .addInterceptor(NetworkMonitorInterceptor())
            .build()
    }
    private lateinit var jsonObject:JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkMonitor.register()

        for (i in 0..10) {
            okHttpGet(i)
        }

        jsonObject = JSONObject()
        jsonObject.put("id", 1)
        jsonObject.put("title", "foo")
        jsonObject.put("body", "bar")
        jsonObject.put("userId", 1)

        okHttpPost(jsonObject.toString())
        okHttpDelete()

        jsonObject = JSONObject()
        jsonObject.put("title", "foo")
        okHttpPatch(jsonObject.toString())
    }

    private fun okHttpGet(i: Int) {
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

    private fun okHttpPost(body: String){

        val body = jsonObject.toString()
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body)
        val request:Request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(requestBody)
                .build()

        client.newCall(request).enqueue(object :Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
    })
    }

    private fun okHttpDelete() {
        val request: Request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .delete()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })
    }

    private fun okHttpPatch(body: String){
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body)
        val request :Request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .patch(requestBody)
                .build()

        client.newCall(request).enqueue(object :Callback {
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
