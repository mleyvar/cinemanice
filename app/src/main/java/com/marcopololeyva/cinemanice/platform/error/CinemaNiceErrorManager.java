package com.marcopololeyva.cinemanice.platform.error;

import android.content.Context;

import com.marcopololeyva.cinemanice.R;
import com.marcopololeyva.cinemanice.constant.Constant;


public class CinemaNiceErrorManager {


    public static String ErrorManager(Context con, int errorCode, String messageResponse){

        String msg="";
        switch(errorCode){
            case Constant.ERROR_GENERAL: {
                msg = con.getString(R.string.ErrorGeneral);
                break;
            }
            case Constant.ERROR_MESSAGE_RESPONSE: {
                msg = messageResponse;
                break;
            }
            case Constant.ERROR_MOVIES_NOT_FOUND: {
                msg = con.getString(R.string.Movies_not_found);
                break;
            }
            default:
                msg = messageResponse;

        }

        return msg;


    }



}
