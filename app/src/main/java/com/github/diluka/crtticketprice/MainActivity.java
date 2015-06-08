package com.github.diluka.crtticketprice;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.github.diluka.crtticketprice.dao.DatabaseHelper;
import com.github.diluka.crtticketprice.entity.TicketPrice;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;


public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    TextView tvResult;
    EditText etStation1, etStation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        findViewById(R.id.btn_ok).setOnClickListener(this);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unchecked")
    private void doSearch() {
        String s1 = etStation1.getText().toString();
        String s2 = etStation2.getText().toString();

        try {
            RuntimeExceptionDao<TicketPrice, Integer> dao = getHelper().getTicketPriceDao();
            QueryBuilder<TicketPrice, Integer> qb = dao.queryBuilder();
            Where<TicketPrice, Integer> where = qb.where();

            qb.setWhere(
                    where.and(
                            where.or(where.eq(TicketPrice.STATION_NAME_1_FIELD_NAME, s1),
                                    where.eq(TicketPrice.STATION_NAME_1_PINYIN_FIELD_NAME, s1.toLowerCase())
                            ),
                            where.or(where.eq(TicketPrice.STATION_NAME_2_FIELD_NAME, s2),
                                    where.eq(TicketPrice.STATION_NAME_2_PINYIN_FIELD_NAME, s2.toLowerCase())
                                    )
                    )
            );

            TicketPrice ticketPrice = qb.queryForFirst();

            if(ticketPrice!=null){
                tvResult.setText(ticketPrice.getPrice()+"");
            }else{
                tvResult.setText(getString(R.string.data_not_found));
            }

        } catch (SQLException e) {
            tvResult.setText(getString(R.string.search_error));
        }
    }

    private void checkDatabase(TextView tv) {
        RuntimeExceptionDao<TicketPrice, Integer> dao = getHelper().getTicketPriceDao();
        long count = dao.countOf();
        tv.setText(String.format(getString(R.string.db_result_tpl), count));

    }

    private void bindComponents() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        etStation1 = (EditText) findViewById(R.id.etStation1);
        etStation2 = (EditText) findViewById(R.id.etStation2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                doSearch();
                break;
        }
    }
}
