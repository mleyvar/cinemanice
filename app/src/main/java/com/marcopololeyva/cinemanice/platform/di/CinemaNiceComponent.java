package com.marcopololeyva.cinemanice.platform.di;

import com.marcopololeyva.cinemanice.presentation.base.BaseActivity;
import com.marcopololeyva.cinemanice.presentation.base.BaseFragment;
import com.marcopololeyva.cinemanice.presentation.home.view.HomeActivity;
import com.marcopololeyva.cinemanice.presentation.moviehome.view.PopularFragment;
import com.marcopololeyva.cinemanice.presentation.splash.view.SplashActivity;

import dagger.Component;

@ActivityScope
@Component( modules = CinemaNiceModule.class)
public interface CinemaNiceComponent {

    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(PopularFragment popularFragment);

}
