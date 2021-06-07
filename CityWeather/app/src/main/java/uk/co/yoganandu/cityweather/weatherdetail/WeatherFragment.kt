package uk.co.yoganandu.cityweather.weatherdetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import uk.co.yoganandu.cityweather.R
import uk.co.yoganandu.cityweather.WeatherApplication
import uk.co.yoganandu.cityweather.WeatherBaseFragment
import uk.co.yoganandu.cityweather.databinding.FragmentWeatherBinding
import uk.co.yoganandu.cityweather.model.WeatherInfo
import uk.co.yoganandu.cityweather.utils.WeatherUtils


/**
 * Created by yoganandu on 04/06/2021.
 */
class WeatherFragment : WeatherBaseFragment(), WeatherDetailContract.View {


    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!

    private lateinit var weatherDetailPresenter: WeatherDetailPresenter
    private  var cityId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        cityId = arguments?.getInt("cityId") ?: 0
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherApplication: WeatherApplication = activity?.application as WeatherApplication
        if(weatherApplication.getWeatherDetails(cityId) == null){
            weatherDetailPresenter = WeatherDetailPresenter(this)
            weatherDetailPresenter.fetchWeather(cityId, Schedulers.io(), AndroidSchedulers.mainThread())
        }else{
            showWeather(weatherApplication.getWeatherDetails(cityId)!!)
        }
    }

    override fun showWeather(weather: WeatherInfo) {
        binding.tvcityName.text = weather.cityName + ", " + weather.sys.country
        val weatherResourceStr = context?.getString(WeatherUtils.getWeatherImageResourceFromWeatherCode(weather.weather[0].icon))
        binding.weatherDetailIconIV.setIconResource(weatherResourceStr)
        binding.currentTempDetailTV.text = Math.round(weather.main.temp).toString() + " \u2103"
        binding.weatherConditionDetailTV.text = weather.weather[0].main
        binding.tvWindDetails.text = getString(R.string.wind_speed_text, weather.wind.speed.toString(), weather.wind.deg.toString())
        binding.tvPressureDetails.text = getString(R.string.pressure_speed_text, weather.main.pressure.toString())
        binding.tvHumidityDetails.text = getString(R.string.humidity_text, weather.main.humidity.toString()) + " %"
        binding.tvSunriseDetails.text = getString(R.string.sunrise_text, WeatherUtils.getFormatedTimeFromUTC(weather.sys.sunrise))
        binding.tvSunsetDetails.text = getString(R.string.sunrise_text, WeatherUtils.getFormatedTimeFromUTC(weather.sys.sunset))
    }

    override fun showError(errorTitle: String, errorMessage: String) {
        binding.llWeatherDetails.visibility = View.GONE
        binding.tvDetailErrorMessage.visibility = View.VISIBLE
        binding.tvDetailErrorMessage.text = errorMessage
    }


}// Required empty public constructor
