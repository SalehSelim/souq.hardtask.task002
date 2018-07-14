package com.task002.Api;


import com.task002.Model.City;
import com.task002.Model.Country;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("GetCountries")
    Call<ArrayList<Country>> getCountries();

    @GET("GetCities")
    Call<ArrayList<City>> getCities(@Query("countryId") String countryId);
}
