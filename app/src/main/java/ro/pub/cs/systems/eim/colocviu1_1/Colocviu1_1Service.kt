package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

class Colocviu1_1Service : Service() {

    private var processingThread: ProcessingThread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pressed = intent?.getStringExtra("pressed") ?: "None"
        processingThread = ProcessingThread(this, pressed)
        processingThread?.start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        processingThread?.stopThread()
        super.onDestroy()
    }
}

class ProcessingThread(private val ctx: Context, private val pressed: String): Thread() {
    private var isRunning = true
    private val actions = listOf(
        "ro.pub.cs.systems.eim.action1",
        "ro.pub.cs.systems.eim.action2",
        "ro.pub.cs.systems.eim.action3"
    )

    override fun run() {
        while (isRunning) {
            val date = java.util.Date().toString()

            val intent = Intent()
            intent.action = actions.random()
            intent.putExtra("message", "Time: $date, Pressed: $pressed")
            ctx.sendBroadcast(intent)

            try {
                sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun stopThread() {
        isRunning = false
    }
}