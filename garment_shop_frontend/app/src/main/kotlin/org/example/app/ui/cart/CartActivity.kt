package org.example.app.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.GarmentApp
import org.example.app.R
import org.example.app.ui.checkout.CheckoutActivity
import org.example.app.util.toCurrency

/**
 * Shows cart contents and allows quantity management and checkout navigation.
 */
class CartActivity : AppCompatActivity() {

    private val cartRepo by lazy { (application as GarmentApp).cartRepository }

    private lateinit var recycler: RecyclerView
    private lateinit var totalView: TextView
    private lateinit var btnCheckout: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recycler = findViewById(R.id.recyclerCart)
        totalView = findViewById(R.id.txtTotal)
        btnCheckout = findViewById(R.id.btnCheckout)

        adapter = CartAdapter(
            onIncrease = { item ->
                cartRepo.updateQuantity(item.product.id, item.quantity + 1)
                refresh()
            },
            onDecrease = { item ->
                cartRepo.updateQuantity(item.product.id, item.quantity - 1)
                refresh()
            },
            onRemove = { item ->
                cartRepo.remove(item.product.id)
                refresh()
            }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        refresh()

        btnCheckout.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }

    private fun refresh() {
        val items = cartRepo.getCartItems()
        adapter.submit(items)
        totalView.text = getString(R.string.label_total) + ": " + cartRepo.totalCents().toCurrency()
    }
}
