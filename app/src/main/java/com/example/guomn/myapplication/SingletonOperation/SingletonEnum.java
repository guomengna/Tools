package com.example.guomn.myapplication.SingletonOperation;

/**
 * Created by guomn on 2018/9/26.
 * 通过枚举实现单例模式
 * 其优点是即使经过反序列化也不会重新生成对象
 */

public enum SingletonEnum{
    INSTACE;
    //实例化的单例类型为Rsource
    private Resouce instance;
    SingletonEnum(){
        instance = new Resouce();
    }
    //暴露在外的获取单例的方法
    public Resouce getInstance(){
        return instance;
    }
    public void doSomeThing(){
        System.out.println("do sth.");
    }
}
