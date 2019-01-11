package com.example.michel.mobile_dev_application_week_5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView mQuoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set text
        mQuoteTextView= findViewById(R.id.quote_message);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    private void requestData(){
        NumbersApiService service = NumbersApiService.retrofit.create(NumbersApiService.class);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; //Calendar.MONTH starts at zero
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        Call<DayQuoteItem> call = service.getTodaysQuote(month, dayOfMonth);
        call.enqueue(new Callback<DayQuoteItem>() {

            @Override
            public void onResponse(Call<DayQuoteItem> call, Response<DayQuoteItem> response) {
                DayQuoteItem dayQuoteItem = response.body();
                setQuoteTextView(dayQuoteItem.getText());
            }

            @Override
            public void onFailure(Call<DayQuoteItem> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }

    public void setQuoteTextView(String quoteMessage) {
        mQuoteTextView.setText(quoteMessage);
    }
}


