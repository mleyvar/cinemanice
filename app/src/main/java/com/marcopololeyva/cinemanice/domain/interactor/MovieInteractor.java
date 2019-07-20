package com.marcopololeyva.cinemanice.domain.interactor;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.marcopololeyva.cinemanice.R;

import com.marcopololeyva.cinemanice.constant.Constant;
import com.marcopololeyva.cinemanice.data.local.PopularLocal;
import com.marcopololeyva.cinemanice.data.remote.PopularRemote;
import com.marcopololeyva.cinemanice.model.ResultMovie;
import com.marcopololeyva.cinemanice.model.response.PopularResponse;

import com.marcopololeyva.cinemanice.model.response.TopRatedResponse;
import com.marcopololeyva.cinemanice.model.response.UpComingResponse;
import com.marcopololeyva.cinemanice.platform.monitor.LogMon;
import com.marcopololeyva.cinemanice.platform.network.DownloadFile;
import com.marcopololeyva.cinemanice.presentation.moviehome.MovieContract;
import com.marcopololeyva.cinemanice.root.App;

//import rx.Subscriber;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static com.marcopololeyva.cinemanice.constant.Constant.BASE_IMAGE_URL;
import static com.marcopololeyva.cinemanice.constant.Constant.ERROR_GENERAL;
import static com.marcopololeyva.cinemanice.constant.Constant.ERROR_MESSAGE_RESPONSE;
import static com.marcopololeyva.cinemanice.constant.Constant.ERROR_MOVIES_NOT_FOUND;


public class MovieInteractor implements Handler.Callback  {

    private MovieContract.PopularPresenter presenter;


    ArrayList<ResultMovie> responseDownloadImage;
    int pageDownloadImage;
    int typeDownloadImage;
    int curCountImage;
    int totImageToDownload;



    public MovieInteractor(MovieContract.PopularPresenter presenter){
        this.presenter=presenter;
    }




    public void getPopularMovies( int page,boolean isconectionNetwork, int type){

        try {
            //verificar tipo de conexion
            PopularLocal dataLocal = new PopularLocal();

               if (isconectionNetwork) {

                Log.e("OkHttp", "==============================>>  CONECCION RES OK: " );
                PopularRemote    data = new PopularRemote();

                        data.callPopularMovies(page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<PopularResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(PopularResponse response) {
                                if (response.getStatus_code()==0){

                                    if (response.getResults().size()==0){
                                        presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));
                                    }else{

                                        Log.e("OkHttp", "==============================>>  RESPONSE :    "+ new Gson().toJson(response.getResults()) );


                                        dataLocal.removeMoviesByPage(page,type);

                                        dataLocal.addMovies(page, response.getResults(), new PopularLocal.PopularLocalInterface() {
                                            @Override
                                            public void onSuccess(ArrayList<ResultMovie> res) {

                                            }

                                            @Override
                                            public void onSuccessAddMovie() {

                                            }

                                            @Override
                                            public void onSuccessAddMovies() {
                                                dataLocal.updateMoviesPage(page, type);
                                                presenter.onSuccess(response.getResults(), page,type);
                                            }


                                            @Override
                                            public void onError() {
                                                presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));

                                            }
                                        });

                                    }

                                }else{
                                    presenter.onError(ERROR_MESSAGE_RESPONSE, response.getStatus_message());
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogMon.Log(e,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
                                presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));
                            }

                            @Override
                            public void onComplete() {

                            }
                        });






            } else {

                Log.e("OkHttp", "==============================>>  CONECCION LOCAL: " );
                dataLocal.getMovies(page, new PopularLocal.PopularLocalInterface() {
                        @Override
                        public void onSuccess(ArrayList<ResultMovie> res) {
                            Log.e("OkHttp", "==============================>>  getMovies from Interactor susses: " );
                            presenter.onSuccessDownload(res,page,type);
                        }

                        @Override
                        public void onSuccessAddMovie() {

                        }

                        @Override
                        public void onSuccessAddMovies() {

                        }
                        @Override
                        public void onError() {
                            presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));
                        }
                    },type);
            }

        }catch(Exception ex){
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
            presenter.onError(ERROR_GENERAL,"");

        }

    }



    public void getTopRatedMovies( int page,boolean isconectinNetwork, int type){


        try {
            //verificar tipo de conexion
            PopularLocal dataLocal = new PopularLocal();

            if (isconectinNetwork) {

                Log.e("OkHttp", "==============================>>  CONECCION RES OK: " );
                PopularRemote    data = new PopularRemote();

                data.callTopRatedMovies(page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<TopRatedResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(TopRatedResponse response) {
                                //   if (response.getStatus_code()==0){

                                if (response.getResults().size()==0){
                                    presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));
                                }else{

                                    Log.e("OkHttp", "==============================>>  RESPONSE :    "+ new Gson().toJson(response.getResults()) );
                                    dataLocal.removeMoviesByPage(page,type);

                                    dataLocal.addMovies(page, response.getResults(), new PopularLocal.PopularLocalInterface() {
                                        @Override
                                        public void onSuccess(ArrayList<ResultMovie> res) {

                                        }

                                        @Override
                                        public void onSuccessAddMovie() {

                                        }

                                        @Override
                                        public void onSuccessAddMovies() {
                                            dataLocal.updateMoviesPage(page, type);
                                            presenter.onSuccess(response.getResults(), page, type);
                                        }


                                        @Override
                                        public void onError() {
                                            presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));

                                        }
                                    });

                                }

                                //   }else{
                                //      presenter.onError(ERROR_MESSAGE_RESPONSE, response.getStatus_message());
                                // }

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogMon.Log(e,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
                                presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));
                            }

                            @Override
                            public void onComplete() {

                            }
                        });






            } else {

                Log.e("OkHttp", "==============================>>  CONECCION LOCAL: " );
                dataLocal.getMovies(page, new PopularLocal.PopularLocalInterface() {
                    @Override
                    public void onSuccess(ArrayList<ResultMovie> res) {
                        Log.e("OkHttp", "==============================>>  getMovies from Interactor susses: " );

                        presenter.onSuccess(res, page, type);
                    }

                    @Override
                    public void onSuccessAddMovie() {

                    }

                    @Override
                    public void onSuccessAddMovies() {

                    }
                    @Override
                    public void onError() {
                        presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));
                    }
                },type);


            }



        }catch(Exception ex){
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
            presenter.onError(ERROR_GENERAL,"");

        }


    }




    public void getUpCommingMovies( int page,boolean isconectinNetwork, int type){



        try {
            //verificar tipo de conexion
            PopularLocal dataLocal = new PopularLocal();

            if (isconectinNetwork) {

                Log.e("OkHttp", "==============================>>  CONECCION RES OK: " );
                PopularRemote    data = new PopularRemote();

                data.callUpComingMovies(page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<UpComingResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UpComingResponse response) {
                                //   if (response.getStatus_code()==0){

                                if (response.getResults().size()==0){
                                    presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));
                                }else{

                                    Log.e("OkHttp", "==============================>>  RESPONSE :    "+ new Gson().toJson(response.getResults()) );
                                    dataLocal.removeMoviesByPage(page,type);

                                    dataLocal.addMovies(page, response.getResults(), new PopularLocal.PopularLocalInterface() {
                                        @Override
                                        public void onSuccess(ArrayList<ResultMovie> res) {

                                        }

                                        @Override
                                        public void onSuccessAddMovie() {

                                        }

                                        @Override
                                        public void onSuccessAddMovies() {
                                            dataLocal.updateMoviesPage(page, type);
                                            presenter.onSuccess(response.getResults(), page, type);
                                        }


                                        @Override
                                        public void onError() {
                                            presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));

                                        }
                                    });

                                }

                                //   }else{
                                //      presenter.onError(ERROR_MESSAGE_RESPONSE, response.getStatus_message());
                                // }

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogMon.Log(e,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
                                presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));
                            }

                            @Override
                            public void onComplete() {

                            }
                        });






            } else {

                Log.e("OkHttp", "==============================>>  CONECCION LOCAL: " );
                dataLocal.getMovies(page, new PopularLocal.PopularLocalInterface() {
                    @Override
                    public void onSuccess(ArrayList<ResultMovie> res) {
                        Log.e("OkHttp", "==============================>>  getMovies from Interactor susses: " );

                        presenter.onSuccess(res, page, type);
                    }

                    @Override
                    public void onSuccessAddMovie() {

                    }

                    @Override
                    public void onSuccessAddMovies() {

                    }
                    @Override
                    public void onError() {
                        presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));
                    }
                },type);


            }



        }catch(Exception ex){
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
            presenter.onError(ERROR_GENERAL,"");
        }
    }





    public void getDataSearch(String cad, int page, boolean isconectinNetwork){

        if (isconectinNetwork) {


            CompositeDisposable disposable = new CompositeDisposable();
            PublishSubject<String> publishSubject = PublishSubject.create();

            PopularRemote data = new PopularRemote();
            DisposableObserver<PopularResponse> observer = getSearchObserver(page);

            disposable.add(publishSubject.debounce(300, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .switchMapSingle(new Function<String, Single<PopularResponse>>() {
                        @Override
                        public Single<PopularResponse> apply(String s) throws Exception {
                            Log.e("OkHttp", "==============================>> QUERY SERVICE: " + s);
                            return data.callPopularSearchMovies(s, page)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread());
                        }
                    })
                    .subscribeWith(observer));

            disposable.add(observer);

            publishSubject.onNext(cad);


        }else{
            //Local

            PopularLocal dataLocal = new PopularLocal();

            dataLocal.getMoviesQuery(cad, new PopularLocal.PopularLocalInterface() {
                @Override
                public void onSuccess(ArrayList<ResultMovie> res) {
                    presenter.onSuccess(res, page, Constant.SEARCH_MOVIES);
                }

                @Override
                public void onSuccessAddMovie() {

                }

                @Override
                public void onSuccessAddMovies() {

                }

                @Override
                public void onError() {
                    presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));
                }
            });

        }
    }


    private DisposableObserver<PopularResponse> getSearchObserver(int page ) {


        return new DisposableObserver<PopularResponse>() {
            @Override
            public void onNext(PopularResponse response) {


                if (response.getStatus_code()==0){

                    if (response.getResults().size()==0){
                        presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Movies_not_found));

                    }else{


                        PopularLocal dataLocal = new PopularLocal();


                        Log.e("OkHttp", "==============================>>  RESPONSE :    "+ new Gson().toJson(response.getResults()) );
                        dataLocal.removeMoviesByPage(page, Constant.SEARCH_MOVIES);

                        dataLocal.addMovies(page, response.getResults(), new PopularLocal.PopularLocalInterface() {
                            @Override
                            public void onSuccess(ArrayList<ResultMovie> res) {

                            }

                            @Override
                            public void onSuccessAddMovie() {

                            }

                            @Override
                            public void onSuccessAddMovies() {
                                dataLocal.updateMoviesPage(page,  Constant.SEARCH_MOVIES);
                                presenter.onSuccess(response.getResults(), page, Constant.SEARCH_MOVIES);
                            }


                            @Override
                            public void onError() {
                                presenter.onError(ERROR_MESSAGE_RESPONSE, App.getContext().getString(R.string.Error_In_Service));

                            }
                        });




                    }

                }else{
                    presenter.onError(ERROR_MESSAGE_RESPONSE, response.getStatus_message());
                }

            }

            @Override
            public void onError(Throwable e) {
                LogMon.Log(e,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
                //   presenter.onError(ERROR_MOVIES_NOT_FOUND, App.getContext().getString(R.string.Error_In_Service));
            }

            @Override
            public void onComplete() {

            }
        };
    }








    public void DownloadAndSaveImage(ArrayList<ResultMovie> response,int page, int type){

        responseDownloadImage=response;
        pageDownloadImage=page;
        typeDownloadImage=page;

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );

        int th=0;
        int withImages=0;

        PopularLocal dataLocal = new PopularLocal();

        for (ResultMovie r : response) {

            if (dataLocal.withImageMovie(r.getId())){
                withImages++;
            }else{
                th++;
                executor.execute(new DownloadFile(th, r.getId() , BASE_IMAGE_URL + r.getPoster_path(), new Handler(this),response.size()) );

            }

        }


        dataLocal.closeDB();
        totImageToDownload = responseDownloadImage.size() - withImages;
    }

    @Override
    public boolean handleMessage(Message msg) {
        curCountImage++;

        Log.e("OkHttp", "==============================>>  MESSAGE HANDLER:  "+ msg );

        if (curCountImage == totImageToDownload){
            presenter.onSuccessDownload(responseDownloadImage,pageDownloadImage,typeDownloadImage);
        }
        return true;

    }



}
