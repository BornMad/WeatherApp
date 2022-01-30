package com.example.weatherapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog_city.*
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        //Toast.makeText(this,"Latitude: $lat && longitude: $long",Toast.LENGTH_LONG).show()
        window.statusBarColor = Color.parseColor("#1383C3")
        getJsonDataByLocation(lat,long)

    }
    private fun getJsonDataByLocation(lat:String?,long:String?){
        // Instantiate the RequestQueue.
        val API_KEY = "fdc1c657251344b85b423891332e9fa8"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=fdc1c657251344b85b423891332e9fa8"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener {
                Toast.makeText(this,"$lat && $long",Toast.LENGTH_LONG).show() })
        queue.add(jsonRequest)
        //MySingleton.getInstance(this).addToRequestQueue(jsonRequest)
    }

    private fun getJsonDataByCity(city:String?){
        // Instantiate the RequestQueue.
        val API_KEY = "fdc1c657251344b85b423891332e9fa8"
        val queue = Volley.newRequestQueue(this)
        val url = "api.openweathermap.org/data/2.5/weather?q=${city}&appid=fdc1c657251344b85b423891332e9fa8"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener {
                Toast.makeText(this,"$city",Toast.LENGTH_LONG).show() })
        queue.add(jsonRequest)
        //MySingleton.getInstance(this).addToRequestQueue(jsonRequest)
    }

    private fun setValues(response: JSONObject?) {
        city.text = response?.getString("name")
        var lat = response?.getJSONObject("coord")?.getString("lat")
        var long = response?.getJSONObject("coord")?.getString("lon")
        coordinates.text = "${lat},${long}"
        weather.text = response?.getJSONArray("weather")?.getJSONObject(0)?.getString("main")
        var tempr = response?.getJSONObject("main")?.getString("temp")
        tempr = (((((tempr)?.toFloat())?.minus(273.13)))?.toInt()).toString()
        temp.text = tempr+"°C"

        var mintemp = response?.getJSONObject("main")?.getString("temp_min")
        mintemp = ((((mintemp)?.toFloat()?.minus(273.15))?.toInt()).toString())
        min_temp.text = mintemp+"°C"

        var maxtemp = response?.getJSONObject("main")?.getString("temp_max")
        maxtemp = ((ceil((maxtemp)?.toFloat()?.minus(273.15)!!)?.toInt()).toString())
        max_temp.text = maxtemp+"°C"

        pressure.text = response?.getJSONObject("main")?.getString("pressure")
        humidity.text = response?.getJSONObject("main")?.getString("humidity") + "%"

        wind.text = response?.getJSONObject("wind")?.getString("speed")
        degree.text = "Degree : " + response?.getJSONObject("wind")?.getString("deg")+"°"
        //gust.text = "Gust : " +response?.getJSONObject("wind")?.getString("gust")
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu,menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miCity -> {
                Mydialog().show(supportFragmentManager, "MyCustomFragment")

            }
            R.id.miZip ->  Toast.makeText(this,"City wise",Toast.LENGTH_LONG).show()
        }
        return true
    }*/



}