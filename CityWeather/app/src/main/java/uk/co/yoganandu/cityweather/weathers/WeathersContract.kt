package uk.co.yoganandu.cityweather.weathers

import io.reactivex.Scheduler
import uk.co.yoganandu.cityweather.model.WeatherInfo

/**
 * Created by yoganandu on 04/06/2021.
 */
interface WeathersContract{

    interface View{
        fun showProgress()
        fun hideProgress()
        fun showWeatherList(weatherList: List<WeatherInfo>)
        fun showError(errorTitle : String, errorMessage : String)
    }

    interface Presenter{
        fun fetchWeathers(cityIds: List<String>, processScheduler: Scheduler, androidScheduler: Scheduler)
    }
}