package com.imdb.ui.mvp;

/**
 * Created by pranavbhoraskar on 8/15/16.
 */
public interface Presenter<T extends ViewInterface> {

    void attachView(T t);

    void detachView();
}