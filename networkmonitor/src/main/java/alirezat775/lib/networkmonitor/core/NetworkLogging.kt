package alirezat775.lib.networkmonitor.core

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-30
 */

object NetworkLogging {

    internal val list = mutableListOf<NetworkModel>()
    internal var addItemListener: OnAddItemListener? = null

    fun clear() {
        list.clear()
    }

}