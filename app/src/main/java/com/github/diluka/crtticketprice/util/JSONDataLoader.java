package com.github.diluka.crtticketprice.util;

import android.content.Context;
import android.util.Log;

import com.github.diluka.crtticketprice.entity.Line;
import com.github.diluka.crtticketprice.entity.Station;
import com.github.diluka.crtticketprice.entity.Ticket;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.EBean;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * JSON数据读取器
 *
 * @author Diluka
 */
public class JSONDataLoader {

    public static final String TAG = "JSONDataLoader";


    private Context context;

    public JSONDataLoader(Context context) {
        this.context = context;
    }

    public Map<String, Collection> loadJson(int rawId) {
        Gson gson = new Gson();
        Map<String, Collection> map = new HashMap<>();

        try (InputStreamReader reader = new InputStreamReader(new GZIPInputStream(context.getResources().openRawResource(rawId)))) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            Map<String, Line> lines = new HashMap<>();
            Map<String, Station> stations = new HashMap<>();
            List<Ticket> tickets = new ArrayList<>();

            for (JsonElement lineJson : jsonObject.get("lines").getAsJsonArray()) {
                Line line = new Line();
                line.lineName = lineJson.getAsJsonObject().get("lineName").getAsString();
                line.lineId = lineJson.getAsJsonObject().get("lineId").getAsString();
                line.lineNameFullPinyin = PinyinHelper.convertToPinyinString(line.lineName, "", PinyinFormat.WITHOUT_TONE);
                line.lineNameShortPinyin = PinyinHelper.getShortPinyin(line.lineName);
                lines.put(line.lineId, line);
            }

            for (JsonElement stationJson : jsonObject.get("stations").getAsJsonArray()) {
                Station station = new Station();
                station.stationId = stationJson.getAsJsonObject().get("stationId").getAsString();
                station.stationName = stationJson.getAsJsonObject().get("stationName").getAsString();
                station.stationNameFullPinyin = PinyinHelper.convertToPinyinString(station.stationName, "", PinyinFormat.WITHOUT_TONE);
                station.stationNameShortPinyin = PinyinHelper.getShortPinyin(station.stationName);
                station.line = lines.get(stationJson.getAsJsonObject().get("lineId").getAsString());
                stations.put(station.stationId, station);
            }

            for (JsonElement ticketJson : jsonObject.get("tickets").getAsJsonArray()) {
                Ticket ticket = new Ticket();
                ticket.line1 = lines.get(ticketJson.getAsJsonObject().get("lineId1").getAsString());
                ticket.line2 = lines.get(ticketJson.getAsJsonObject().get("lineId2").getAsString());
                ticket.station1 = stations.get(ticketJson.getAsJsonObject().get("stationId1").getAsString());
                ticket.station2 = stations.get(ticketJson.getAsJsonObject().get("stationId2").getAsString());
                ticket.price = ticketJson.getAsJsonObject().get("price").getAsInt();
                tickets.add(ticket);
            }

            map.put("lines", lines.values());
            map.put("stations", stations.values());
            map.put("tickets", tickets);

        } catch (IOException e) {
            Log.e(TAG, "文件读取错误");
        }

        return map;

    }
}
