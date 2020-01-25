package alirezat775.networkmonitor.core

import okhttp3.Headers

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-30
 */

data class NetworkModel(
    val uuid: String,
    val request: RequestNetworkModel,
    val response: ResponseNetworkModel
)

data class RequestNetworkModel(
    val url: String,
    val method: String,
    val headers: Headers,
    val body: String?
)

data class ResponseNetworkModel(
    val code: Int,
    val message: String,
    val headers: Headers,
    val body: String?
)