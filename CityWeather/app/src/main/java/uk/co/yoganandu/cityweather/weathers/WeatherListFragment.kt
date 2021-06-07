package uk.co.yoganandu.cityweather.weathers

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import uk.co.yoganandu.cityweather.WeatherApplication
import uk.co.yoganandu.cityweather.WeatherBaseFragment
import uk.co.yoganandu.cityweather.databinding.FragmentWeatherListBinding
import uk.co.yoganandu.cityweather.model.WeatherInfo

/**
 * Created by yoganandu on 04/06/2021.
 */

class WeatherListFragment : WeatherBaseFragment(), WeathersContract.View {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListener: WeatherCityClickListener

    private lateinit var mWeatherListPresenter: WeathersPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lvWeatherList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lvWeatherList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val weatherApplication: WeatherApplication = activity?.application as WeatherApplication
        val cityList = listOf("2643743", "3333184", "2653822", "2655603", "2640729", "2654710", "3333231", "2644688", "2644210", "2653941")
        if(weatherApplication.isWeatherDataExpired()){
            mWeatherListPresenter = WeathersPresenter(this)
            mWeatherListPresenter.fetchWeathers(cityList, Schedulers.io(), AndroidSchedulers.mainThread())
        }else{
            showWeatherList(weatherApplication.getWeatherList())
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is WeatherCityClickListener) {
            mListener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement WeatherCityClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface WeatherCityClickListener {
        // TODO: Update argument type and name
        fun showDetailedWeatherForCity(cityId: Int)
    }
    override fun showWeatherList(weatherList: List<WeatherInfo>) {
        val weatherApplication: WeatherApplication = activity?.application as WeatherApplication
        weatherApplication.addWeatherList(weatherList)
        val weatherListAdapter = WeatherListAdapter(weatherList, requireActivity(), mListener)
        binding.lvWeatherList.adapter = weatherListAdapter
        weatherListAdapter.notifyDataSetChanged()
    }

    override fun showError(errorTitle: String, errorMessage: String) {
        binding.tvErrorMessage.visibility= View.VISIBLE
        binding.lvWeatherList.visibility = View.GONE
        binding.tvErrorMessage.text = errorMessage
    }

}
