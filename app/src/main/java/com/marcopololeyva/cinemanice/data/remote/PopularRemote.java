package com.marcopololeyva.cinemanice.data.remote;

import com.marcopololeyva.cinemanice.data.api.ApiConnect;
import com.marcopololeyva.cinemanice.data.api.ApiService;
import com.marcopololeyva.cinemanice.model.response.PopularResponse;
import com.marcopololeyva.cinemanice.model.response.TopRatedResponse;
import com.marcopololeyva.cinemanice.model.response.UpComingResponse;


import io.reactivex.Observable;
import io.reactivex.Single;

import static com.marcopololeyva.cinemanice.constant.Constant.API_KEY;

public class PopularRemote {

    private ApiService mAPIService;


    public PopularRemote(){
        mAPIService = ApiConnect.getApiService() ;
    }




    public Observable<PopularResponse> callPopularMovies(int page){
        // RxJava
        return mAPIService.callPopularMovies(API_KEY,page);

    }

    public Single<PopularResponse> callPopularSearchMovies(String query, int page){

        // RxJava
        return mAPIService.callPopularMoviesSearch(API_KEY,query, page);

    }

    public Observable<TopRatedResponse> callTopRatedMovies(int page){
        // RxJava
        return mAPIService.callTopRatedMovies(API_KEY,page);

    }

    public Observable<UpComingResponse> callUpComingMovies(int page){
        // RxJava
        return mAPIService.callUpComingMovies(API_KEY,page);

    }





}
