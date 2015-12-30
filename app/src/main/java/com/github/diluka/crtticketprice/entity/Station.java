package com.github.diluka.crtticketprice.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Objects;

@DatabaseTable(tableName = "station")
public class Station implements Serializable {

    public static final String LINE_ID_FIELD_NAME = "line_id";
    public static final String STATION_ID_FIELD_NAME = "station_id";
    public static final String STATION_NAME_FIELD_NAME = "station_name";
    public static final String STATION_NAME_FULL_PINYIN_FIELD_NAME = "station_name_full_pinyin";
    public static final String STATION_NAME_SHORT_PINYIN_FIELD_NAME = "station_name_short_pinyin";

    @DatabaseField(id = true, columnName = STATION_ID_FIELD_NAME)
    public String stationId;
    @DatabaseField(index = true, columnName = STATION_NAME_FIELD_NAME)
    public String stationName;
    @DatabaseField(index = true, columnName = STATION_NAME_FULL_PINYIN_FIELD_NAME)
    public String stationNameFullPinyin;
    @DatabaseField(index = true, columnName = STATION_NAME_SHORT_PINYIN_FIELD_NAME)
    public String stationNameShortPinyin;
//    @DatabaseField(columnName = LINE_ID_FIELD_NAME)
//    public String lineId;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = LINE_ID_FIELD_NAME)
    public Line line;

    @ForeignCollectionField
    public ForeignCollection<Ticket> tickets;

    public Station() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(stationId, station.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId);
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getStationNameFullPinyin() {
        return stationNameFullPinyin;
    }

    public void setStationNameFullPinyin(String stationNameFullPinyin) {
        this.stationNameFullPinyin = stationNameFullPinyin;
    }

    public String getStationNameShortPinyin() {
        return stationNameShortPinyin;
    }

    public void setStationNameShortPinyin(String stationNameShortPinyin) {
        this.stationNameShortPinyin = stationNameShortPinyin;
    }

    public ForeignCollection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ForeignCollection<Ticket> tickets) {
        this.tickets = tickets;
    }

}
