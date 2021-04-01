package com.demo.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.demo.assignment.adapter.WeatherAdapter;
import com.demo.assignment.connectnetwork.ApiManager;
import com.demo.assignment.model.Weather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Weather> weatherList = new ArrayList<>();
    WeatherAdapter adapter;
    TextView tvStatus, tvTem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        tvStatus = findViewById(R.id.tvStatus);

        tvTem = findViewById(R.id.tvTem);

        adapter = new WeatherAdapter(this, weatherList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);

        RecyclerView rvItem = findViewById(R.id.rvWeather);
        rvItem.setLayoutManager(layoutManager);
        rvItem.setAdapter(adapter);

    }

    private  void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiManager service = retrofit.create(ApiManager.class);
        service.getListData().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                weatherList = response.body();
                adapter.reloadData(weatherList);
                tvStatus.setText(weatherList.get(0).getIconPhrase());
                tvTem.setText(String.valueOf((int) weatherList.get(0).getTemperature().getValue()));
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Log.d("TAG", "onFatlure" + weatherList);
            }
        });
    }
}