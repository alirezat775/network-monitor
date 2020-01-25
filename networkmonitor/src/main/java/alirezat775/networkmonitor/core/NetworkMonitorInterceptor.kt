package alirezat775.networkmonitor.core

import alirezat775.networkmonitor.OkHttpHelper
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
                OkHttpHelper.requestToString(rq)
            )
        val rs = chain.proceed(rq)
        val rsModel =
            ResponseNetworkModel(
                rs.code(),
                rs.message(),
                rs.headers(),
                OkHttpHelper.responseToString(rs)
            )
        val uuid = UUID.randomUUID().toString() + System.currentTimeMillis()
        NetworkLogging.list.add(
            NetworkModel(
                uuid,
                rqModel,
                rsModel
            )
        )
        NetworkLogging.addItemListener?.itemAdded()

        return rs
    }
}