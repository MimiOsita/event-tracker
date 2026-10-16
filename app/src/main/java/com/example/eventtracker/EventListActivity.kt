package com.example.eventtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eventtracker.main.deleteEvent
import com.example.eventtracker.models.EventModel

class EventListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mark_list)

        recyclerView = findViewById(R.id.marksRecyclerView)

        adapter = EventAdapter(
            events = AppData.events.findAll(),
            onEdit = { event -> editEvent(event) },
            onDelete = { event -> deleteEvent(event) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.updateEvents(AppData.events.findAll())
        }
    }

    private fun editEvent(event: EventModel) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", event.id)
        startActivity(intent)
    }

    private fun deleteEvent(event: EventModel) {
        AppData.events.delete(event.id)
        adapter.updateEvents(AppData.events.findAll())

    }
}