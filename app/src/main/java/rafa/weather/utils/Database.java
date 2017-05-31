package rafa.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ftoslab.openweatherretrieverz.CurrentWeatherInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rafiz_q on 30.05.2017.
 */

public class Database {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "WeatherPref";
    private static final String PREF_WEATHER = "Weather_";
    private static final String PREF_NAME_FIRST_TIME = "FirstTime";

    public Database(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void insertWeather(Weather data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(PREF_WEATHER + data.getCityID(), json);
        editor.commit();
    }

    public Weather getWeather(String cityID) {
        Gson gson = new Gson();
        String json = pref.getString(PREF_WEATHER + cityID, "");
        Type classType = new TypeToken<Weather>() {
        }.getType();
        return gson.fromJson(json, classType);
    }

    public void updateCurrentWeather(CurrentWeatherInfo currentWeatherInfo) {

        int intMinTemp = (int) Double.parseDouble(currentWeatherInfo.getCurrentTemperatureMin());
        int intMaxTemp = (int) Double.parseDouble(currentWeatherInfo.getCurrentTemperatureMax());
        String strMinTemp = intMinTemp + "° ";
        String strMaxTemp = intMaxTemp + "° ";
        String windSpeed = currentWeatherInfo.getWindSpeed();
        int windIndex = windSpeed.indexOf(".");
        if (windIndex > 0) {
            windSpeed = windSpeed.substring(0, windIndex);
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        //currentWeatherInfo.get
        Weather weather = new Weather(strMinTemp, strMaxTemp, currentWeatherInfo.getCityName(), (int) Double.parseDouble(currentWeatherInfo.getCurrentTemperature()) + "°",
                currentWeatherInfo.getWeatherDescriptionLong().toUpperCase(), currentWeatherInfo.getWeatherIconLink(), datetime, currentWeatherInfo.getCityId(),
                windSpeed, currentWeatherInfo.getHumidity(), currentWeatherInfo.getPressure());
        insertWeather(weather);
    }


    public void firstTime() {
        editor.putBoolean(PREF_NAME_FIRST_TIME, false);
        editor.commit();
    }

    public boolean isFirstTime() {
        if (pref.getBoolean(PREF_NAME_FIRST_TIME, true)) {
            firstTime();
            return true;
        } else {
            return false;
        }
    }


}
