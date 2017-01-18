package com.zbie.rxjavaretrofitdemo.entity;

/**
 * Created by 涛 on 2017/1/16 0016.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.entity
 * 创建时间         2017/01/16 22:07
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            HttpResult就相当于一个包装类
 */

public class HttpResult<T> {

    //用来模仿resultCode和resultMessage(相同格式的数据)
    public int    count;
    public int    start;
    public int    totle;
    public String title;

    //用来模仿Data(不同的数据用泛型)
    public T subjects;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HttpResult---").append("title=").append(title).append(" count=").append(count).append(" start=").append(start);
        if (null != subjects) {
            sb.append(" subjects:").append(subjects.toString()).append("应该是一个对象哈希值");
        }
        return sb.toString();
    }

}
