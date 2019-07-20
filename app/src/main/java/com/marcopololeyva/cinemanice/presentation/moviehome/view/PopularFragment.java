package com.marcopololeyva.cinemanice.presentation.moviehome.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.marcopololeyva.cinemanice.R;
import com.marcopololeyva.cinemanice.adapter.MoviesAdapter;
import com.marcopololeyva.cinemanice.constant.Constant;
import com.marcopololeyva.cinemanice.model.ResultMovie;
import com.marcopololeyva.cinemanice.platform.custom.GridSpacingItemDecoration;
import com.marcopololeyva.cinemanice.platform.network.Conexion;
import com.marcopololeyva.cinemanice.presentation.home.view.HomeActivity;
import com.marcopololeyva.cinemanice.presentation.moviehome.MovieContract;
import com.marcopololeyva.cinemanice.presentation.moviehome.presenter.PopularPresenter;
import com.marcopololeyva.cinemanice.root.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularFragment extends Fragment implements MovieContract.ViewPopularMovies {

    private PopularPresenter presenter;
    private MoviesAdapter adapter;
    List<ResultMovie> movieList;
    FrameLayout view;

    @BindView(R.id.recycler_view_popular)
    RecyclerView recycler_view;

    Conexion conexionNetwork;

    LayoutInflater inflater;
    ViewGroup container;



    public static PopularFragment newInstance(Bundle args) {
        PopularFragment fragment = new PopularFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        this.inflater=inflater;
        this.container=container;

        this.view = (FrameLayout) inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this,view);
        adapter = new MoviesAdapter ( getActivity(), getFragmentManager(), getContext(), movieList);
        presenter = new PopularPresenter(this);

       // recycler_view = view.findViewById(R.id.recycler_view_popular);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.addItemDecoration(new GridSpacingItemDecoration(1, ((HomeActivity) getActivity()).dpToPx(2), false));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(adapter);

        if ( App.getmPreference().getBooleanData("First")){
            App.showLoadDialog(getFragmentManager());
            getData(0);
            App.getmPreference().setBooleanData("First",false);
        }

        return view;
    }


    public void getData(int pos){

        conexionNetwork=  (Conexion) getArguments().getSerializable(Constant.BUNDLES_CONEXION_NETWORK);

        if (presenter == null ) presenter = new PopularPresenter(this);
        App.showLoadDialog(getFragmentManager());
        switch (pos){
            case 0:
                presenter.getPopularMovies(1, conexionNetwork.isConnected());
                break;
            case 1:
                presenter.getTopRatedMovies(1, conexionNetwork.isConnected());
                break;
            case 2:
                presenter.getUpComingMovies(1, conexionNetwork.isConnected());
                break;
        }

    }

    public void getDataSearch(String cad){
        presenter.getDataSearch(cad,1,conexionNetwork.isConnected());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        App.setWhichFragment(this);
    }

    @Override
    public void onSuccessPopularMovies(ArrayList<ResultMovie> response,int page, int type) {
        App.dissmissLoad();
            if (response.size() > 0) {
                movieList = response;
                adapter.setMovieList(movieList);
                recycler_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
    }

    @Override
    public void onError(String message) {
        App.dissmissLoad();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
