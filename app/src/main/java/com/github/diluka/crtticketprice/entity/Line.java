package com.github.diluka.crtticketprice.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Objects;

@DatabaseTable(tableName = "line")
public class Line implements Serializable {

    public static final String LINE_ID_FIELD_NAME = "line_id";
    public static final String LINE_NAME_FIELD_NAME = "line_name";
    public static final String LINE_NAME_FULL_PINYIN_FIELD_NAME = "line_name_full_pinyin";
    public static final String LINE_NAME_SHORT_PINYIN_FIELD_NAME = "line_name_short_pinyin";

    @DatabaseField(id = true, columnName = LINE_ID_FIELD_NAME)
    public String lineId;
    @DatabaseField(index = true, columnName = LINE_NAME_FIELD_NAME)
    public String lineName;
    @DatabaseField(index = true, columnName = LINE_NAME_FULL_PINYIN_FIELD_NAME)
    public String lineNameFullPinyin;
    @DatabaseField(index = true, columnName = LINE_NAME_SHORT_PINYIN_FIELD_NAME)
    public String lineNameShortPinyin;

    @ForeignCollectionField
    public ForeignCollection<Station> stations;

    public Line() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(lineId, line.lineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineId);
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineNameFullPinyin() {
        return lineNameFullPinyin;
    }

    public void setLineNameFullPinyin(String lineNameFullPinyin) {
        this.lineNameFullPinyin = lineNameFullPinyin;
    }

    public String getLineNameShortPinyin() {
        return lineNameShortPinyin;
    }

    public void setLineNameShortPinyin(String lineNameShortPinyin) {
        this.lineNameShortPinyin = lineNameShortPinyin;
    }

    public ForeignCollection<Station> getStations() {
        return stations;
    }

    public void setStations(ForeignCollection<Station> stations) {
        this.stations = stations;
    }
}
