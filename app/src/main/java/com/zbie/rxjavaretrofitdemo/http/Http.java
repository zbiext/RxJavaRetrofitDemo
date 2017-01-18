package com.zbie.rxjavaretrofitdemo.http;

import com.zbie.rxjavaretrofitdemo.entity.MovieEntity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 涛 on 2017/1/16 0016.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.http
 * 创建时间         2017/01/16 21:42
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            TODO
 */

public enum Http {

    INSTANCE;

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5000;

    private HttpMethods instance;

    Http() { //在访问INSTANCE时创建单例
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

        private Retrofit retrofit;
        private MovieService1 movieService;

        private HttpMethods() {
            //手动创建一个OkHttpClient，设置超时时间5s
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

            retrofit = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
            movieService = retrofit.create(MovieService1.class);
        }

        /**
         * 用于获取豆瓣电影Top250的数据
         *
         * @param subscriber 由调用者(如:activity、fragment)传过来的观察者对象
         * @param start      起始位置
         * @param count      获取长度
         */
        public void getTop250Movice(Subscriber<MovieEntity> subscriber, int start, int count) {
            movieService.getTop250Movice(start, count)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }
}
