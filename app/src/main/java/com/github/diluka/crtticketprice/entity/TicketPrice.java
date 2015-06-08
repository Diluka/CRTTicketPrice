package com.github.diluka.crtticketprice.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

/**
 * 票价信息实体类
 */
@DatabaseTable(tableName = "ticket_price")
public class TicketPrice {

    //<editor-fold desc="字段名常量">
    public static final String ID_FIELD_NAME = "id";
    public static final String LINE_ID_1_FIELD_NAME = "line_id_1";
    public static final String LINE_NAME_1_FIELD_NAME = "line_name_1";
    public static final String LINE_NAME_1_PINYIN_FIELD_NAME = "line_name_1_pinyin";
    public static final String STATION_ID_1_FIELD_NAME = "station_id_1";
    public static final String STATION_NAME_1_FIELD_NAME = "station_name_1";
    public static final String STATION_NAME_1_PINYIN_FIELD_NAME = "station_name_1_pinyin";
    public static final String LINE_ID_2_FIELD_NAME = "line_id_2";
    public static final String LINE_NAME_2_FIELD_NAME = "line_name_2";
    public static final String LINE_NAME_2_PINYIN_FIELD_NAME = "line_name_2_pinyin";
    public static final String STATION_ID_2_FIELD_NAME = "station_id_2";
    public static final String STATION_NAME_2_FIELD_NAME = "station_name_2";
    public static final String STATION_NAME_2_PINYIN_FIELD_NAME = "station_name_2_pinyin";
    public static final String PRICE_FIELD_NAME = "price";
    //</editor-fold>

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @DatabaseField(index = true, indexName = "line_id_1_idx", columnName = "line_id_1")
    public String lineId1;
    @DatabaseField(columnName = "line_name_1")
    public String lineName1;
    @DatabaseField(columnName = "line_name_1_pinyin")
    public String lineName1Pinyin;
    @DatabaseField(index = true, indexName = "station_id_1_idx", columnName = "station_id_1")
    public String stationId1;
    @DatabaseField(columnName = "station_name_1")
    public String stationName1;
    @DatabaseField(columnName = "station_name_1_pinyin")
    public String stationName1Pinyin;

    @DatabaseField(index = true, indexName = "line_id_2_idx", columnName = "line_id_2")
    public String lineId2;
    @DatabaseField(columnName = "line_name_2")
    public String lineName2;
    @DatabaseField(columnName = "line_name_2_pinyin")
    public String lineName2Pinyin;
    @DatabaseField(index = true, indexName = "station_id_2_idx", columnName = "station_id_2")
    public String stationId2;
    @DatabaseField(columnName = "station_name_2")
    public String stationName2;
    @DatabaseField(columnName = "station_name_2_pinyin")
    public String stationName2Pinyin;

    @DatabaseField(columnName = "price", dataType = DataType.INTEGER, defaultValue = "0")
    public int price;

    public TicketPrice() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketPrice that = (TicketPrice) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //<editor-fold desc="属性">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLineId1() {
        return lineId1;
    }

    public void setLineId1(String lineId1) {
        this.lineId1 = lineId1;
    }

    public String getLineName1() {
        return lineName1;
    }

    public void setLineName1(String lineName1) {
        this.lineName1 = lineName1;
    }

    public String getStationId1() {
        return stationId1;
    }

    public void setStationId1(String stationId1) {
        this.stationId1 = stationId1;
    }

    public String getStationName1() {
        return stationName1;
    }

    public void setStationName1(String stationName1) {
        this.stationName1 = stationName1;
    }

    public String getLineId2() {
        return lineId2;
    }

    public void setLineId2(String lineId2) {
        this.lineId2 = lineId2;
    }

    public String getLineName2() {
        return lineName2;
    }

    public void setLineName2(String lineName2) {
        this.lineName2 = lineName2;
    }

    public String getStationId2() {
        return stationId2;
    }

    public void setStationId2(String stationId2) {
        this.stationId2 = stationId2;
    }

    public String getStationName2() {
        return stationName2;
    }

    public void setStationName2(String stationName2) {
        this.stationName2 = stationName2;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    public String getLineName1Pinyin() {
        return lineName1Pinyin;
    }

    public void setLineName1Pinyin(String lineName1Pinyin) {
        this.lineName1Pinyin = lineName1Pinyin;
    }

    public String getStationName1Pinyin() {
        return stationName1Pinyin;
    }

    public void setStationName1Pinyin(String stationName1Pinyin) {
        this.stationName1Pinyin = stationName1Pinyin;
    }

    public String getLineName2Pinyin() {
        return lineName2Pinyin;
    }

    public void setLineName2Pinyin(String lineName2Pinyin) {
        this.lineName2Pinyin = lineName2Pinyin;
    }

    public String getStationName2Pinyin() {
        return stationName2Pinyin;
    }

    public void setStationName2Pinyin(String stationName2Pinyin) {
        this.stationName2Pinyin = stationName2Pinyin;
    }

    //</editor-fold>
}
