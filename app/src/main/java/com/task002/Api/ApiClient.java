package com.task002.Api;

import com.task002.Utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    public static String Base_URL = Constants.BASE_URL;
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit ;
    }
}
