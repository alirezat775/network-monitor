package alirezat775.networkmonitor.view

import alirezat775.networkmonitor.R
import alirezat775.networkmonitor.core.NetworkModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.network_log_item.view.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-30
 */

open class NetworkLoggingAdapter : RecyclerView.Adapter<NetworkLoggingAdapter.ViewHolder>() {

    private var list = mutableListOf<NetworkModel>()
    var clickItem: ClickItem? = null

    interface ClickItem {
        fun onClick(uuid: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val url = itemView.network_url_log
        val method = itemView.network_method_log
        val status = itemView.network_status_log
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
                R.layout.network_log_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { clickItem?.onClick(list[position].uuid) }
        holder.url.text = list[position].request.url
        holder.method.text = list[position].request.method
        holder.status.text = list[position].response.code.toString()
        holder.itemView.context?.let {
            if (list[position].response.code < 400) {
                holder.status.setTextColor(ContextCompat.getColor(it, R.color.network_green))
            } else {
                holder.status.setTextColor(ContextCompat.getColor(it, R.color.network_red))
            }
        }
    }
}