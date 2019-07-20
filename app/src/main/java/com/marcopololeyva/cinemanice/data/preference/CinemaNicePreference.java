package com.marcopololeyva.cinemanice.data.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CinemaNicePreference {
    private SharedPreferences mSharedPreferences;

    public CinemaNicePreference(Context context){
        this.mSharedPreferences = context.getSharedPreferences("CinemaNicePref",Context.MODE_PRIVATE);
    }

    public CinemaNicePreference(SharedPreferences mSharedPreferences){
        this.mSharedPreferences = mSharedPreferences;
    }

    public void setStringData(String key, String value){
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public String getStringData(String key){
        return  mSharedPreferences.getString(key, "");
    }

    public void setIntData(String key, int value){
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public int getIntData(String key){
        return mSharedPreferences.getInt(key, 0);
    }


    public void setBooleanData(String key, boolean value){
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBooleanData(String key){
        return mSharedPreferences.getBoolean(key, false);
    }



}
