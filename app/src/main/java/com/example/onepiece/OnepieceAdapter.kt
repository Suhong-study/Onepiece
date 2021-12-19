package com.example.onepiece

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.NetworkImageView

class OnepieceAdapter(
    private val model: OnepieceViewModel,
    private val onClick: (OnepieceViewModel.Person) -> Unit
): RecyclerView.Adapter<OnepieceAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txName: TextView = itemView.findViewById(R.id.text1)
        val txPirate: TextView = itemView.findViewById(R.id.text2)
        val niImage: NetworkImageView = itemView.findViewById(R.id.image)
        init{
            niImage.setDefaultImageResId(android.R.drawable.ic_menu_report_image)
            itemView.setOnClickListener{
                onClick(model.getPerson(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txName.text = model.getPerson(position).name
        holder.txPirate.text = model.getPerson(position).pirate
        holder.niImage.setImageUrl(model.getImageUrl(position), model.imageLoader)


    }
    override fun getItemCount() = model.getSize()
}