package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.sam_chordas.android.stockhawk.DateAxisValueFormatter;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.historicaldata.HistoricalData;
import com.sam_chordas.android.stockhawk.historicaldata.Quote;
import com.sam_chordas.android.stockhawk.service.HistoricalDataService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sam_chordas.android.stockhawk.service.HistoricalDataService.retrofit;

/**
 * Created by v.maletskiy on 12.09.2016.
 */

public class StockChartActivity extends AppCompatActivity {

    final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        String symbol = getIntent().getStringExtra(getString(R.string.symbol_param));
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, -8);
        final Date startDate = c.getTime();
        TextView title = (TextView) findViewById(R.id.symbol);
        title.setText(symbol.toUpperCase());

        final LineChart chart = (LineChart) findViewById(R.id.chart);
        final List<Entry> entries = new ArrayList<>();


        HistoricalDataService service = retrofit.create(HistoricalDataService.class);

        StringBuilder sb = new StringBuilder();
        String query = String.format(Locale.US, "select * from yahoo.finance.historicaldata " +
                "where symbol = '%1$s' and endDate = '%2$tY-%2$tm-%2$td' and startDate = '%3$tY-%3$tm-%3$td'", symbol, now, startDate);

        Call call = service.getData(query, "store://datatables.org/alltableswithkeys", "json");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    HistoricalData data = (HistoricalData) response.body();
                    ArrayList<Quote> quotes = data.query.results.quote;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        float referenceTimestamp = startDate.getTime();

                        for (Quote q : quotes) {
                            Date d = sdf.parse(q.date);
                            float timestampNormalized = d.getTime() - referenceTimestamp;
                            entries.add(
                                    new Entry(
                                            timestampNormalized,
                                            Float.parseFloat(q.adjClose)
                                    )
                            );
                        }

                        Collections.sort(entries, new EntryXComparator());
                        AxisValueFormatter xAxisFormatter =
                                new DateAxisValueFormatter(((long) referenceTimestamp));
                        XAxis xAxis = chart.getXAxis();
                        xAxis.setLabelRotationAngle(-45);
                        xAxis.setValueFormatter(xAxisFormatter);
                        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.chartLabel));
                        LineData lineData = new LineData(dataSet);
                        chart.setDescription(getString(R.string.chart_description));
                        chart.setData(lineData);
                        chart.getXAxis().setTextColor(Color.WHITE);
                        chart.getAxisLeft().setTextColor(Color.WHITE);
                        chart.getAxisRight().setTextColor(Color.WHITE);
                        chart.getLineData().setValueTextColor(Color.WHITE);
                        chart.invalidate();
                    } catch (ParseException e) {
                        Log.v(LOG_TAG, e.getLocalizedMessage());
                    }

                } else {
                    try {
                        final String error = response.errorBody().string();
                    } catch (IOException e) {

                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
