package com.github.diluka.crtticketprice.util;


import com.github.diluka.crtticketprice.entity.Line;
import com.github.diluka.crtticketprice.entity.Station;
import com.github.diluka.crtticketprice.entity.Ticket;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * DatabaseConfigUtl writes a configuration file to avoid using annotation processing in runtime which is very slow
 * under Android. This gains a noticeable performance improvement.
 *
 * The configuration file is written to /res/raw/ by default. More info at: http://ormlite.com/docs/table-config
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] CLASSES={Line.class, Station.class, Ticket.class};

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt",CLASSES);
    }
}
