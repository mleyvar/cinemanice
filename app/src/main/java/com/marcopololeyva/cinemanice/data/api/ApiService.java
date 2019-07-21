package com.marcopololeyva.cinemanice.data.api;

import com.marcopololeyva.cinemanice.model.response.PopularResponse;
import com.marcopololeyva.cinemanice.model.response.TopRatedResponse;
import com.marcopololeyva.cinemanice.model.response.UpComingResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.marcopololeyva.cinemanice.constant.Constant.SERVICE_POPULAR;
import static com.marcopololeyva.cinemanice.constant.Constant.SERVICE_SEARCH;
import static com.marcopololeyva.cinemanice.constant.Constant.SERVICE_TOP_RATED;
import static com.marcopololeyva.cinemanice.constant.Constant.SERVICE_UPCOMING;

public interface ApiService {


    @GET(SERVICE_POPULAR)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<PopularResponse> callPopularMovies(@Query(value="api_key", encoded=true) String api_key, @Query(value="page", encoded=true)  int page);

    @GET(SERVICE_TOP_RATED)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<TopRatedResponse> callTopRatedMovies(@Query(value="api_key", encoded=true) String api_key, @Query(value="page", encoded=true)  int page);

    @GET(SERVICE_UPCOMING)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<UpComingResponse> callUpComingMovies(@Query(value="api_key", encoded=true) String api_key, @Query(value="page", encoded=true)  int page);


    @GET(SERVICE_SEARCH)
    Single<PopularResponse> callPopularMoviesSearch(@Query(value="api_key", encoded=true) String api_key,@Query(value="query", encoded=true)  String query, @Query(value="page", encoded=true)  int page);

}
