package org.example.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.app.GarmentApp
import org.example.app.R
import org.example.app.data.model.Product
import org.example.app.ui.auth.LoginActivity
import org.example.app.ui.cart.CartActivity
import org.example.app.ui.product.ProductDetailActivity

/**
 * HomeActivity displays a searchable list of products and provides navigation
 * to authentication, cart, and product detail.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var searchInput: EditText
    private lateinit var searchClear: ImageButton
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductAdapter

    private var searchJob: Job? = null
    private var sessionToken: String? = null

    private val productRepo by lazy { (application as GarmentApp).productRepository }
    private val cartRepo by lazy { (application as GarmentApp).cartRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        searchInput = findViewById(R.id.inputSearch)
        searchClear = findViewById(R.id.btnClear)
        recycler = findViewById(R.id.recyclerProducts)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(
            onClick = { openProduct(it) },
            onAddToCart = {
                cartRepo.addToCart(it, 1)
                Toast.makeText(this, getString(R.string.msg_added_to_cart), Toast.LENGTH_SHORT).show()
            }
        )
        recycler.adapter = adapter

        searchInput.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { /* no-op */ }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { /* no-op */ }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                debounceSearch(s?.toString().orEmpty())
            }
        })
        searchClear.setOnClickListener {
            searchInput.text.clear()
        }

        // Initial load
        loadProducts(null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            R.id.action_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(250)
            loadProducts(query)
        }
    }

    private fun loadProducts(query: String?) {
        lifecycleScope.launch {
            val items = productRepo.getProducts(query)
            adapter.submit(items)
        }
    }

    private fun openProduct(product: Product) {
        val i = Intent(this, ProductDetailActivity::class.java)
        i.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
        i.putExtra(ProductDetailActivity.EXTRA_PRODUCT_NAME, product.name)
        i.putExtra(ProductDetailActivity.EXTRA_PRODUCT_DESC, product.description)
        i.putExtra(ProductDetailActivity.EXTRA_PRODUCT_PRICE, product.priceCents)
        startActivity(i)
    }
}
