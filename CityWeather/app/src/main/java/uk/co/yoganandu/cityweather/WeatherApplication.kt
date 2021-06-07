package uk.co.yoganandu.cityweather

import android.app.Application
import uk.co.yoganandu.cityweather.model.WeatherInfo

/**
 * Created by yoganandu on 04/06/2021.
 */
class WeatherApplication: Application() {

    private lateinit var weatherMap: HashMap<Int, WeatherInfo>
    private  var lud: Long = 0
    private lateinit var weatherList: List<WeatherInfo>

    override fun onCreate() {
        super.onCreate()
        weatherMap = HashMap<Int, WeatherInfo>()
    }
    private fun setLud(lud: Long){
        this.lud = lud
    }

    fun isWeatherDataExpired(): Boolean{
        return System.currentTimeMillis() - lud > 1800000
    }
    fun addWeatherList(weatherList: List<WeatherInfo>){
        this.weatherList = weatherList
        weatherMap.clear()
        for(weatherInfo in weatherList){
            weatherMap.put(weatherInfo.cityId, weatherInfo)
        }
        setLud(System.currentTimeMillis())
    }

    fun getWeatherDetails(cityId: Int): WeatherInfo?{
        return weatherMap.get(cityId)
    }

    fun getWeatherList(): List<WeatherInfo>{
        return weatherList
    }

    fun resetLud(){
        setLud(0)
    }
}