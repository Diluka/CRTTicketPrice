package com.github.diluka.crtticketprice.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Objects;

@DatabaseTable(tableName = "ticket")
public class Ticket implements Serializable {
    //<editor-fold desc="字段名常量">
    public static final String ID_FIELD_NAME = "id";
    public static final String LINE_ID_1_FIELD_NAME = "line_id_1";
    public static final String STATION_ID_1_FIELD_NAME = "station_id_1";
    public static final String LINE_ID_2_FIELD_NAME = "line_id_2";
    public static final String STATION_ID_2_FIELD_NAME = "station_id_2";
    public static final String PRICE_FIELD_NAME = "price";
    //</editor-fold>

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    public int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = LINE_ID_1_FIELD_NAME)
    public Line line1;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = STATION_ID_1_FIELD_NAME)
    public Station station1;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = LINE_ID_2_FIELD_NAME)
    public Line line2;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = STATION_ID_2_FIELD_NAME)
    public Station station2;

//    @DatabaseField(index = true, indexName = "line_id_1_idx", columnName = "line_id_1")
//    public String lineId1;
//    @DatabaseField(index = true, indexName = "station_id_1_idx", columnName = "station_id_1")
//    public String stationId1;
//
//    @DatabaseField(index = true, indexName = "line_id_2_idx", columnName = "line_id_2")
//    public String lineId2;
//    @DatabaseField(index = true, indexName = "station_id_2_idx", columnName = "station_id_2")
//    public String stationId2;


    @DatabaseField(columnName = PRICE_FIELD_NAME, dataType = DataType.INTEGER, defaultValue = "0")
    public int price;

    public Ticket() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Line getLine1() {
        return line1;
    }

    public void setLine1(Line line1) {
        this.line1 = line1;
    }

    public Station getStation1() {
        return station1;
    }

    public void setStation1(Station station1) {
        this.station1 = station1;
    }

    public Line getLine2() {
        return line2;
    }

    public void setLine2(Line line2) {
        this.line2 = line2;
    }

    public Station getStation2() {
        return station2;
    }

    public void setStation2(Station station2) {
        this.station2 = station2;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
