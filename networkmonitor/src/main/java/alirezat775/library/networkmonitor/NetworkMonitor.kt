package alirezat775.library.networkmonitor

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitor(private val context: Context) {

    private var sd: ShakeDetector? = null
    private var sensorManager: SensorManager? = null
    private var shakeListener: ShakeDetector.Listener? = null

    fun register() {
        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager?
        shakeListener = ShakeDetector.Listener {
            val intent = Intent(context, NetworkMonitorActivity::class.java)
            context.startActivity(intent)
        }
        sd = ShakeDetector(shakeListener)
        sd?.start(sensorManager)
    }

    fun unRegister() {
        sensorManager = null
        shakeListener = null
    }
}