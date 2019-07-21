package com.marcopololeyva.cinemanice.data.api;


import android.util.Log;

import static com.marcopololeyva.cinemanice.constant.Constant.BASE_URL;

public class ApiConnect {

    private ApiConnect() {}



    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);


    }


}
