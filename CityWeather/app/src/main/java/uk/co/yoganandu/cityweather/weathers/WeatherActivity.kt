package uk.co.yoganandu.cityweather.weathers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uk.co.yoganandu.cityweather.R
import uk.co.yoganandu.cityweather.WeatherApplication
import uk.co.yoganandu.cityweather.weatherdetail.WeatherFragment

/**
 * Created by yoganandu on 04/06/2021.
 */

class WeatherActivity : AppCompatActivity(), WeatherListFragment.WeatherCityClickListener {
    override fun showDetailedWeatherForCity(cityId: Int) {
        val ft = supportFragmentManager.beginTransaction()
        val weatherFragment = WeatherFragment()
        val bundle = Bundle()
        bundle.putInt("cityId", cityId)
        weatherFragment.arguments = bundle
        ft.replace(R.id.weatherFragment, weatherFragment)
        ft.addToBackStack("weatherDetails")
        ft.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        val weatherApplication = application as WeatherApplication
        weatherApplication.resetLud()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.weatherFragment, WeatherListFragment())
        ft.commit()
    }
}
