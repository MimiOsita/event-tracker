package com.example.eventtracker.models

import java.sql.Time

data class EventModel (
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var time: String = "",
    var x: Double = 0.0,
    var y: Double = 0.0
)
