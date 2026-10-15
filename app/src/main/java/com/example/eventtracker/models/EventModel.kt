package com.example.eventtracker.models

data class EventModel (
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var x: Double = 0.0,
    var y: Double = 0.0
)
