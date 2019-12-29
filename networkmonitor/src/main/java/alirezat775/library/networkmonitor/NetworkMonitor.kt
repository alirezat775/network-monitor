package alirezat775.library.networkmonitor

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector


/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-12-29
 * Email:   alirezat775@gmail.com
 */

class NetworkMonitor(context: Context) {

    private var sd: ShakeDetector
    private var sensorManager: SensorManager? =
        context.getSystemService(SENSOR_SERVICE) as SensorManager?
    private var shakeListener: ShakeDetector.Listener? = null

    init {
        shakeListener.let {
            sd = ShakeDetector(shakeListener)
        }
    }

    fun register() {
        shakeListener = ShakeDetector.Listener {}
        sd.start(sensorManager)
    }

    fun unRegister() {
        shakeListener = null
    }
}