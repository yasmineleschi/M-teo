package iset.dsi.meteo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherapi {
    @GET("weather")
    Call<Exemple> getweather(@Query("q") String cityname,
                             @Query("cnt") int count,
                             @Query("appid") String apikey);
}
