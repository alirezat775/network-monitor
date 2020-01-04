package alirezat775.library.networkmonitor.core

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
        val rqModel =
            RequestNetworkModel(
                rq.url().host() + rq.url().encodedPath(),
                rq.method(),
                rq.headers(),
                rq.body()
            )
        val rs = chain.proceed(rq)
        val rsModel =
            ResponseNetworkModel(
                rs.code(),
                rs.message(),
                rs.headers(),
                rs.body()?.string()
            )
        if (NetworkLogging.list.size > 30) NetworkLogging.clear()

        val uuid = UUID.randomUUID().toString()
        NetworkLogging.list.add(NetworkModel(uuid, rqModel, rsModel))
        return rs
    }
}