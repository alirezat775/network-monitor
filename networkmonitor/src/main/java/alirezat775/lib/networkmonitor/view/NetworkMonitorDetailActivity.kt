package alirezat775.lib.networkmonitor.view

import alirezat775.lib.networkmonitor.core.NetworkLogging
import alirezat775.networkmonitor.R
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.network_log_details.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2020-01-04
 */

class NetworkMonitorDetailActivity : AppCompatActivity() {

    private var responseLog: String = ""

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.network_log_details)
        val uuid: String = intent.getStringExtra("uuid") ?: ""

        network_response_copy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("response", responseLog)
            Toast.makeText(this, getString(R.string.network_response_copied), Toast.LENGTH_LONG)
            clipboard.setPrimaryClip(clip)
        }

        NetworkLogging.list.forEach {
            if (it.uuid == uuid) {
                val rqUrl: Spannable = SpannableString("url: ${it.request.url}")
                rqUrl.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val rqHeaders: Spannable = SpannableString("headers: ${it.request.headers}")
                rqHeaders.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val rqBody: Spannable = SpannableString("body: ${it.request.body}")
                rqBody.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                network_request_url.text = rqUrl
                network_request_header.text = rqHeaders
                network_request_body.text = rqBody

                val rsCode: Spannable = SpannableString("code: ${it.response.code}")
                rsCode.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val rsMessage: Spannable = SpannableString("message: ${it.response.message}")
                rsMessage.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val rsHeaders: Spannable = SpannableString("headers: ${it.response.headers}")
                rsHeaders.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val rsBody: Spannable = SpannableString("body: ${it.response.body}")
                rsBody.setSpan(
                    ForegroundColorSpan(Color.BLACK), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                network_response_code.text = rsCode
                network_response_message.text = rsMessage
                network_response_header.text = rsHeaders
                network_response_body.text = rsBody

                responseLog =
                    "status code:======== $rsCode \n======== \n message:======== $rsMessage \n======== \n headers:======== $rsHeaders \n======== \n body:========\n $rsBody \n========"
            }

        }
    }
}