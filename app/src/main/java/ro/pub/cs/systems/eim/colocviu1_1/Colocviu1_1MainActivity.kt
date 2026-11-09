package ro.pub.cs.systems.eim.colocviu1_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1MainActivity : AppCompatActivity() {
    private lateinit var pressedText: TextView
    private lateinit var countText: TextView
    private var count: Int = 0
    private val pressed = StringBuilder()
    private val SERVICE_THRESHHOLD = 4
    private var serviceStarted = false

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("Colocviu1Broadcast", intent?.getStringExtra("message") ?: "Empty")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colocviu1_1_main)

        pressedText = findViewById(R.id.pressed_text)
        countText = findViewById(R.id.count_text)
        val northButton: Button = findViewById(R.id.north_button)
        val eastButton: Button = findViewById(R.id.east_button)
        val westButton: Button = findViewById(R.id.west_button)
        val southButton: Button = findViewById(R.id.south_button)
        val navigateButton: Button = findViewById(R.id.navigate_button)

        northButton.setOnClickListener {
            if (pressed.isEmpty()) {
                pressed.append("North")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            } else {
                pressed.append(",North")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            }
        }

        eastButton.setOnClickListener {
            if (pressed.isEmpty()) {
                pressed.append("East")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            } else {
                pressed.append(",East")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            }
        }

        westButton.setOnClickListener {
            if (pressed.isEmpty()) {
                pressed.append("West")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            } else {
                pressed.append(",West")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            }
        }

        southButton.setOnClickListener {
            if (pressed.isEmpty()) {
                pressed.append("South")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            } else {
                pressed.append(",South")
                count++
                countText.setText(count.toString())
                pressedText.setText(pressed.toString())
                checkAndStartService()
            }
        }

        navigateButton.setOnClickListener {
            val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java)
            intent.putExtra("pressed", pressed.toString())
            activityResultLauncher.launch(intent)
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("count")) {
                count = savedInstanceState.getInt("count")
                countText.setText(count.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    override fun onDestroy() {
        val intent = Intent(this, Colocviu1_1Service::class.java)
        stopService(intent)
        super.onDestroy()
    }

    private fun checkAndStartService() {
        if (count > SERVICE_THRESHHOLD && !serviceStarted) {
            val intent = Intent(this, Colocviu1_1Service::class.java)
            intent.putExtra("pressed", pressed.toString())
            startService(intent)
            serviceStarted = true
        }
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            count = 0
            countText.setText(count.toString())
            pressed.clear()
            pressedText.setText(pressed.toString())
            Toast.makeText(this, "Message from secondary activity: Register", Toast.LENGTH_LONG).show()
        }
        if (result.resultCode == RESULT_CANCELED) {
            count = 0
            countText.setText(count.toString())
            pressed.clear()
            pressedText.setText(pressed.toString())
            Toast.makeText(this, "Message from secondary activity: Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", count)
    }

    private val intentFilter = IntentFilter().apply {
        addAction("ro.pub.cs.systems.eim.action1")
        addAction("ro.pub.cs.systems.eim.action2")
        addAction("ro.pub.cs.systems.eim.action3")
    }
}