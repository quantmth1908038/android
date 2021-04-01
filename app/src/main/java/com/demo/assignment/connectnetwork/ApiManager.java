package com.demo.assignment.connectnetwork;

import com.demo.assignment.model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {

    String SERVER_URL = "http://dataservice.accuweather.com/";

    @GET("forecasts/v1/hourly/12hour/353412?apikey=JxNELVMAGZ9JF8Qmps1EARpfb69Tlrnj&language=vi-vn&metric=true")
    Call<List<Weather>> getListData();

}
