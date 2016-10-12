package com.imdb.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pranavbhoraskar on 8/10/16.
 */


@Module
public class NetworkModule {

    String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

//    @Provides
//    @Singleton
//    Gson provideGson(){
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        return gsonBuilder.create();
//    }
//
//    @Provides
//    @Singleton
//    OkHttpClient providesOkHttpClient(){
//        return new OkHttpClient();
//    }
//

    @Provides
    @Singleton
//    Retrofit provideRetrofitConnection(Gson gson, OkHttpClient okHttpClient){
    Retrofit provideRetrofitConnection(){

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .baseUrl(baseUrl)
                .build();
     }
}