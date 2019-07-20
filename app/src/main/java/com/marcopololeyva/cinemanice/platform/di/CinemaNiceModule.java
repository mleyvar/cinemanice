package com.marcopololeyva.cinemanice.platform.di;


import com.marcopololeyva.cinemanice.presentation.moviehome.MovieContract;
import com.marcopololeyva.cinemanice.presentation.moviehome.presenter.PopularPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CinemaNiceModule {

    MovieContract.ViewPopularMovies view;

    public CinemaNiceModule(MovieContract.ViewPopularMovies view){
        this.view=view;
    }

    @Provides
    @Singleton
    public PopularPresenter providerMoviePresenter(MovieContract.ViewPopularMovies view){
        return new PopularPresenter(view);
    }

    @Provides
    public MovieContract.ViewPopularMovies provideView() {
        return view;
    }





}
