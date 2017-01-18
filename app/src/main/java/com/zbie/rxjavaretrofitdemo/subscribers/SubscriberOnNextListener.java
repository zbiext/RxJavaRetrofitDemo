package com.zbie.rxjavaretrofitdemo.subscribers;

/**
 * Created by 涛 on 2017/1/17 0017.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.subscribers
 * 创建时间         2017/01/17 00:30
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
