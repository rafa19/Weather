package rafa.weather.utils;

/**
 * Created by rafiz_q on 30.05.2017.
 */

public class Weather {
    private String TemperatureHigh;
    private String TemperatureLow;
    private String CityName;
    private String Temperature;
    private String Description;
    private String IconLink;
    private String DateTime;
    private String CityID;
    private String Wind;
    private String Humidity;
    private String Pressure;

    public Weather(String temperatureHigh, String temperatureLow, String cityName, String temperature, String description, String iconLink, String dateTime, String cityID, String wind, String humidity, String pressure) {
        TemperatureHigh = temperatureHigh;
        TemperatureLow = temperatureLow;
        CityName = cityName;
        Temperature = temperature;
        Description = description;
        IconLink = iconLink;
        DateTime = dateTime;
        CityID = cityID;
        Wind = wind;
        Humidity = humidity;
        Pressure = pressure;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getTemperatureHigh() {
        return TemperatureHigh;
    }

    public void setTemperatureHigh(String temperatureHigh) {
        TemperatureHigh = temperatureHigh;
    }

    public String getTemperatureLow() {
        return TemperatureLow;
    }

    public void setTemperatureLow(String temperatureLow) {
        TemperatureLow = temperatureLow;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIconLink() {
        return IconLink;
    }

    public void setIconLink(String iconLink) {
        IconLink = iconLink;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }
}