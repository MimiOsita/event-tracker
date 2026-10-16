package com.example.eventtracker.models

import java.util.concurrent.atomic.AtomicLong

class EventMemStore {

    private val events = ArrayList<EventModel>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<EventModel> {
        return events
    }

    fun create(event: EventModel) {
        event.id = lastId.incrementAndGet()
        events.add(event)
    }

    fun update(event: EventModel): Boolean {
        val foundEvent = findOne(event.id)
        return if (foundEvent != null) {
            foundEvent.title = event.title
            foundEvent.description = event.description
            foundEvent.date = event.date
            foundEvent.time = event.time
            foundEvent.x = event.x
            foundEvent.y = event.y

            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundEvent = findOne(id)
        return if (foundEvent != null) {
            events.remove(foundEvent)

            true
        } else {
            false
        }
    }

    fun findOne(id: Long): EventModel? {
        return events.find { e -> e.id == id }
    }
}