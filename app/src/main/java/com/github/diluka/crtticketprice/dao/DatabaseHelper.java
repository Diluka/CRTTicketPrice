package com.github.diluka.crtticketprice.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.diluka.crtticketprice.R;
import com.github.diluka.crtticketprice.entity.TicketPrice;
import com.github.diluka.crtticketprice.util.JSONDataLoader;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 数据库辅助类
 * @author Diluka
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String TAG="DatabaseHelper";

    private Context context;

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "CRTTicketPrice.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2;

    // the DAO object we use to access the SimpleData table
    private Dao<TicketPrice, Integer> simpleDao = null;
    private RuntimeExceptionDao<TicketPrice, Integer> simpleRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context=context;
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<TicketPrice,Integer> getDao()throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(TicketPrice.class);
        }
        return simpleDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<TicketPrice, Integer> getTicketPriceDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(TicketPrice.class);
        }
        return simpleRuntimeDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        long start=System.currentTimeMillis();
        Log.i(TAG,"创建数据库...");

        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, TicketPrice.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        //TODO 从data.json中导入初始化数据
        Log.i(TAG,"初始化数据库...");
        JSONDataLoader dataLoader=new JSONDataLoader(context);
        TicketPrice[] ticketPrices = dataLoader.loadJson(TicketPrice[].class, R.raw.data_json_gz);

        RuntimeExceptionDao<TicketPrice,Integer> dao=getRuntimeExceptionDao(TicketPrice.class);
        for (TicketPrice tp:ticketPrices){
            tp.lineName1Pinyin=PinyinHelper.convertToPinyinString(tp.lineName1,"", PinyinFormat.WITHOUT_TONE);
            tp.lineName2Pinyin=PinyinHelper.convertToPinyinString(tp.lineName2,"", PinyinFormat.WITHOUT_TONE);
            tp.stationName1Pinyin=PinyinHelper.convertToPinyinString(tp.stationName1,"", PinyinFormat.WITHOUT_TONE);
            tp.stationName2Pinyin=PinyinHelper.convertToPinyinString(tp.stationName2,"", PinyinFormat.WITHOUT_TONE);

            if(dao.create(tp)!=1){
                Log.i(TAG,"插入数据失败："+tp);
            }
        }

        Log.i(TAG,String.format("初始化数据库完成，耗时：%d ms",System.currentTimeMillis()-start));
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        Log.i(TAG,"更新数据库...");
        //版本不对就删了重来
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, TicketPrice.class, true);
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
        simpleDao = null;
        simpleRuntimeDao = null;
    }
}
