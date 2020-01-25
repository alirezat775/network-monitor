package alirezat775.networkmonitor

import android.text.TextUtils
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.StatusLine
import okio.Buffer
import java.io.IOException
import java.net.HttpURLConnection
import java.nio.charset.Charset

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-30
 */
object OkHttpHelper {

    private const val DEFAULT_INDENT_SPACES = 4
    private val UTF8 = Charset.forName("UTF-8")

    fun stringifyHeaders(headers: Map<String?, List<String?>?>): String {
        val sb = StringBuilder()
        for ((key, value) in headers) {
            if (value != null && value.isNotEmpty()) {
                appendKey(sb, key)
                appendValue(sb, value)
            }
        }
        return sb.toString()
    }

    private fun appendKey(sb: StringBuilder, key: String?) {
        if (key != null) {
            sb.append('[').append(key).append("]\n")
        }
    }

    private fun appendValue(sb: StringBuilder, values: List<String?>?) {
        if (values != null) {
            sb.append(TextUtils.join(", ", values)).append("\n\n")
        }
    }

    fun cloneResponse(response: Response): Response? {
        return try {
            response.newBuilder().body(response.peekBody(Long.MAX_VALUE))
                .build()
        } catch (ignore: IOException) {
            null
        }
    }

    fun requestToString(request: Request): String? {
        return if (request.body() == null) "" else try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "No request data"
        }
    }

    fun responseToString(response: Response): String? {
        val responseBody = response.body()
        val source = responseBody!!.source()
        val buffer = source.buffer()
        return buffer.readUtf8()
        //return buffer.clone().readString(UTF8) ?: return buffer.readUtf8()
    }

    /**
     * Returns true if the response must have a (possibly 0-length) body. See RFC 2616 section 4.3.
     */
    fun hasBody(response: Response): Boolean { // HEAD requests never yield a body regardless of the response headers.
        if (response.request().method() == "HEAD") {
            return false
        }
        val responseCode = response.code()
        if ((responseCode < StatusLine.HTTP_CONTINUE || responseCode >= 200)
            && responseCode != HttpURLConnection.HTTP_NO_CONTENT && responseCode != HttpURLConnection.HTTP_NOT_MODIFIED
        ) {
            return true
        }
        val contentLength = stringToLong(
            response.header("Content-Length")
        )
        return contentLength != -1L || "chunked".equals(
            response.header("Transfer-Encoding"),
            ignoreCase = true
        )
    }

    private fun stringToLong(s: String?): Long {
        return if (s == null) -1 else try {
            s.toLong()
        } catch (e: NumberFormatException) {
            -1
        } as Long
    }
}