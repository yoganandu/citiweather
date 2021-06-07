package uk.co.yoganandu.cityweather.weatherdetail

import io.reactivex.Scheduler
import uk.co.yoganandu.cityweather.model.WeatherInfo

/**
 * Created by yoganandu on 04/06/2021.
 */
interface WeatherDetailContract{

    interface View{
        fun showProgress()
        fun hideProgress()
        fun showWeather(weather: WeatherInfo)
        fun showError(errorTitle : String, errorMessage : String)
    }

    interface Presenter{
        fun fetchWeather(cityId: Int, processScheduler: Scheduler, androidScheduler: Scheduler)
    }
}