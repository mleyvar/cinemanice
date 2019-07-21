package com.marcopololeyva.cinemanice.presentation.moviehome.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.marcopololeyva.cinemanice.domain.interactor.MovieInteractor;
import com.marcopololeyva.cinemanice.presentation.moviehome.view.PopularFragment;
import com.marcopololeyva.cinemanice.root.App;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({App.class})
public class PopularPresenterTest {

    @Mock
    Context mockContext;
    @Mock
    PopularFragment view;

    @Mock
    MovieInteractor model;

    PopularPresenter presenter;


    @Mock
    App app;



    @Before
    public void setUp(){


        mockContext = mock(Context.class);
        view = mock(PopularFragment.class);

        model = mock(MovieInteractor.class);

        app = PowerMockito.mock(App.class);

        PowerMockito.mockStatic(App.class);
        PowerMockito.when(App.getInstance()).thenReturn(app);

        presenter = new PopularPresenter(view);

        model = new MovieInteractor(presenter);

    }







}
