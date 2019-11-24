package com.rajkumarrajan.latesttechexample.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel
import com.rajkumarrajan.latesttechexample.R

class AlbumsDetailsAdapter(
    var context: Context,
    var listAlbumsDetails: ArrayList<MyModel>,
    var albumsDetailsInterface: AlbumsDetailsInterface
) : RecyclerView.Adapter<AlbumsDetailsAdapter.ViewHolder>() {

    interface AlbumsDetailsInterface {
        fun click(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_details_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listAlbumsDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.TextViewTitle.text = listAlbumsDetails[holder.adapterPosition].title

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TextViewTitle: TextView = itemView.findViewById(R.id.TextViewTitle)
    }
}
