package com.example.eventtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventtracker.models.EventModel

class EventAdapter (

    private var events: List<EventModel>,
    private val onEdit: (EventModel) -> Unit,
    private val onDelete: (EventModel) -> Unit

    ) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
        val coordinatesText: TextView = itemView.findViewById(R.id.coordinatesText)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_placed_mark, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.titleText.text = "${event.id}: ${event.title}"
        holder.descriptionText.text = event.description
        holder.coordinatesText.text = "X: ${event.x}, Y: ${event.y}"

        holder.editButton.setOnClickListener {
            onEdit(event)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun updateEvents(newEvents: List<EventModel>) {
        events = newEvents
        notifyDataSetChanged()
    }

}