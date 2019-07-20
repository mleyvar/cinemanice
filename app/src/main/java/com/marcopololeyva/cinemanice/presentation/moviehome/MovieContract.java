package com.marcopololeyva.cinemanice.presentation.moviehome;


import com.marcopololeyva.cinemanice.model.ResultMovie;
import java.util.ArrayList;

public interface MovieContract {

    interface   ViewPopularMovies{


        void onSuccessPopularMovies(ArrayList<ResultMovie> response,int page, int type);
        void onError(String message);

    }


    interface PopularPresenter{

        void getDataSearch(String cad, int page, boolean isconectinNetwork);

        void getPopularMovies( int page,boolean isconectinNetwork);

        void getTopRatedMovies( int page,boolean isconectinNetwork);

        void getUpComingMovies( int page,boolean isconectinNetwork);

        void onError(int errorCode, String message);

        void onSuccess(ArrayList<ResultMovie> response,int page, int type);
        void onSuccessDownload(ArrayList<ResultMovie> response,int page, int type);
        void onSuccessSearchMovie(ArrayList<ResultMovie> response,int page, int type);

    }

}
