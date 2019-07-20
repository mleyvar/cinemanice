package com.marcopololeyva.cinemanice.root;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;

import com.marcopololeyva.cinemanice.data.preference.CinemaNicePreference;
import com.marcopololeyva.cinemanice.platform.custom.LoadDialog;
import com.marcopololeyva.cinemanice.platform.di.CinemaNiceComponent;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends MultiDexApplication {

    private static App instance = null;
    private static Context context;
    private static SharedPreferences settings;
    private static Fragment whichFragment;
    private static CinemaNicePreference mPreference;
    private static LoadDialog load;

    private CinemaNiceComponent cinemaNiceComponent;

    private static ArrayList<byte[]> imageListDownload = new ArrayList<>();

    public static void addImage(byte[] img){
        imageListDownload.add(img);
    }

    public static ArrayList<byte[]> getImages(){
        return imageListDownload;
    }


    private void setDefaultConfig(){
        Realm.init(getApplicationContext());
        RealmConfiguration config =new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);


        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("CinemaNice||.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);



    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //initDagger();
        //SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
        if (android.support.multidex.BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
        }


        setDefaultConfig();

        ImageCache.Build();

        mPreference = new CinemaNicePreference(this);


    //    cinemaNiceComponent = DaggerCinemaNiceComponent.builder().cinemaNiceModule (new CinemaNiceModule(this)).build();


    }


   /* private void initDagger() {
        DaggerManager.getInstance().buildComponentAndInject(this);
    }
*/

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }


    public static LoadDialog showLoadDialog(FragmentManager manager){
        if(load == null){
            synchronized (App.class){
                if(load == null){
                    load = LoadDialog.newInstance();
                    load.setCancelable(false);
                    try{
                        load.show(manager, "load dialog");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return load;
    }

    public static void dissmissLoad(){
        try {
            if(load != null) {
                try{
                    load.dismiss();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                load = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }


    public static Fragment getWhichFragment() {
        return whichFragment;
    }

    public static void setWhichFragment(Fragment whichFragment) {
        App.whichFragment = whichFragment;
    }

    public static CinemaNicePreference getmPreference() {
        return mPreference;
    }
}
