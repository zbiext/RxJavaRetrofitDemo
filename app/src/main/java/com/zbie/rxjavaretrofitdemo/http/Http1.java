package com.zbie.rxjavaretrofitdemo.http;

import com.zbie.rxjavaretrofitdemo.entity.HttpResult;
import com.zbie.rxjavaretrofitdemo.entity.Subject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 涛 on 2017/1/16 0016.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.http
 * 创建时间         2017/01/16 22:14
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            Http 再次改造
 */

public enum Http1 {

    INSTANCE;

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5000;

    private HttpMethods instance;

    Http1() { //在访问INSTANCE时创建单例
        instance = new HttpMethods();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public HttpMethods getInstance() {
        return instance;
    }

    public class HttpMethods {

        private Retrofit      retrofit;
        //private MovieService1 movieService1;
        private MovieService2 movieService2;

        private HttpMethods() {
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

            retrofit = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    //.addConverterFactory(GsonConverterFactory.create())
                    //对http请求结果进行统一的预处理 GosnResponseBodyConvert
                    .addConverterFactory(ResponseConvertFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
            //movieService1 = retrofit.create(MovieService1.class);
            movieService2 = retrofit.create(MovieService2.class);
        }

        /**
         * 用于获取豆瓣电影Top250的数据
         *
         * @param subscriber 由调用者传过来的观察者对象
         * @param start      起始位置
         * @param count      获取长度
         */
        public void getTop250Movice(Subscriber<HttpResult<List<Subject>>> subscriber, int start, int count) {
            movieService2.getTop250Movice(start, count)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

        /**
         * 用于获取豆瓣电影Top250的数据
         * 请求数据统一进行预处理
         *
         * @param subscriber 由调用者传过来的观察者对象
         * @param start      起始位置
         * @param count      获取长度
         */
        public void getTop250Movice1(Subscriber<List<Subject>> subscriber, int start, int count) {
            movieService2.getTop250Movice(start, count)
                    .map(new HttpResultFunc<List<Subject>>()) // getTop250Movice 与 getTop250Movice1的区别：就在这句
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

        /**
         * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
         * 请求数据统一进行预处理
         *
         * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
         */
        private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
            @Override
            public T call(HttpResult<T> tHttpResult) {
                if (tHttpResult.count == 0) {
                    throw new ApiException(100);
                }
                return tHttpResult.subjects;
            }
        }
    }
}
