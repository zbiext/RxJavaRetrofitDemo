package com.zbie.rxjavaretrofitdemo.http;

import android.util.Log;

import com.google.gson.Gson;
import com.zbie.rxjavaretrofitdemo.entity.HttpResult;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by 涛 on 2017/1/18 0018.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.http
 * 创建时间         2017/01/18 22:28
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("Network", "response>>>" + response);
        //httpResult 只解析result字段
        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
        //
        if (httpResult.count == 0) {
            throw new ApiException(100);
        }
        return gson.fromJson(response, type);
    }
}
