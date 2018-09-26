package com.example.guomn.myapplication.ImageOperation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by guomn on 2018/9/21.
 */

/**
 * 图片加载类
 * 修改之后
 */
public class ImageLoader {
//    //内存缓存
//    ImageCache mImageCache = new ImageCache();
//    //SD卡缓存
//    DiskCache mdiskCache = new DiskCache();
//    //双缓存
//    DoubleCache mDoubleCache = new DoubleCache();
//    //是否使用SD卡缓存
//    boolean isUseDiskCache = false;
//    //使用双缓存
//    boolean isUseDoubleCache = false;
//    //线程池，线程数量为CPU的数量
//    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//    //加载图片
//    public void displayImage(final String url,final ImageView imageView){
//        //判断使用哪种缓存
////        Bitmap bitmap = isUseDiskCache ? mdiskCache.get(url):mImageCache.get(url);
//        Bitmap bmp = null;
//        if(isUseDoubleCache){
//            bmp = mDoubleCache.get(url);
//        }else if(isUseDiskCache){
//            bmp = mdiskCache.get(url);
//        }else{
//            bmp = mImageCache.get(url);
//        }
////        if(bitmap!=null){
////            imageView.setImageBitmap(bitmap);
////            return;
////        }
//        if(bmp!=null){
//            imageView.setImageBitmap(bmp);
//        }
//        //没有缓存，则提交给线程池进行下载
//        imageView.setTag(url);
//        mExecutorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = downloadImage(url);
//                if(bitmap == null){
//                    return;
//                }
//                if(imageView.getTag().equals((url))){
//                    imageView.setImageBitmap(bitmap);
//                }
//                mImageCache.put(url,bitmap);
//            }
//        });
//    }
//    public void useDiskCache(boolean useDiskCache){
//    isUseDiskCache = useDiskCache;
//}
//    public void useDoubleCache(boolean useDoubleCache){
//        isUseDiskCache = useDoubleCache;
//    }
    //图片缓存
    ImageCache mImageCache = new MemoryCache();
    //线程池，线程数量为CPU的数量
    ExecutorService mExecutorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //注入缓存实现
    public void setImageCache(ImageCache cache){
        mImageCache = cache;
    }
    public void displayImage(String imageUrl, ImageView imageView){
        Bitmap bitmap = mImageCache.get(imageUrl);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        //图片没有缓存,提交到线程池中下载图片
        submitLoadRequest(imageUrl, imageView);
    }

    private void submitLoadRequest(final String imageUrl,final ImageView imageView){
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if(bitmap == null){
                    return;
                }
                if(imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(imageUrl,bitmap);
            }
        });
    }

    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap = null;
        try{
            URL url=new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
