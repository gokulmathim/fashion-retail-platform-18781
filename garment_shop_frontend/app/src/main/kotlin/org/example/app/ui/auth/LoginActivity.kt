package org.example.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.GarmentApp
import org.example.app.R
import org.example.app.data.model.UserSession
import org.example.app.ui.home.HomeActivity

/**
 * Log in to the app (mock). On success, return to home.
 */
class LoginActivity : AppCompatActivity() {

    private val authRepo by lazy { (application as GarmentApp).authRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email: EditText = findViewById(R.id.inputEmail)
        val password: EditText = findViewById(R.id.inputPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnGoRegister)

        btnLogin.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val session: UserSession = authRepo.login(
                        email.text.toString(),
                        password.text.toString()
                    )
                    Toast.makeText(this@LoginActivity, getString(R.string.msg_login_success), Toast.LENGTH_SHORT).show()
                    // In real app, save session/token securely (DataStore/EncryptedPrefs).
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, e.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
