package com.marcopololeyva.cinemanice.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.marcopololeyva.cinemanice.model.ResultMovie;
import com.marcopololeyva.cinemanice.platform.monitor.LogMon;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataRepository {
    Realm realm;

    public interface PopularLocalInterface{

        void onSuccess( ArrayList<ResultMovie> res);
        void onSuccessAddMovie();
        void onSuccessAddMovies();
        void onError();

    }


    public DataRepository(){
        realm = Realm.getDefaultInstance();

    }

    public void closeDB(){
        if (realm != null) realm.close();
    }

    public void  addMovie(int page, ResultMovie mov, PopularLocalInterface listener) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                realm.beginTransaction();
                ResultMovie movies = realm.createObject(ResultMovie.class);
                movies.setId(mov.getId());
                movies.setTitle(mov.getTitle());
                movies.setOverview(mov.getOverview());
                movies.setVote_count(mov.getVote_count());
                movies.setPoster_path(mov.getPoster_path());
                movies.setPage(page);
                realm.commitTransaction();
            }

        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSuccessAddMovie();
            }

        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                if (realm.isInTransaction())
                    realm.cancelTransaction();
                LogMon.Log(error,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
                listener.onError();

            }
        });


    }


    public void addMovies(int type,int page, List<ResultMovie> mov, PopularLocalInterface listener){
            realm.beginTransaction();
            try {
                for(int x=0; x<mov.size();x++){
                    mov.get(x).setType(type);
                    mov.get(x).setPage(page);
                }

                realm.createAllFromJson(ResultMovie.class,  new Gson().toJson(mov));

                realm.commitTransaction();
                listener.onSuccessAddMovies();
            } catch (Exception e) {
                if (realm.isInTransaction())
                    realm.cancelTransaction();
                LogMon.Log(e, this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
                listener.onError();

            }
    }


    public void getMovies(int page, PopularLocalInterface listener, int type){

        ArrayList<ResultMovie> res= new ArrayList<ResultMovie>();

        try{
            realm.beginTransaction();
            RealmQuery<ResultMovie> query = realm.where(ResultMovie.class);
            RealmResults<ResultMovie> movie = query.equalTo("page",page).equalTo("type",type).findAll();

            res.addAll(realm.copyFromRealm(movie));
            realm.commitTransaction();
            listener.onSuccess(res);


        }catch(Exception ex){
            if (realm.isInTransaction())
                realm.cancelTransaction();
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
            listener.onError();

        }
    }
    public boolean withImageMovie(int id){


        boolean res=false;

        try{
            realm.beginTransaction();
            RealmQuery<ResultMovie> query = realm.where(ResultMovie.class);
            ResultMovie movie = query.equalTo("id",id).findFirst();


            if (movie != null && movie.getImage() != null ) {
                res = true;
            }else {
                res = false;
            }
            realm.commitTransaction();


        }catch(Exception ex){
            if (realm.isInTransaction())
                realm.cancelTransaction();
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
            res=false;

        }
        finally {

            return res;
        }

    }

    public boolean isExistMovie(Realm realm2,int id){


        boolean res=false;

        try{
            RealmQuery<ResultMovie> query = realm2.where(ResultMovie.class);
            ResultMovie movie = query.equalTo("id",id).findFirst();

            if (movie.getId()>0){
                res=true;
            }else{
                res=false;
            }


        }catch(Exception ex){
            res=false;

        }
        finally {

            return res;
        }

    }

    public void getMoviesQuery( String queryData, PopularLocalInterface listener){

        ArrayList<ResultMovie> res= new ArrayList<ResultMovie>();
        try{
            realm.beginTransaction();
            RealmQuery<ResultMovie> query = realm.where(ResultMovie.class);
            RealmResults<ResultMovie> movie = query.contains("title",queryData).distinct("id") .findAll();

            res.addAll(realm.copyFromRealm(movie));
            realm.commitTransaction();
            listener.onSuccess(res);



        }catch(Exception ex){
            if (realm.isInTransaction())
                realm.cancelTransaction();
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
            listener.onError();

        }
    }

    public void removeMoviesByPage(int page, int type){

        Realm realm2 = Realm.getDefaultInstance();
        try {

            realm2.beginTransaction();
            final RealmResults<ResultMovie> result = realm.where(ResultMovie.class).findAll();

            RealmResults<ResultMovie> dr_cero = result.where().equalTo("page", 0).findAll();

            RealmResults<ResultMovie> dr = result.where().equalTo("page", page).equalTo("type",type).findAll();
            dr.deleteAllFromRealm();
            dr_cero.deleteAllFromRealm();
            realm2.commitTransaction();

        }catch(Exception ex){
            if (realm2.isInTransaction())
                realm2.cancelTransaction();
            LogMon.Log(ex,this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());

        }finally {
            if (realm2 != null) realm2.close();
        }

    }

    public void updateMoviesPage(int page, int type) {
        try {
            realm.beginTransaction();
            RealmResults<ResultMovie> movies = realm
                    .where(ResultMovie.class)
                    .equalTo("page", 0).equalTo("type", type)
                    .findAll();
            for (ResultMovie m : movies) {
                m.setPage(page);
                m.setType(type);
            }
            realm.commitTransaction();
        } catch (Exception ex) {
            if (realm.isInTransaction())
                realm.cancelTransaction();
            LogMon.Log(ex, this.getClass().getSimpleName() + "-" + new Throwable().getStackTrace()[0].getMethodName());
        }
    }


    public void updateImageFromMovie(int id, byte[] image) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    ResultMovie movies = realm
                            .where(ResultMovie.class)
                            .equalTo("id", id)
                            .findFirst();

                    if (movies != null) {
                        movies.setImage(image);
                    }


                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            if (realm.isInTransaction())
                realm.cancelTransaction();
        }

    }


}
