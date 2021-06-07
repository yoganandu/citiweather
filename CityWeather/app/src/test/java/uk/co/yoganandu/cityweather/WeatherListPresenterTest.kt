package uk.co.yoganandu.cityweather

import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import uk.co.yoganandu.cityweather.weathers.WeathersContract
import uk.co.yoganandu.cityweather.weathers.WeathersPresenter

/**
 * Created by yoganandu on 04/06/2021.
 */
class WeatherListPresenterTest{
    private val weatherListView: WeathersContract.View = Mockito.mock(WeathersContract.View::class.java)
    private val objectUnderTest = WeathersPresenter(weatherListView)

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
        objectUnderTest.fetchWeathers(listOf("12345"), testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherListView).showProgress()
        Mockito.verify(weatherListView).showError(any(),any())
        Mockito.verify(weatherListView).hideProgress()
        Mockito.verifyNoMoreInteractions(weatherListView)
    }
    @Test
    fun testShowListIsNotCalledForInValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeathers(listOf("12345"), testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherListView, Mockito.never()).showWeatherList(any())
    }
    @Test
    fun testFetchWeatherForValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeathers(listOf("2643743"), testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherListView).showProgress()
        Mockito.verify(weatherListView).showWeatherList(any())
        Mockito.verify(weatherListView).hideProgress()
        Mockito.verifyNoMoreInteractions(weatherListView)
    }
    @Test
    fun testShowErrorIsNotCalledForValidCityIds(){
        val testScheduler = TestScheduler()
        objectUnderTest.fetchWeathers(listOf("2643743"), testScheduler, testScheduler)
        testScheduler.triggerActions()
        Mockito.verify(weatherListView, Mockito.never()).showError(any(), any())
    }
}