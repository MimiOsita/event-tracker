package com.example.eventtracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventtracker.models.EventModel


class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var xInput: EditText
    private lateinit var yInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_edit)

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        dateInput = findViewById(R.id.dateInput)
        timeInput = findViewById(R.id.timeInput)
        xInput = findViewById(R.id.xInput)
        yInput = findViewById(R.id.yInput)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        editingId = intent.getLongExtra("id", -1L)
            .takeIf { it != -1L }

        if (editingId != null) {
            findViewById<TextView>(R.id.formTitle).text = "Edit Event"
            loadExistingEvent(editingId!!)
        }

        saveButton.setOnClickListener {
            saveEvent()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadExistingEvent(id: Long) {
        val event = AppData.events.findOne(id)

        if (event == null) {
            Toast.makeText(
                this,
                "Event not found",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        titleInput.setText(event.title)
        descriptionInput.setText(event.description)
        dateInput.setText(event.date)
        timeInput.setText(event.time)
        xInput.setText(event.x.toString())
        yInput.setText(event.y.toString())
    }

    private fun saveEvent() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val date = dateInput.text.toString().trim()
        val time = timeInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Name is required"
            return
        }

        val x = xInput.text.toString().toDoubleOrNull()
        if (x == null) {
            xInput.error = "Enter a valid number"
            return
        }

        val y = yInput.text.toString().toDoubleOrNull()
        if (y == null) {
            yInput.error = "Enter a valid number"
            return
        }

        if (editingId == null) {
            val event = EventModel(
                title = title,
                description = description,
                date = date,
                time = time,
                x = x,
                y = y
            )

            AppData.events.create(event)
            Toast.makeText(
                this,
                "Event created",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val event = EventModel(
                id = editingId!!,
                title = title,
                description = description,
                date = date,
                time = time,
                x = x,
                y = y
            )

            AppData.events.update(event)
            Toast.makeText(
                this,
                "Event updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}