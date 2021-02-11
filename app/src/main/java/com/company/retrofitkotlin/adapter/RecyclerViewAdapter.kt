package com.company.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.retrofitkotlin.R
import com.company.retrofitkotlin.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(model: CryptoModel)
    }
    private val colors: Array<String> = arrayOf("#82ac9f", "#82c09a", "#829298", "#2f6690", "#81c3d7")

    class RowHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(model: CryptoModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.text_name.text = model.currency
            itemView.text_price.text = model.price.toString()

            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]))

            itemView.setOnClickListener {
                listener.onItemClick(model)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList.get(position),colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}