package alirezat775.library.networkmonitor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val rq = chain.request()
        val rqModel = RequestNetworkModel(rq.url(), rq.method(), rq.headers(), rq.body())
        val rs = chain.proceed(rq)
        val rsModel = ResponseNetworkModel(rs.code(), rs.message(), rs.headers(), rs.body())
        NetworkLogging.hashMap[rqModel] = rsModel
        return rs
    }
}