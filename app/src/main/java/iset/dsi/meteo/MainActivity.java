package iset.dsi.meteo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView tt, humidity, ws, pressure;
    TextView description;
    ImageView image;
    TextView dateTextView;
    RecyclerView recyclerView;
    ForecastAdapter forecastAdapter;


    String url = "https://api.openweathermap.org/data/2.5/forecast?q={city name}&cnt=5&appid={your api key}";
    String apikey = "2b60378621d8ecb326d1340a0e5b7f41";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        tt = findViewById(R.id.tt);
        dateTextView = findViewById(R.id.dateTextView);
        humidity = findViewById(R.id.ph);
        ws = findViewById(R.id.pw);
        pressure = findViewById(R.id.pr);
        description = findViewById(R.id.description);
        image = findViewById(R.id.img);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy ");
        String formattedDate = sdf.format(currentDate);
        dateTextView.setText(formattedDate);

        recyclerView = findViewById(R.id.recyclerView);
        forecastAdapter = new ForecastAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(forecastAdapter);
        forecastAdapter.notifyDataSetChanged();


    }


    public void getweather(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<Exemple> exempleCall = myapi.getweather(city.getText().toString().trim(), 5, apikey);
        exempleCall.enqueue(new Callback<Exemple>() {
            @Override
            public void onResponse(Call<Exemple> call, Response<Exemple> response) {
                if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "Please Enter a valid City", Toast.LENGTH_LONG).show();
                } else if (!(response.isSuccessful())) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();

                }
                Exemple mydata = response.body();
                Main main = mydata.getMain();
               // liste de recyler view
                List<ForecastItem> forecastList = mydata.getForecastItemList();
                if (forecastList != null && !forecastList.isEmpty()) {
                    forecastAdapter.setForecastList(forecastList);
                    forecastAdapter.notifyDataSetChanged();
                }
                // temperature
                Double temp = main.getTemp();
                Integer temperature = (int) (temp - 273.15);
                tt.setText(String.valueOf(temperature) + "°c");
                // humidity
                Integer humidityValue = main.getHumidity();
                humidity.setText(String.valueOf(humidityValue) + "%");
                // pressure
                Integer pressureValue = main.getPressure();
                pressure.setText(String.valueOf(pressureValue) + " hPa");

                List<Weather> weatherList = mydata.getWeatherList();
                if (weatherList != null && !weatherList.isEmpty()) {
                    Weather weather = weatherList.get(0);

                    // description
                    String descriptionText = weather.getDescription();
                    description.setText(descriptionText);
                    //icon
                    String iconCode = weather.getIcon();
                    String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
                    Log.d("Weather", "Icon URL: " + iconUrl);
                    Picasso.get().load(iconUrl).into(image);
                    //wind speed
                    Wind wind = mydata.getWind();
                    Double windSpeed = wind.getSpeed();
                    ws.setText(String.valueOf(windSpeed) + " m/s");




                }

            }

            @Override
            public void onFailure(Call<Exemple> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}