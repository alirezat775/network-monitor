package alirezat775.library.networkmonitor

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-30
 */

object NetworkLogging {

    internal val hashMap: HashMap<RequestNetworkModel, ResponseNetworkModel> = HashMap()

    fun clear() {
        hashMap.clear()
    }
}