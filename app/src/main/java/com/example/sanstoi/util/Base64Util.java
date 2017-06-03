package com.example.sanstoi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by Sans toi on 2017/4/17.
 */

public class Base64Util {

    public static Bitmap getBitmap(Uri uri, Context context){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);

            return transform(bitmap, 400, 300 );
        } catch (Exception e){
            Log.e("Exception",e.getMessage());
        }
        return null;
    }

    public static String imageToBase64(Uri uri, Context context){
        Bitmap bitmap = getBitmap(uri, context);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap transform(Bitmap bitmap, int width, int height) {
        if(bitmap.getWidth() < width || bitmap.getHeight() < height){
            return bitmap;
        }
        int x = (bitmap.getWidth() - width) / 2;
        int y = (bitmap.getHeight() - height) / 2;
        Bitmap result = Bitmap.createBitmap(bitmap, x, y, width, height);
        if (result != bitmap) {
            bitmap.recycle();
        }
        return result;
    }

}
