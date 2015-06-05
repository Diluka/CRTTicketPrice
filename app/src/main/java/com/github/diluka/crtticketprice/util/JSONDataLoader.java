package com.github.diluka.crtticketprice.util;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * JSON数据读取器
 * @author Diluka
 */
public class JSONDataLoader {

    public static final String TAG = "JSONDataLoader";

    private Context context;

    public JSONDataLoader(Context ctx) {
        this.context = ctx;
    }

    public <T> T loadJson(Class<T> clazz, int resId) {
        Gson gson = new Gson();

        try (InputStreamReader reader = new InputStreamReader(new GZIPInputStream(context.getResources().openRawResource(resId)))) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            Log.e(TAG, "文件读取错误");
        }

        return null;

    }
}
