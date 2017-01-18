package com.zbie.rxjavaretrofitdemo.entity;

import java.util.List;

/**
 * Created by 涛 on 2017/1/16 0016.
 * 项目名           RxJavaRetrofitDemo
 * 包名             com.zbie.rxjavaretrofitdemo.entity
 * 创建时间         2017/01/16 20:44
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class MovieEntity {

    public int    count;
    public int    start;
    public int    totle;
    public String title;

    public List<Subject> subjects;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MovieEntity---").append("title=").append(title).append(" count=").append(count).append(" start=").append(start);
        if (null != subjects) {
            sb.append(" subjects:").append(subjects.toString());
        }
        return sb.toString();
    }

    private class Subject {

        public String       id;
        public String       alt;
        public String       year;
        public String       title;
        public String       original_title;
        public List<String> genres;
        public List<Cast>   casts;
        public List<Cast>   directors;
        public Avatars      images;

        @Override
        public String toString() {
            return "Subject.id=" + id
                    + " Subject.title=" + title
                    + " Subject.year=" + year
                    + " Subject.originalTitle=" + original_title + casts.toString() + directors.toString() + " | ";
        }
    }

    private class Cast {
        public String  id;
        public String  name;
        public String  alt;
        public Avatars avatars;

        @Override
        public String toString() {
            return "cast.id=" + id + " cast.name=" + name + " | " + "cast.alt=" + alt + avatars.toString();
        }
    }

    private class Avatars {
        public String small;
        public String medium;
        public String large;

        @Override
        public String toString() {
            return "avatars.small" + small + "avatars.medium" + medium + "avatars.medium" + large;
        }
    }
}
