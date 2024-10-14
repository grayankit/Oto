package com.bheshnarayan.oto

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class SongsAdapter(private val songlist: Array<File>, private val context: Context) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = songlist[position].name
        holder.itemView.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            try {
                mediaPlayer.setDataSource(songlist[position].path)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }catch (e: Exception){
                e.printStackTrace()

            }
        }
    }
    override fun getItemCount(): Int {
        return songlist.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }
}