package rafa.weather.utils;

import android.content.Context;
import android.util.Log;

import com.ftoslab.openweatherretrieverz.CurrentWeatherInfo;
import com.ftoslab.openweatherretrieverz.OpenWeatherRetrieverZ;
import com.ftoslab.openweatherretrieverz.WeatherCallback;
import com.ftoslab.openweatherretrieverz.WeatherUnitConverter;

/**
 * Created by rafiz_q on 30.05.2017.
 */

public class WeatherCall {

    public static void getData(final Context context) {
        OpenWeatherRetrieverZ retriever = new OpenWeatherRetrieverZ(Other.API_KEY);
        for (final String cityID : Other.CITY_IDS) {
            retriever.updateCurrentWeatherInfo(cityID, new WeatherCallback() {
                @Override
                public void onReceiveWeatherInfo(CurrentWeatherInfo currentWeatherInfo) {
                    Database database = new Database(context);
                    CurrentWeatherInfo currentWeatherInfoC = WeatherUnitConverter.convertToMetric(currentWeatherInfo);
                    database.updateCurrentWeather(currentWeatherInfoC);

                }

                @Override
                public void onFailure(String error) {
                    Log.e("error", error);
                }
            });
        }
    }
}
