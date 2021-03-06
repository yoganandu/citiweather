package uk.co.yoganandu.cityweather.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.yoganandu.cityweather.model.WeatherAPIResponse
import uk.co.yoganandu.cityweather.model.WeatherInfo

/**
 * Created by yoganandu on 04/06/2021.
 */
interface OpenWeatherAPIService{

    @GET("group?")
    fun getCurrentWeatherForSeveralCities(@Query("APPID") apiKey: String,
                                          @Query("id") ids : String,
                                          @Query("units") units : String): Observable<WeatherAPIResponse>

    @GET("weather?")
    fun getCurrentWeatherForCityById(@Query("APPID") apiKey: String,
                                     @Query("id") cityId: Int,
                                     @Query("units") units: String): Observable<WeatherInfo>

    /**
     * Companion object to create the OpenWeatherAPIService
     */
    companion object Factory {
        fun create(loggingInterceptor: HttpLoggingInterceptor): OpenWeatherAPIService {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(loggingInterceptor)
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(WeatherAPIConstants.BASE_URL)
                    .client(okHttpClient.build())
                    .build()

            return retrofit.create(OpenWeatherAPIService::class.java);
        }
        fun create(): OpenWeatherAPIService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return create(httpLoggingInterceptor)
        }
    }
}
