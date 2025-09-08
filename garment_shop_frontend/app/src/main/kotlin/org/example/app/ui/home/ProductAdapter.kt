package org.example.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.model.Product
import org.example.app.util.toCurrency

/**
 * RecyclerView adapter for product items in the home list.
 */
class ProductAdapter(
    private val onClick: (Product) -> Unit,
    private val onAddToCart: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    private val items = mutableListOf<Product>()

    fun submit(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.txtTitle)
        private val subtitle: TextView = view.findViewById(R.id.txtSubtitle)
        private val price: TextView = view.findViewById(R.id.txtPrice)
        private val btnAdd: Button = view.findViewById(R.id.btnAdd)

        fun bind(p: Product) {
            title.text = p.name
            subtitle.text = p.description
            price.text = p.priceCents.toCurrency()
            itemView.setOnClickListener { onClick(p) }
            btnAdd.setOnClickListener { onAddToCart(p) }
        }
    }
}
