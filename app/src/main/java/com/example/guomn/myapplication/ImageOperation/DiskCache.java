package com.example.guomn.myapplication.ImageOperation;

/**
 * Created by guomn on 2018/9/21.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 将图片缓存在SD卡中
 */
public class DiskCache implements ImageCache{
    static String cacheDir = "sdcard/cache/";
    //从缓存中获取图片
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }
    //将图片缓存到内存
    public void put(String url,Bitmap bmp){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(cacheDir+url);
            bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
