package uk.co.yoganandu.cityweather.weatherdetail

import io.reactivex.Scheduler
import uk.co.yoganandu.cityweather.api.WeatherAPICallBack
import uk.co.yoganandu.cityweather.data.WeatherAPIRepository
import uk.co.yoganandu.cityweather.model.WeatherAPIError
import uk.co.yoganandu.cityweather.model.WeatherInfo

/**
 * Created by yoganandu on 04/06/2021.
 */
class WeatherDetailPresenter(val weatherView: WeatherDetailContract.View): WeatherDetailContract.Presenter{
    override fun fetchWeather(cityId: Int, processScheduler: Scheduler, androidScheduler: Scheduler) {
        weatherView.showProgress()
        val weatherRepository = WeatherAPIRepository()
        weatherRepository.fetchWeather(cityId, object: WeatherAPICallBack.WeatherCallBack {
            override fun onSuccess(weatherInfo: WeatherInfo) {
                weatherView.hideProgress()
                weatherView.showWeather(weatherInfo)
            }

            override fun onError(weatherError: WeatherAPIError) {
                weatherView.hideProgress()
                weatherView.showError(weatherError.errorCode.toString(), weatherError.errorMessage)
            }

        }, processScheduler, androidScheduler)
    }

}