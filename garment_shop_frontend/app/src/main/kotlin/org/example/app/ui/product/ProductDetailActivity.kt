package org.example.app.ui.product

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.example.app.GarmentApp
import org.example.app.R
import org.example.app.data.model.Product
import org.example.app.ui.cart.CartActivity
import org.example.app.util.toCurrency

/**
 * Shows details about a selected product and allows adding to cart.
 */
class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
        const val EXTRA_PRODUCT_NAME = "extra_product_name"
        const val EXTRA_PRODUCT_DESC = "extra_product_desc"
        const val EXTRA_PRODUCT_PRICE = "extra_product_price"
    }

    private val cartRepo by lazy { (application as GarmentApp).cartRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val name = intent.getStringExtra(EXTRA_PRODUCT_NAME).orEmpty()
        val desc = intent.getStringExtra(EXTRA_PRODUCT_DESC).orEmpty()
        val priceCents = intent.getIntExtra(EXTRA_PRODUCT_PRICE, 0)
        val id = intent.getStringExtra(EXTRA_PRODUCT_ID).orEmpty()

        val product = Product(id = id, name = name, description = desc, priceCents = priceCents)

        val title: TextView = findViewById(R.id.txtTitle)
        val description: TextView = findViewById(R.id.txtDescription)
        val price: TextView = findViewById(R.id.txtPrice)
        val btnAdd: Button = findViewById(R.id.btnAddToCart)
        val btnGoCart: Button = findViewById(R.id.btnGoToCart)

        title.text = product.name
        description.text = product.description
        price.text = product.priceCents.toCurrency()

        btnAdd.setOnClickListener {
            cartRepo.addToCart(product, 1)
        }
        btnGoCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}
