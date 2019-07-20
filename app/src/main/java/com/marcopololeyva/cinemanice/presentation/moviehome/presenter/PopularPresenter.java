package com.marcopololeyva.cinemanice.presentation.moviehome.presenter;

import android.content.Context;
import android.util.Log;

import com.marcopololeyva.cinemanice.constant.Constant;
import com.marcopololeyva.cinemanice.domain.interactor.MovieInteractor;
import com.marcopololeyva.cinemanice.model.ResultMovie;
import com.marcopololeyva.cinemanice.platform.error.CinemaNiceErrorManager;
import com.marcopololeyva.cinemanice.presentation.moviehome.MovieContract;
import com.marcopololeyva.cinemanice.root.App;

import java.util.ArrayList;


public class PopularPresenter implements MovieContract.PopularPresenter {

    private static PopularPresenter _instance = null;

    private MovieContract.ViewPopularMovies view;
    private MovieInteractor model;
    private Context context;


    public PopularPresenter(MovieContract.ViewPopularMovies view){
        this.view=view;
        this.context=context;
        this.model = new MovieInteractor(this);
    }




    @Override
    public void getPopularMovies( int page,boolean isconectinNetwork) {
        Log.e("OkHttp", "==============================>>  GET POPULAR MOVIES: " );
        model.getPopularMovies( page, isconectinNetwork, Constant.POPULAR_MOVIES);
    }

    @Override
    public void getTopRatedMovies(int page, boolean isconectinNetwork) {
        Log.e("OkHttp", "==============================>>  GET getTopRatedMovies MOVIES: " );
        model.getTopRatedMovies( page, isconectinNetwork, Constant.TOP_RATED_MOVIES);
    }

    @Override
    public void getUpComingMovies(int page, boolean isconectinNetwork) {
        Log.e("OkHttp", "==============================>>  GET getUpComingMovies MOVIES: " );
        model.getUpCommingMovies( page, isconectinNetwork, Constant.UPCOMING_MOVIES);
    }


    @Override
    public void getDataSearch(String cad, int page, boolean isconectinNetwork) {
        model.getDataSearch(cad,page, isconectinNetwork);
    }


    @Override
    public void onError(int errorCode, String message) {
        view.onError(CinemaNiceErrorManager.ErrorManager(App.getContext(),errorCode,message));
    }

    @Override
    public void onSuccess(ArrayList<ResultMovie> response,int page, int type) {
        model.DownloadAndSaveImage(response,page,type);

    }

    @Override
    public void onSuccessDownload(ArrayList<ResultMovie> response, int page, int type) {
        view.onSuccessPopularMovies(response, page,type);
    }

    @Override
    public void onSuccessSearchMovie(ArrayList<ResultMovie> response,int page, int type) {
         view.onSuccessPopularMovies( (ArrayList<ResultMovie>)response,page,type );
    }
}
