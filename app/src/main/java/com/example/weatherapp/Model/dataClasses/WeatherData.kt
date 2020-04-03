package com.example.weatherapp.Model.dataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * this class will be used in UI to show weather data
 */
@Entity(tableName = "weather_data")
data class WeatherData(
        @ColumnInfo(name = "date_Time")
        var dateTime: String = "",
        @ColumnInfo(name ="temperature")
        var temperature: String = "0",
        @PrimaryKey
        var cityAndCountry: String = "",
        @ColumnInfo(name="humidity")
        var humidity: String = "",
        @ColumnInfo(name = "pressure")
        var pressure: String = "",
        @ColumnInfo(name = "visibility")
        var visibility: String = ""

){
//      @PrimaryKey(autoGenerate = true)
//      var uuid:Int = 0
}