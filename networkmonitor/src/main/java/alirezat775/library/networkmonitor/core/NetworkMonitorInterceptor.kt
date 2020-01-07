package alirezat775.library.networkmonitor.core

import alirezat775.library.networkmonitor.OkHttpHelper
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val rq = chain.request()
        val rqModel = RequestNetworkModel(
            rq.url().host() + rq.url().encodedPath(),
            rq.method(),
            rq.headers(),
            OkHttpHelper.requestToString(rq)
        )
        val rs = chain.proceed(rq)
        val rsModel = ResponseNetworkModel(
            rs.code(),
            rs.message(),
            rs.headers(),
            rs.body()?.string()
        )
        val uuid = UUID.randomUUID().toString() + System.currentTimeMillis()
        NetworkLogging.list.add(NetworkModel(uuid, rqModel, rsModel))
        Log.d("TAG_TEST", rqModel.method)
        return rs
    }
}