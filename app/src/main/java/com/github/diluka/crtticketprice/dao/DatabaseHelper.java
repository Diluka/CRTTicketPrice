package com.github.diluka.crtticketprice.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.diluka.crtticketprice.R;
import com.github.diluka.crtticketprice.entity.TicketPrice;
import com.github.diluka.crtticketprice.util.JSONDataLoader;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
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
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context=context;
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
        TicketPrice[] ticketPrices = dataLoader.loadJson(TicketPrice[].class, R.raw.data);

        RuntimeExceptionDao<TicketPrice,Integer> dao=getRuntimeExceptionDao(TicketPrice.class);
        for (TicketPrice tp:ticketPrices){
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
}
