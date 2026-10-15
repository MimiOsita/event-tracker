package com.example.eventtracker.main

import com.example.eventtracker.models.EventModel
import com.example.eventtracker.models.EventMemStore

val store = EventMemStore()

fun main() {
    println("=== Event Tracker ===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addEvent()
            2 -> listEvents()
            3 -> updateEvent()
            4 -> deleteEvent()
            5 -> searchEvent()
            0 -> println("\nExiting Event Tracker!")
            else -> println("\nInvalid option. Please try again")

            }

        } while (input != 0)
    }

fun menu(): Int {
    println("\n----------------")
    println(" MAIN MENU ")
    println("\n----------------")
    println("1. Add an Event")
    println("2. List all Events")
    println("3. Update an Event")
    println("4. Delete an Event")
    println("5. Search Event by ID")
    println("0. Exit")
    print("\nEnter Option: ")

    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addEvent() {
    println("\n--- Add an Event ---")
    println("Enter Name of Event: ")
    val title = readlnOrNull()?.trim().orEmpty()

    print("Describe the Event: ")
    val description = readlnOrNull()?.trim().orEmpty()

    if (title.isNotEmpty()) {
        val event = EventModel(title = title, description = description)
        store.create(event)
        println("Event has been added successfully with an ID of: ${event.id}")
    } else {
        println("Event name cannot be empty. Creation cancelled.")
    }
}

fun listEvents() {
    println("\n--- All Events ---")
    val events = store.findAll()
    if (events.isEmpty()) {
        println("No events stored yet.")
    } else {
        events.forEach { println("ID: ${it.id} | Name: ${it.title} | Description: ${it.description}") }
    }
}

fun updateEvent() {
    println("\n--- Update Event ---")
    listEvents()
    if (store.findAll().isEmpty())
        return

    print("\nEnter ID of an Event to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Event Name: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter Description of Event: ")
        val description = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val updated = store.update(EventModel(id = id, title = title, description = description))
            if (updated)
                println("Event has been updated successfully.")
        } else {
            println("Name cannot be empty. Update cancelled.")
        }
    } else {
        println("Event with ID $id not found.")
    }
}

fun deleteEvent() {
    println("\n--- Delete Event ---")
    listEvents()
    if (store.findAll().isEmpty())
        return

    print("\nEnter ID of event to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Event with ID $id has been deleted successfully.")
        } else {
            println("Event with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchEvent() {
    println("\n--- Search Event ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val event = store.findOne(id)
        if (event != null) {
            println("Found ID: ${event.id} | Name: ${event.title} | Description: ${event.description}")
        } else {
            println("No event has been found with ID: $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}
