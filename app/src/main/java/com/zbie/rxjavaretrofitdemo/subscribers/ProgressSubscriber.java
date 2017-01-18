package com.zbie.rxjavaretrofitdemo.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.zbie.rxjavaretrofitdemo.progress.ProgressCancelListener;
import com.zbie.rxjavaretrofitdemo.progress.ProgressDialogHandler;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by 涛 on 2017/1/17 0017.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.subscribers
 * 创建时间         2017/01/17 00:26
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            带DialogProgress的订阅者
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler    mProgressDialogHandler;// 处理取消请求
    private Context                  mContext;

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        mSubscriberOnNextListener = subscriberOnNextListener;
        mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        //super.onStart();
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(mContext, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        //Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(mContext, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     * 取消http请求 TODO
     * 取消http请求
     * 取消http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }
}
