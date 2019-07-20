package com.marcopololeyva.cinemanice.platform.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.Serializable;

public class Conexion implements Serializable {
    private  Activity activity;
    private  static Conexion _instance = null;

    public Conexion(Activity activity) {
        this.activity = activity;
    }

    public static synchronized Conexion getInstance(Activity ac) {

        if (_instance == null) {
            _instance = new Conexion(ac);
        }
        return _instance;
    }

    public  boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
