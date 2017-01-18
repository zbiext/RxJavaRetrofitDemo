package com.zbie.rxjavaretrofitdemo.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by 涛 on 2017/1/17 0017.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.progress
 * 创建时间         2017/01/17 00:32
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG    = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Context                mContext;
    private ProgressCancelListener mProgressCancelListener;
    private boolean                cancelable;

    private ProgressDialog pd;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.mContext = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(mContext);
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }
}
