package co.zero.android.armyofones.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory that creates interfaces to access REST services
 * Created by htenjo on 8/19/16.
 */
public class NetworkFactory{
    private NetworkFactory(){
    }

    public static <T> T buildService(Class<T> serviceClass, String serviceBaseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serviceBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = retrofit.create(serviceClass);
        return service;
    }
}
