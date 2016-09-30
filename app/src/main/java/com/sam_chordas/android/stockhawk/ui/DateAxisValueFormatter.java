package com.sam_chordas.android.stockhawk.ui;

import com.github.mikephil.charting.formatter.AxisValueFormatter;


import com.github.mikephil.charting.components.AxisBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAxisValueFormatter implements AxisValueFormatter
{

    private long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public DateAxisValueFormatter(long referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.mDate = new Date();
    }


    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) value;

        // Retrieve original timestamp
        long originalTimestamp = referenceTimestamp + convertedTimestamp;

        // Convert timestamp to date
        return getDate(originalTimestamp);
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }

    private String getDate(long timestamp){
        try{
            mDate.setTime(timestamp);
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "";
        }
    }
}