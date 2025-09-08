package org.example.app.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.GarmentApp
import org.example.app.R

/**
 * Register a new user (mock). Returns to previous screen on success.
 */
class RegisterActivity : AppCompatActivity() {

    private val authRepo by lazy { (application as GarmentApp).authRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name: EditText = findViewById(R.id.inputName)
        val email: EditText = findViewById(R.id.inputEmail)
        val password: EditText = findViewById(R.id.inputPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            lifecycleScope.launch {
                try {
                    authRepo.register(
                        name.text.toString(),
                        email.text.toString(),
                        password.text.toString()
                    )
                    Toast.makeText(this@RegisterActivity, getString(R.string.msg_register_success), Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, e.message ?: "Register failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
