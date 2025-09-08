package org.example.app.ui.checkout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.GarmentApp
import org.example.app.R
import org.example.app.util.toCurrency

/**
 * Checkout screen that performs a mock checkout flow and displays a summary.
 */
class CheckoutActivity : AppCompatActivity() {

    private val cartRepo by lazy { (application as GarmentApp).cartRepository }
    private val authRepo by lazy { (application as GarmentApp).authRepository } // For future use

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val summary: TextView = findViewById(R.id.txtSummary)
        val btnPlaceOrder: Button = findViewById(R.id.btnPlaceOrder)

        summary.text = getString(R.string.label_total) + ": " + cartRepo.totalCents().toCurrency()

        btnPlaceOrder.setOnClickListener {
            lifecycleScope.launch {
                try {
                    // For demo, generate a temporary session by logging in anonymously
                    val session = (application as GarmentApp).authRepository.login("demo@user.com", "password")
                    val order = cartRepo.checkout(session.token)
                    Toast.makeText(this@CheckoutActivity, getString(R.string.msg_order_success), Toast.LENGTH_LONG).show()
                    val result: TextView = findViewById(R.id.txtOrderResult)
                    result.text = "Order #${order.orderId}\nItems: ${order.itemCount}\nTotal: ${order.totalCents.toCurrency()}"
                } catch (_: Exception) {
                    Toast.makeText(this@CheckoutActivity, getString(R.string.msg_order_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
