package iset.dsi.meteo;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Exemple {
    @SerializedName("main")
    Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("list")
    private List<ForecastItem> forecastItemList;

    public List<ForecastItem> getForecastItemList() {
        return forecastItemList;
    }

    public void setForecastItemList(List<ForecastItem> forecastItem) {
        this.forecastItemList = forecastItemList;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
