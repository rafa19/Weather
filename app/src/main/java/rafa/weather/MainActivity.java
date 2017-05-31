package rafa.weather;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoslab.openweatherretrieverz.CurrentWeatherInfo;
import com.ftoslab.openweatherretrieverz.OpenWeatherRetrieverZ;
import com.ftoslab.openweatherretrieverz.WeatherCallback;
import com.ftoslab.openweatherretrieverz.WeatherUnitConverter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafa.weather.utils.AlarmReceiver;
import rafa.weather.utils.Database;
import rafa.weather.utils.Other;
import rafa.weather.utils.Weather;
import rafa.weather.utils.WeatherCall;

public class MainActivity extends AppCompatActivity {

    private final int REQUESTCITYCHOOSER = 101;
    private final int INTERVAL_IN_MILLIS = 1800000;//30 minutes
    CurrentWeatherInfo currentWeatherInfoC;
    Database database;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtCityName)
    TextView txtCityName;
    @BindView(R.id.txtTemperature)
    TextView txtTemperature;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.txtTemperatureHigh)
    TextView txtTemperatureHigh;
    @BindView(R.id.txtDateTime)
    TextView txtDateTime;
    @BindView(R.id.txtTemperatureLow)
    TextView txtTemperatureLow;
    @BindView(R.id.txtHumidity)
    TextView txtHumidity;
    @BindView(R.id.txtPressure)
    TextView txtPressure;
    @BindView(R.id.txtWind)
    TextView txtWind;
    @BindView(R.id.imgCondition)
    ImageView imgCondition;
    @BindView(R.id.btnSetting)
    ImageView btnSetting;


    private String cityID = Other.CITY_IDS[0];
    private String cityName = Other.CITY_NAMES[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("");

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CityChooserActivity.class);
                startActivityForResult(intent, REQUESTCITYCHOOSER);
            }
        });

        Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), INTERVAL_IN_MILLIS, pendingIntent);
        }

        database = new Database(getApplicationContext());
        Weather weather = database.getWeather(cityID);
        if (weather != null) {
            updateView(weather);
        }

        if (Other.isOnline(getApplicationContext())) {
            requestWeather();
            if (database.isFirstTime()) {
                WeatherCall.getData(getApplicationContext());
            }
        }
    }

    private void requestWeather() {
        OpenWeatherRetrieverZ retriever = new OpenWeatherRetrieverZ(Other.API_KEY);
        retriever.updateCurrentWeatherInfo(cityID, new WeatherCallback() {
            @Override
            public void onReceiveWeatherInfo(CurrentWeatherInfo currentWeatherInfo) {
                // Switch the weather unit to Metric
                currentWeatherInfoC = WeatherUnitConverter.convertToMetric(currentWeatherInfo);
                database.updateCurrentWeather(currentWeatherInfoC);
                Weather weather = database.getWeather(cityID);
                if (weather != null) {
                    updateView(weather);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("error", error);
            }
        });
    }

    private void updateView(Weather weather) {
        txtTemperature.setText(weather.getTemperature());
        txtCityName.setText(cityName);
        txtTemperatureHigh.setText(weather.getTemperatureHigh());
        txtTemperatureLow.setText(weather.getTemperatureLow());
        txtDescription.setText(weather.getDescription());
        txtDateTime.setText(weather.getDateTime());
        txtHumidity.setText(weather.getHumidity());
        txtPressure.setText(weather.getPressure());
        txtWind.setText(weather.getWind());
        Picasso.with(getApplicationContext())
                .load(weather.getIconLink())
                .placeholder(R.drawable.weather)
                .error(R.drawable.weather)
                .into(imgCondition);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCITYCHOOSER) {
            if (resultCode == Activity.RESULT_OK) {
                cityID = data.getStringExtra("cityID");
                cityName = data.getStringExtra("cityName");
                Weather weather = database.getWeather(cityID);
                if (weather != null) {
                    updateView(weather);
                }
                if (Other.isOnline(getApplicationContext())) {
                    requestWeather();
                }

            }
        }
    }

}
