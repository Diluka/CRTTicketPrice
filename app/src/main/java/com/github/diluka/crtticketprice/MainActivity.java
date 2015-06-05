package com.github.diluka.crtticketprice;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.github.diluka.crtticketprice.dao.DatabaseHelper;
import com.github.diluka.crtticketprice.entity.TicketPrice;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;


public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private static final String TAG = "MainActivity";

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        checkDatabase(tvResult);
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

    private void checkDatabase(TextView tv){
        RuntimeExceptionDao<TicketPrice,Integer> dao=getHelper().getRuntimeExceptionDao(TicketPrice.class);
        long count=dao.countOf();
        tv.setText(String.format(getString(R.string.db_result_tpl),count));
    }

    private void bindComponents(){
        tvResult= (TextView) findViewById(R.id.tvResult);
    }
}
