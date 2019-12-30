package alirezat775.library.networkmonitor.view

import alirezat775.library.networkmonitor.R
import alirezat775.library.networkmonitor.core.NetworkModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.log_item.view.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-30
 */

class NetworkLoggingAdapter : RecyclerView.Adapter<NetworkLoggingAdapter.ViewHolder>() {

    private var list = mutableListOf<NetworkModel>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val url = itemView.network_url_log
    }

    fun addItems(list: MutableList<NetworkModel>) {
        this.list = list
    }

    fun addItem(networkModel: NetworkModel) {
        this.list.add(networkModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.log_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.url.text = list[position].request.url.encodedPath()
    }
}