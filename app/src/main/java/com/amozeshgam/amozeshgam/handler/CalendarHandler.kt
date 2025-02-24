package com.amozeshgam.amozeshgam.handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarHandler @Inject constructor(@ApplicationContext private val context: Context) {
    fun insertEvent(title: String, description: String, beginTime: Long, endTime: Long): Boolean {
        val calender = getPrimaryCalendar()
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, beginTime)
            put(CalendarContract.Events.DTEND, endTime)
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.DESCRIPTION, description)
            put(
                CalendarContract.Events.CALENDAR_ID, calender
            )
        }
        val uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        val eventId = uri?.lastPathSegment?.toLongOrNull()
        return eventId != null

    }

    private fun getPrimaryCalendar(): Long {
        val projection = arrayOf(CalendarContract.Calendars._ID)
        val selection =
            CalendarContract.Calendars.VISIBLE + " = 1" + "AND " + CalendarContract.Calendars.IS_PRIMARY + " = 1"
        val cursor: Cursor? = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            selection,
            null,
            null
        )
        var calendarId: Long = -1
        cursor?.use {
            if (it.moveToFirst()) {
                calendarId =
                    cursor.getLong(it.getColumnIndex(CalendarContract.Calendars._ID) ?: -1)
            }
        }
        return calendarId
    }
}