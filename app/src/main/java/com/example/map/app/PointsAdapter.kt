package com.example.map.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.map.R
import com.example.map.data.PointModel
import com.example.map.databinding.PointItemBinding

class PointsAdapter() : RecyclerView.Adapter<PointsAdapter.ViewHolder>() {

    var points: List<PointModel> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = points.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(points[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PointItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    class ViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
        fun bind (item: PointModel) {
            itemView.findViewById<TextView>(R.id.title).text = "Точка ${item.id}"
            itemView.findViewById<TextView>(R.id.latitude).text = formatValue(item.latitude)
            itemView.findViewById<TextView>(R.id.longitude).text = formatValue(item.longitude)
        }
        private fun formatValue(value: Double) = String.format("%.4f", value) + "°"
    }
}
