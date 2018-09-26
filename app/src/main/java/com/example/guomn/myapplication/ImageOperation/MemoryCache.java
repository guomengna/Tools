package com.example.guomn.myapplication.ImageOperation;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by guomn on 2018/9/26.
 */
//内存缓存类
public class MemoryCache implements ImageCache{
    private LruCache<String, Bitmap> mMemoryCache;
    public MemoryCache(){
        //初始化LRU缓存
        initImageCache();
    }
    private void initImageCache(){
        //计算可使用的最大内存
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        //取四分之一的可用内存作为缓存
        final int cacheSize = maxMemory/4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }
    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bmp) {
        mMemoryCache.put(url,bmp);
    }
}
