package com.github.diluka.crtticketprice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.github.diluka.crtticketprice.R;
import com.github.diluka.crtticketprice.dao.DatabaseHelper;
import com.github.diluka.crtticketprice.entity.Line;
import com.github.diluka.crtticketprice.entity.LineColors;
import com.github.diluka.crtticketprice.entity.Station;
import com.github.diluka.crtticketprice.entity.Ticket;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.SystemService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@EBean
public class StationAdapter extends BaseAdapter implements Filterable {

    @OrmLiteDao(helper = DatabaseHelper.class)
    RuntimeExceptionDao<Line, String> lineDAO;
    @OrmLiteDao(helper = DatabaseHelper.class)
    RuntimeExceptionDao<Station, String> stationDAO;
    @OrmLiteDao(helper = DatabaseHelper.class)
    RuntimeExceptionDao<Ticket, Integer> ticketDAO;

    @SystemService
    LayoutInflater inflater;

    private final Context context;
    private StationFilter filter;
    private List<Station> stations;
    private long maxStations = 10;


    public StationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int position) {
        return stations.get(position).getStationName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_for_auto, null);
        }

        TextView tvLine = (TextView) convertView.findViewById(R.id.auto_item_line);
        TextView tvStation = (TextView) convertView.findViewById(R.id.auto_item_station);

        Station station = stations.get(position);

        tvLine.setText(station.getLine().getLineName());
        tvStation.setText(station.getStationName());
        convertView.setBackgroundColor(LineColors.getColor(station.getLine().getLineId()));


        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new StationFilter();
        }
        return filter;
    }

    public void setMaxStations(long maxStations) {
        this.maxStations = maxStations;
    }

    public long getMaxStations() {
        return maxStations;
    }

    private class StationFilter extends Filter {

        FilterResults results = new FilterResults();

        @SuppressWarnings("unchecked")
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            QueryBuilder<Station, String> sqb = stationDAO.queryBuilder();
            Where<Station, String> where = sqb.where();

            try {
                if (!TextUtils.isEmpty(constraint)) {

                    sqb.setWhere(
                            where.or(
                                    where.like(Station.STATION_NAME_FIELD_NAME, constraint + "%"),
                                    where.like(Station.STATION_NAME_FULL_PINYIN_FIELD_NAME, constraint + "%"),
                                    where.like(Station.STATION_NAME_SHORT_PINYIN_FIELD_NAME, constraint + "%")
                            )
                    );

                    sqb.orderBy(Station.LINE_ID_FIELD_NAME,true).orderBy(Station.STATION_ID_FIELD_NAME, true);

                    stations = sqb.distinct().limit(maxStations).query();

                } else {
                    stations = Collections.EMPTY_LIST;
                }
            } catch (SQLException e) {
                stations = Collections.EMPTY_LIST;
            }

            results.count = stations.size();
            results.values = stations;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
