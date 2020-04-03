package com.example.weatherapp.Model.dataClasses
import com.google.gson.annotations.SerializedName

//data class WeatherInfoResponse(
//    @SerializedName("coord")
//        val coord: Coord = Coord(),
//    @SerializedName("weather")
//        val weather: List<Weather> = listOf(),
//    @SerializedName("base")
//        val base: String = "",
//    @SerializedName("main")
//        val main: Main = Main(),
//    @SerializedName("visibility")
//        val visibility: Int = 0,
//    @SerializedName("wind")
//        val wind: Wind = Wind(),
//    @SerializedName("clouds")
//        val clouds: Clouds = Clouds(),
//    @SerializedName("dt")
//        val dt: Int = 0,
//    @SerializedName("sys")
//        val sys: Sys = Sys(),
//    @SerializedName("id")
//        val id: Int = 0,
//    @SerializedName("name")
//        val name: String = "",
//    @SerializedName("cod")
//        val cod: Int = 0
//)
data class WeatherInfoResponse( val coord: Coord,
                                val weather: List<Weather>,
                                val base: String,
                                val main: Main,
                                val visibility: Int,
                                val wind: Wind,
                                val clouds: Clouds,
                                val dt: Int,
                                val sys: Sys,
                                val id: Int,
                                val name: String,
                                val cod: Int

){

}