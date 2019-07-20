package com.marcopololeyva.cinemanice.util.converter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConverterGen {


    public static byte[] convertBitmapToArrayBytes(BitmapDrawable bmp){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    public static byte[] convertInputStreamToArrayBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
