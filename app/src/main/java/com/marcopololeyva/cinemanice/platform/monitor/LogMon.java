package com.marcopololeyva.cinemanice.platform.monitor;

import android.util.Log;

public class LogMon {


    public static void Log(Throwable ex, String module){
        Log.e("OkHttp", "==============================>>  MONITOR MODULE : "+ module + "  ERROR:  ", ex );
        // crashalitics


    }
}
