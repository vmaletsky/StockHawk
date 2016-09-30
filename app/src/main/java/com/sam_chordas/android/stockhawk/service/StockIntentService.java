package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.R;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

    public StockIntentService() {
        super(StockIntentService.class.getName());
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        StockTaskService stockTaskService = new StockTaskService(this);
        Bundle args = new Bundle();
        if (intent.getStringExtra(getString(R.string.tag_param)).equals(getString(R.string.add_tag))) {
            args.putString(getString(R.string.symbol_param),
                    intent.getStringExtra(getString(R.string.symbol_param)));
        }
        // We can call OnRunTask from the intent service to force it to run immediately instead of
        // scheduling a task.
        stockTaskService.onRunTask(
                new TaskParams(intent.getStringExtra(getString(R.string.tag_param)), args));
    }
}
