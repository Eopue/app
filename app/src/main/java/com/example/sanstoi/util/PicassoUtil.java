package com.example.sanstoi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.example.sanstoi.constants.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Sans toi on 2017/4/16.
 */

public class PicassoUtil {

    static String ip = Constants.url;

    //默认加载
    public static void loadImageView(Context mContext, String url, ImageView imageView){
        Picasso.with(mContext).load(ip+url).into(imageView);
    }

    //默认加载
    public static void loadImageView(Context mContext, Uri uri, ImageView imageView){
        Picasso.with(mContext).load(uri).into(imageView);
    }

    //默认加载图片，指定大小
    public static void loadImageView(Context mContext, String url, int width, int height, ImageView imageView){
        Picasso.with(mContext)
                .load(ip+url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    //加载图片有默认图片
    public static void loadImageViewHolder(Context mContext, String url, int loadImage, int errImage, ImageView imageView){
        Picasso.with(mContext)
                .load(ip+url)
                .placeholder(loadImage)
                .error(errImage)
                .into(imageView);
    }

    //裁剪图片
    public static void loadImageViewCrop(Context mContext, String url, int loadImage, int errImage, ImageView imageView){
        Picasso.with(mContext)
                .load(ip+url)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }

    //裁剪图片
    public static void loadImageViewCrop(Context mContext, Uri uri, int loadImage, int errImage, ImageView imageView){
        Picasso.with(mContext)
                .load(ip+uri)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }

    //按比例裁剪图片
    public static class CropSquareTransformation implements Transformation {

        @Override
        public Bitmap transform(Bitmap bitmap) {
            int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
            int x = (bitmap.getWidth() - size ) / 2;
            int y = (bitmap.getHeight() - size ) / 2;
            Bitmap result = Bitmap.createBitmap(bitmap, x, y, size, size);
            if(result != bitmap){
                bitmap.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "picture";
        }
    }
}
