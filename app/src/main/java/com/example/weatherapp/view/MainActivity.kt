package com.example.weatherapp.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.Model.dataClasses.WeatherData
import com.example.weatherapp.Model.dataClasses.WeatherInfoResponse
import com.example.weatherapp.R
import com.example.weatherapp.viewModel.MainActivityViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*
import java.util.*

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private val PERMISSION_ID = 1
    private lateinit var locationManager: LocationManager
    private lateinit var viewModel: MainActivityViewModel
    private var cityName: String = "unknown"
    private var googleApiClient: GoogleApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initLocation()
        initPermission()
        progressBar.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        if (googleApiClient != null) {
            googleApiClient!!.connect()
        }
    }

    override fun onStop() {
        googleApiClient?.disconnect()
        super.onStop()
    }

    override fun onConnected(p0: Bundle?) {
        if (isLocationPermissionGranted()) {
            val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            val lat = lastLocation.latitude
            val lon = lastLocation.longitude
            cityName = getCityName(lat, lon)
            Log.d(">>>>>", cityName)
            viewModel!!.getWeatherData(cityName).observe(this, Observer {
                progressBar.visibility = View.GONE
                if (it != null) {
                    tv_temperature?.text = it.temperature
                    tv_city_country?.text = it.cityAndCountry
                    tv_humidity_value?.text = it.humidity
                    tv_pressure_value?.text = it.pressure
                    tv_visibility_value?.text = it.visibility
                }
            })

            viewModel?.errorLiveData.observe(this, Observer {
                progressBar.visibility = View.GONE
                if(it.message.isNotEmpty()){
                    Log.d("MainActivityViewModel","${it.message}");
                    Toast.makeText(this,"${it.message}", Toast.LENGTH_LONG).show()
                    textViewStatus.text = it.message
                }
            })
        }

    }

    private fun showRationalPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessage)
        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            requestPermission()
        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            showSnackBar()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult?) {
        Log.i(
            MainActivity::class.java.simpleName,
            "Can't connect to Google Play Services!"
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
                getLocation()
            } else {
                if(shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION)){
                    showRationalPermissionDialog()
                }else{
                    Toast.makeText(this, "This App requires location permission for fetching weather data." +
                            "Please go to your app settings and allow!", Toast.LENGTH_SHORT).show();
                    progressBar.visibility = View.GONE
                }
            }
        }

    }

    private fun initLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private fun initPermission(){
        if (isLocationPermissionGranted()) {
            getLocation();
        }else{
            if(shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                showRationalPermissionDialog()
            }else {
                requestPermission()
            }
        }

    }

    private fun getLocation(){
        googleApiClient =
            GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSION_ID
        )
    }
    fun showSnackBar(){
//        val snackbar = SnackBar.make(
//            mainLayout, com.example.weatherapp.R.string.msg_no_camera_permission,
//            Snackbar.LENGTH_INDEFINITE
//        )
//        snackbar.setAction(com.example.weatherapp.R.string.ok, View.OnClickListener { openAppSettings() })
//        snackbar.show()
        Toast.makeText(this,"Couldn't fetch the weather info", Toast.LENGTH_LONG).show()
    }

    private fun isLocationPermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true

        }
        return false
    }


    private fun getCityName(MyLat: Double, MyLong: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(MyLat, MyLong, 1)
        val cityName = addresses[0].locality
        return cityName
    }
}
