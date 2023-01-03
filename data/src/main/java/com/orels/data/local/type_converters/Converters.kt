package com.orels.data.local.type_converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.orels.domain.model.entities.CalendarEvents
import com.orels.domain.model.entities.DefaultKeyStatistics
import com.orels.domain.model.entities.FinancialData
import com.orels.domain.model.entities.SummaryDetails

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun convertDefaultKeyStatisticsToString(defaultKeyStatistics: DefaultKeyStatistics): String =
        Gson().toJson(defaultKeyStatistics)

    @TypeConverter
    fun convertStringToDefaultKeyStatistics(value: String): DefaultKeyStatistics =
        Gson().fromJson(value, DefaultKeyStatistics::class.java)

    @TypeConverter
    fun convertFinancialDataToString(financialData: FinancialData): String =
        Gson().toJson(financialData)

    @TypeConverter
    fun convertStringToFinancialData(value: String): FinancialData =
        Gson().fromJson(value, FinancialData::class.java)

    @TypeConverter
    fun convertSummaryDetailsToString(summaryDetails: SummaryDetails): String =
        Gson().toJson(summaryDetails)

    @TypeConverter
    fun convertStringToSummaryDetails(value: String): SummaryDetails =
        Gson().fromJson(value, SummaryDetails::class.java)

    @TypeConverter
    fun convertCalendarEventsToString(calendarEvents: CalendarEvents): String =
        Gson().toJson(calendarEvents)

    @TypeConverter
    fun convertStringToCalendarEvents(value: String): CalendarEvents =
        Gson().fromJson(value, CalendarEvents::class.java)
}