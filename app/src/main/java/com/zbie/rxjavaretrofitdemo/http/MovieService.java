package com.zbie.rxjavaretrofitdemo.http;

import com.zbie.rxjavaretrofitdemo.entity.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 涛 on 2017/1/16 0016.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.http
 * 创建时间         2017/01/16 20:42
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            只用Retrofit
 */
public interface MovieService {

    //https://api.douban.com/v2/movie/top250?start=0&count=10

    String BASE_URL = "https://api.douban.com/v2/movie/";

    @GET("top250")
    Call<MovieEntity> getTop250Movice(@Query("start") int start, @Query("count") int count);
}
