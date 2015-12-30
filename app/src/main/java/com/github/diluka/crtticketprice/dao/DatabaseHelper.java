package com.github.diluka.crtticketprice.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.diluka.crtticketprice.R;
import com.github.diluka.crtticketprice.entity.Line;
import com.github.diluka.crtticketprice.entity.Station;
import com.github.diluka.crtticketprice.entity.Ticket;
import com.github.diluka.crtticketprice.util.JSONDataLoader;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 数据库辅助类
 *
 * @author Diluka
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    private Context context;

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "CRTTicketPrice.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 6;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context = context;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        long start = System.currentTimeMillis();
        Log.i(TAG, "创建数据库...");

        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Line.class);
            TableUtils.createTable(connectionSource, Station.class);
            TableUtils.createTable(connectionSource, Ticket.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        //从data.json中导入初始化数据
        Log.i(TAG, "初始化数据库...");
        JSONDataLoader dataLoader = new JSONDataLoader(context);
        Map<String, Collection> map = dataLoader.loadJson(R.raw.data_json_gz);


        RuntimeExceptionDao<Line, Integer> lineDAO = getRuntimeExceptionDao(Line.class);
        for (Line line : (Collection<Line>) map.get("lines")) {
            if (lineDAO.create(line) != 1) {
                Log.i(TAG, "插入数据失败：" + line);
            }
        }
        RuntimeExceptionDao<Station, Integer> stationDAO = getRuntimeExceptionDao(Station.class);
        for (Station station : (Collection<Station>) map.get("stations")) {
            if (stationDAO.create(station) != 1) {
                Log.i(TAG, "插入数据失败：" + station);
            }
        }
        RuntimeExceptionDao<Ticket, Integer> ticketDAO = getRuntimeExceptionDao(Ticket.class);
        for (Ticket ticket : (Collection<Ticket>) map.get("tickets")) {
            if (ticketDAO.create(ticket) != 1) {
                Log.i(TAG, "插入数据失败：" + ticket);
            }
        }


        Log.i(TAG, String.format("初始化数据库完成，耗时：%f ms", (System.currentTimeMillis() - start) / 1000.0));
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        Log.i(TAG, "更新数据库...");
        //版本不对就删了重来
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Ticket.class, true);
            TableUtils.dropTable(connectionSource, Station.class, true);
            TableUtils.dropTable(connectionSource, Line.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }
}
