package uk.co.yoganandu.cityweather.data

import io.reactivex.Scheduler
import uk.co.yoganandu.cityweather.api.WeatherAPICallBack

/**
 * Created by yoganandu on 04/06/2021.
 */
interface WeatherRepository{

    fun fetchWeatherList(cityIds: String,
                         weathersListCallBack: WeatherAPICallBack.WeathersListCallBack,
                         processSecheduler: Scheduler, androidSchedulers: Scheduler)
    fun fetchWeather(cityId: Int, weatherInfoCallBack: WeatherAPICallBack.WeatherCallBack,
                     processSecheduler: Scheduler, androidSchedulers: Scheduler)
}