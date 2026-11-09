package ro.pub.cs.systems.eim.colocviu1_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_1SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colocviu1_1_secondary)

        val pressedText: TextView = findViewById(R.id.pressed_text)
        val registerButton: Button = findViewById(R.id.register_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        if (intent != null && intent.hasExtra("pressed")) {
            val pressed = intent.getStringExtra("pressed")
            pressedText.text = "Pressed buttons: $pressed"
        }

        registerButton.setOnClickListener {
            setResult(RESULT_OK, Intent())
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED, Intent())
            finish()
        }
    }
}