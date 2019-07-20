package com.marcopololeyva.cinemanice.platform.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.marcopololeyva.cinemanice.data.local.PopularLocal;
import com.marcopololeyva.cinemanice.root.App;
import com.marcopololeyva.cinemanice.util.converter.ConverterGen;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class DownloadFile implements Runnable {

    int numTread;
    Handler handler;
    String imageUrl;
    public static final String TAG = "DownloadFile";
    int id;
    int totImages;


    public DownloadFile() {

    }

    public DownloadFile(int numTread, int id, String imageUrl, Handler handler, int totImages) {
        this.numTread=numTread;
        this.id =id;
        this.handler = handler;
        this.imageUrl = imageUrl;
        this.totImages=totImages;
    }

    @Override
    public void run() {

        Log.e("OkHttp", "==============================>>  Download Image: " +imageUrl + "    Thread: " + numTread);


        byte[] img=  getBitmap(imageUrl);

        if (img==null){
            Log.e("OkHttp", "==============================>>  IMAGE NULL: "  + "    Thread: " + numTread);
        }else{
            Log.e("OkHttp", "==============================>>  IMAGE Downlaod OK: " + img.length  + "    Thread: " + numTread);
        }



        Log.e("OkHttp", "==============================>>  ADD LIST: "+  "    Thread: " + numTread );
        Log.e("OkHttp", "==============================>>  ADD LIST SIZE: "+ img.length + "    Thread: " + numTread );

        if (img != null){
            App.addImage(img);
            PopularLocal dataLocal = new PopularLocal();
            dataLocal.updateImageFromMovie(id, img);
            dataLocal.closeDB();



        }else{
            Log.e("OkHttp", "==============================>>  ADD LIST NULLLL: "+  "    Thread: " + numTread );
        }
        sendMessage(numTread, "FINISH"  + "    Thread: " + numTread);
    }


    public void sendMessage(int what, String msg) {
        Message message = handler.obtainMessage(what, msg);
        message.sendToTarget();
    }

    private byte[]  getBitmap(String urlString) {
        byte[] bitmap = null;

        try {
            Log.e("OkHttp", "==============================>>  Inicio descarga  0 : " +urlString  + "    Thread: " + numTread);
            URL url=new URL(urlString);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            int responseCode=connection.getResponseCode();
            if (responseCode!=200)
                throw new Exception("Error in connection");
            InputStream is=connection.getInputStream();
            bitmap = ConverterGen.convertInputStreamToArrayBytes(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            bitmap=null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap=null;
        } catch (Exception e) {
            e.printStackTrace();
            bitmap=null;
        }finally {
            return bitmap;
        }
    }

}
