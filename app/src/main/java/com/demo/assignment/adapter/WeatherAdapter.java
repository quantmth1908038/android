package com.demo.assignment.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.assignment.R;
import com.demo.assignment.model.Weather;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private List<Weather> weatherList;

    public WeatherAdapter(Activity activity, List<Weather> weatherList) {
        this.activity = activity;
        this.weatherList = weatherList;
    }

    public void reloadData(List<Weather> list) {
        this.weatherList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.list_weather, parent, false);
        WeatherHolder holder = new WeatherHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WeatherHolder itemHolder = (WeatherHolder) holder;
        Weather model = weatherList.get(position);
        itemHolder.tvTime.setText(convertTime(model.getDateTime()));
        itemHolder.tvTemperature.setText(String.valueOf(model.getTemperature().getValue()));
        if (model.getWeatherIcon() < 10) {
            Glide.with(activity).load("https://developer.accuweather.com/sites/default/files/0"+model.getWeatherIcon()+"-s.png").into(itemHolder.ivIcon);
        }else {
            Glide.with(activity).load("https://developer.accuweather.com/sites/default/files/"+model.getWeatherIcon()+"-s.png").into(itemHolder.ivIcon);
        }
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class WeatherHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvTemperature;
        ImageView ivIcon;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }

    public String convertTime(String inputTime) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = (Date) inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("ha");
        String goal = outFormat.format(date);
        return goal;
    }

}
