package com.zbie.rxjavaretrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zbie.rxjavaretrofitdemo.R;
import com.zbie.rxjavaretrofitdemo.entity.HttpResult;
import com.zbie.rxjavaretrofitdemo.entity.MovieEntity;
import com.zbie.rxjavaretrofitdemo.entity.Subject;
import com.zbie.rxjavaretrofitdemo.http.Http;
import com.zbie.rxjavaretrofitdemo.http.Http1;
import com.zbie.rxjavaretrofitdemo.http.MovieService;
import com.zbie.rxjavaretrofitdemo.http.MovieService1;
import com.zbie.rxjavaretrofitdemo.subscribers.ProgressSubscriber;
import com.zbie.rxjavaretrofitdemo.subscribers.SubscriberOnNextListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   mButton1, mButton2, mButton3, mButton4, mButton5, mButton6;
    private TextView mResultTv;
    private SubscriberOnNextListener<List<Subject>> getTopMovieOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                mResultTv.setText(subjects.toString());
            }
        };

        mResultTv = (TextView) findViewById(R.id.result_TV);
        mButton1 = (Button) findViewById(R.id.OnlyRetrofit);
        mButton2 = (Button) findViewById(R.id.Retrofit_RxJava);
        mButton3 = (Button) findViewById(R.id.Retrofit_RxJava1);
        mButton4 = (Button) findViewById(R.id.Retrofit_RxJava2);
        mButton5 = (Button) findViewById(R.id.Retrofit_RxJava3);
        mButton6 = (Button) findViewById(R.id.Retrofit_RxJava4);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {//TODO ps: service的变化
        switch (v.getId()) {
            //1.只用Retrofit [MovieService__MovieEntity]
            case R.id.OnlyRetrofit:
                getMovieOnlyRetrofit(244, 1);
                break;

            //2.RxJava+Retrofit [MovieService1__MovieEntity]
            case R.id.Retrofit_RxJava:
                getMovieRxJavaRetrofit(245, 1);
                break;

            //3.RxJava+Retrofit, 请求过程进行封装Http(枚举单例模式) [MovieService1__MovieEntity]
            case R.id.Retrofit_RxJava1:
                //Http
                Http.INSTANCE.getInstance().getTop250Movice(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "轰轰...>>>Http(枚举单例模式)", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mResultTv.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        mResultTv.setText(movieEntity.toString());
                    }
                }, 246, 1);
                break;

            //4.RxJava+Retrofit, 相同格式的Http请求数据该如何封装 (HttpResult(相同数据和不相同数据)) [MovieService2__HttpResult]
            case R.id.Retrofit_RxJava2:
                //Http1
                Http1.INSTANCE.getInstance().getTop250Movice(new Subscriber<HttpResult<List<Subject>>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "轰轰...>>>Http(封装相同格式->subject)", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mResultTv.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<List<Subject>> listHttpResult) {
                        mResultTv.setText(listHttpResult.toString());
                    }
                }, 247, 1);
                break;

            //5.RxJava+Retrofit, 相同格式的Http请求数据统一进行预处理 (只有不同数据->subject) [MovieService2__Subject]
            case R.id.Retrofit_RxJava3:
                Http1.INSTANCE.getInstance().getTop250Movice1(new Subscriber<List<Subject>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "轰轰...>>>Http(封装相同格式->subject)", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mResultTv.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Subject> subjects) {
                        mResultTv.setText(subjects.toString());
                    }
                }, 248, 1);
                break;

            //6.RxJava+Retrofit, 如何取消一个Http请求+带DialogProgress (subject) [MovieService2__Subject]
            case R.id.Retrofit_RxJava4:
                Http1.INSTANCE.getInstance().getTop250Movice1(new ProgressSubscriber<List<Subject>>(getTopMovieOnNext, MainActivity.this), 251, 1);
                break;
        }
    }

    /**
     * 只使用Retrofit
     *
     * @param start
     * @param count
     */
    private void getMovieOnlyRetrofit(int start, int count) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getTop250Movice(start, count);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                mResultTv.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                mResultTv.setText(t.getMessage());
             }
        });
    }

    /**
     * Retrofit+RxJava
     *
     * @param start
     * @param count
     */
    private void getMovieRxJavaRetrofit(int start, int count) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieService1.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MovieService1 movieService1 = retrofit.create(MovieService1.class);
        movieService1.getTop250Movice(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "获得数据成功,^ _ ^", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        mResultTv.setText(e.getMessage());
                    }
                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        mResultTv.setText(movieEntity.toString());
                    }
                });
    }
}
