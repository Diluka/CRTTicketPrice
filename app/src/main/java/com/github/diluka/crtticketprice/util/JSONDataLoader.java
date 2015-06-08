package com.github.diluka.crtticketprice.util;

import android.content.Context;
import android.util.Log;

import com.github.diluka.crtticketprice.entity.TicketPrice;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * JSON数据读取器
 *
 * @author Diluka
 */
public class JSONDataLoader implements Closeable {

    public static final String TAG = "JSONDataLoader";


    private Context context;
    private final int rawId;

    private InputStream inputStream;
    private GZIPInputStream gzipInputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private JSONArray array;
    private int index=0;

    public void Open() throws IOException, JSONException {
        inputStream=context.getResources().openRawResource(rawId);
        gzipInputStream=new GZIPInputStream(inputStream);
        inputStreamReader=new InputStreamReader(gzipInputStream);
        reader=new BufferedReader(inputStreamReader);

        StringBuilder sb=new StringBuilder();
        String line=reader.readLine();
        while(line!=null){
            sb.append(line);
        }

        JSONTokener tokener=new JSONTokener(sb.toString());
        array= (JSONArray) tokener.nextValue();
    }

    public JSONDataLoader(Context ctx,int rawId) {
        this.context = ctx;
        this.rawId=rawId;
    }

    public TicketPrice nextObject(){
        try {
            JSONObject jsonObject = array.getJSONObject(index++);
            TicketPrice ticketPrice=new TicketPrice();
            ticketPrice.setLineId1(jsonObject.getString("lineId1"));
            ticketPrice.setLineId2(jsonObject.getString("lineId2"));
            ticketPrice.setLineName1(jsonObject.getString("lineName1"));
            ticketPrice.setLineName2(jsonObject.getString("lineName2"));
            ticketPrice.setStationId1(jsonObject.getString("stationId1"));
            ticketPrice.setStationId2(jsonObject.getString("stationId2"));
            ticketPrice.setStationName1(jsonObject.getString("stationName1"));
            ticketPrice.setStationName2(jsonObject.getString("stationName2"));
            ticketPrice.setPrice(jsonObject.getInt("price"));

            ticketPrice.setLineName1Pinyin(PinyinHelper.convertToPinyinString(ticketPrice.getLineName1(), "", PinyinFormat.WITHOUT_TONE));
            ticketPrice.setLineName2Pinyin(PinyinHelper.convertToPinyinString(ticketPrice.getLineName2(), "", PinyinFormat.WITHOUT_TONE));
            ticketPrice.setStationName1Pinyin(PinyinHelper.convertToPinyinString(ticketPrice.getStationName1Pinyin(), "", PinyinFormat.WITHOUT_TONE));
            ticketPrice.setStationName2Pinyin(PinyinHelper.convertToPinyinString(ticketPrice.getStationName2Pinyin(), "", PinyinFormat.WITHOUT_TONE));

            return ticketPrice;
        } catch (JSONException e) {
            return null;
        }
    }

    public <T> T loadJson(Class<T> clazz, int rawId) {
        Gson gson = new Gson();
        T t = null;

        try (InputStreamReader reader = new InputStreamReader(new GZIPInputStream(context.getResources().openRawResource(rawId)))) {
            t = gson.fromJson(reader, clazz);
        } catch (IOException e) {
            Log.e(TAG, "文件读取错误");
        }

        return t;

    }

    @Override
    public void close() throws IOException {
        if(reader!=null){
            reader.close();
        }
        if(inputStreamReader!=null){
            inputStreamReader.close();
        }
        if (gzipInputStream!=null){
            gzipInputStream.close();
        }
        if (inputStream!=null){
            inputStream.close();
        }
    }
}
