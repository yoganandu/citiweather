package uk.co.yoganandu.cityweather

import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import uk.co.yoganandu.cityweather.weatherdetail.WeatherDetailContract
import uk.co.yoganandu.cityweather.weatherdetail.WeatherDetailPresenter

/**
 * Created by yoganandu on 04/06/2021.
 */
class WeatherPresenterTest{
    private val weatherView: WeatherDetailContract.View = Mockito.mock(WeatherDetailContract.View::class.java)
    private val objectUnderTest = WeatherDetailPresenter(weatherView)

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    @Before
    fun setUP(){
        MockitoAnnotations.initMocks(this)

    }
    @Test
    fun testFetchWeatherForInValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeather(12345, testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherView).showProgress()
        Mockito.verify(weatherView).showError(any(),any())
        Mockito.verify(weatherView).hideProgress()
        Mockito.verifyNoMoreInteractions(weatherView)
    }
    @Test
    fun testShowListIsNotCalledForInValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeather(12345, testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherView, Mockito.never()).showWeather(any())
    }
    @Test
    fun testFetchWeatherForValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeather(2643743, testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherView).showProgress()
        Mockito.verify(weatherView).showWeather(any())
        Mockito.verify(weatherView).hideProgress()
        Mockito.verifyNoMoreInteractions(weatherView)
    }
    @Test
    fun testShowErrorIsNotCalledForValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeather(2643743, testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherView, Mockito.never()).showError(any(), any())
    }
}