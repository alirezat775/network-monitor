package alirezat775.lib.networkmonitor.core

import alirezat775.lib.networkmonitor.OkHttpHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset
import java.util.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val rq = chain.request()
        val rqModel =
            RequestNetworkModel(
                rq.url().host() + rq.url().encodedPath(),
                rq.method(),
                rq.headers(),
                OkHttpHelper.requestToString(rq)
            )
        val rs = chain.proceed(rq)

        var responseString = ""
        rs.body()?.let { body ->
            val source = body.source()
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer()
            val charsetRaw = body.contentType()?.charset(Charset.forName("UTF-8"))
            charsetRaw?.let { charset ->
                responseString = buffer.clone().readString(charset)
            }
        }
        val rsModel = ResponseNetworkModel(rs.code(), rs.message(), rs.headers(), responseString)
        val uuid = UUID.randomUUID().toString() + System.currentTimeMillis()
        NetworkLogging.list.add(NetworkModel(uuid, rqModel, rsModel))
        NetworkLogging.addItemListener?.itemAdded()

        return rs
    }
}