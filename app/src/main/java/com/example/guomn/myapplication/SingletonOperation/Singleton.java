package com.example.guomn.myapplication.SingletonOperation;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Created by guomn on 2018/9/26.
 * 静态内部类单例模式
 */
public class Singleton implements Serializable{
    //构造方法私有
    private Singleton(){}
    //获取单一实例的方法，也是本类暴露出来的唯一方法
    public static Singleton getInstance(){
        return SingletonHolder.sInstance;
    }
    /**
     * 静态内部类
     */
    private static class SingletonHolder{
        private static final Singleton sInstance = new Singleton();

    }
    //杜绝单例对象在反序列化的时候重新生成对象
    private Object readResolve() throws ObjectStreamException{
        return getInstance();
    }
}

