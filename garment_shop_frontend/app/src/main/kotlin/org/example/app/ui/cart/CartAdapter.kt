package org.example.app.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.model.CartItem
import org.example.app.util.toCurrency

/**
 * Adapter for displaying cart items with controls.
 */
class CartAdapter(
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onRemove: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    private val items = mutableListOf<CartItem>()

    fun submit(newItems: List<CartItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.txtTitle)
        private val price: TextView = view.findViewById(R.id.txtPrice)
        private val qty: TextView = view.findViewById(R.id.txtQty)
        private val btnInc: ImageButton = view.findViewById(R.id.btnIncrease)
        private val btnDec: ImageButton = view.findViewById(R.id.btnDecrease)
        private val btnRemove: Button = view.findViewById(R.id.btnRemove)

        fun bind(item: CartItem) {
            title.text = item.product.name
            price.text = item.lineTotalCents.toCurrency()
            qty.text = item.quantity.toString()

            btnInc.setOnClickListener { onIncrease(item) }
            btnDec.setOnClickListener { onDecrease(item) }
            btnRemove.setOnClickListener { onRemove(item) }
        }
    }
}
