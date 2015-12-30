package com.github.diluka.crtticketprice;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.github.diluka.crtticketprice.adapter.StationAdapter;
import com.github.diluka.crtticketprice.dao.DatabaseHelper;
import com.github.diluka.crtticketprice.entity.Line;
import com.github.diluka.crtticketprice.entity.Station;
import com.github.diluka.crtticketprice.entity.Ticket;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private static final String TAG = "MainActivity";

    @StringRes
    String data_not_found, search_error, initialization,start_to_search;

    @ViewById
    TextView tvResult;
    @ViewById
    AutoCompleteTextView etStation1, etStation2;

    @Bean
    StationAdapter stationAdapter1;
    @Bean
    StationAdapter stationAdapter2;

    @OrmLiteDao(helper = DatabaseHelper.class)
    RuntimeExceptionDao<Line, String> lineDAO;
    @OrmLiteDao(helper = DatabaseHelper.class)
    RuntimeExceptionDao<Station, String> stationDAO;
    @OrmLiteDao(helper = DatabaseHelper.class)
    RuntimeExceptionDao<Ticket, Integer> ticketDAO;


    @AfterViews
    void init() {
        updateResultUI(initialization);
        lineDAO.countOf();
        updateResultUI(start_to_search);
        etStation1.setAdapter(stationAdapter1);
        etStation2.setAdapter(stationAdapter2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Background
    void doSearch(String s1, String s2) {
        try {

            Long price=ticketDAO.queryRawValue("SELECT a.price FROM ticket a JOIN station b1 ON a.station_id_1=b1.station_id JOIN station b2 ON a.station_id_2=b2.station_id WHERE b1.station_name=? and b2.station_name=? LIMIT 1", s1, s2);


            if (price != null) {
                updateResultUI("￥ %d", price);
            } else {
                updateResultUI(data_not_found);
            }

        } catch (Exception e) {
            updateResultUI(search_error);
        }
    }

    @UiThread
    void updateResultUI(String tpl, Object... objs) {
        tvResult.setText(String.format(tpl, objs));
    }


    @Click({R.id.btn_ok})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                doSearch(etStation1.getText().toString(), etStation2.getText().toString());
                break;
        }
    }

}
