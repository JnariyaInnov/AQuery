package com.aquery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Toast;

import com.aquery.query.QueryNetwork;
import com.aquery.query.QueryView;
import com.aquery.utils.Loader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ocittwo on 11/11/17.
 */

public class AQuery {

    private Context context;
    private View rootView;
    private AlertDialog.Builder alert;
    private QueryView queryView;
    private QueryNetwork queryNetwork;
    private Loader loader;
    private SharedPreferences pref;
    private Gson gson;

    public AQuery(Context context) {
        this.context = context;
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        queryView = new QueryView(context);
        queryNetwork = new QueryNetwork(context);
        alert = new AlertDialog.Builder(context);
        loader = new Loader(context);
        pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    public QueryView id(@IdRes int id) {
        View image = rootView.findViewById(id);
        return queryView.setView(image);
    }

    public QueryNetwork ajax(String url) {
        return queryNetwork.setUrl(url);
    }

    public void alert(String messages) {
        alert.setTitle("Message");
        alert.setMessage(messages);
        alert.setPositiveButton("Done", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public void toast(String messages) {
        Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
    }

    public void runOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public void showPopupLoading() {
        loader.hide();
    }

    public void hidePopupLoadin() {
        loader.hide();
    }

    public void putString(String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    public String grabString(String key) {
        return pref.getString(key, null);
    }

    public void open(Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public void openFromRight(Class clazz) {
        open(clazz);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void openFromLeft(Class clazz) {
        open(clazz);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void closeToRight() {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void closeToLeft() {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public <T> T toObject(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

}