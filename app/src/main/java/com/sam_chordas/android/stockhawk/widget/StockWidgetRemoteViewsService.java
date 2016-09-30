package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteDatabase;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Utils;

/**
 * Created by v.maletskiy on 26.09.2016.
 */

public class StockWidgetRemoteViewsService extends RemoteViewsService {

    private String LOG_TAG = getClass().getSimpleName();

    private static final String[] QUOTE_COLUMNS = {
            QuoteDatabase.QUOTES + "." + QuoteColumns._ID,
            QuoteColumns.SYMBOL,
            QuoteColumns.BIDPRICE,
            QuoteColumns.CHANGE,
            QuoteColumns.PERCENT_CHANGE
    };

    static final int INDEX_QUOTE_ID = 0;
    static final int INDEX_QUOTE_SYMBOL = 1;
    static final int INDEX_QUOTE_BIDPRICE = 2;
    static final int INDEX_QUOTE_CHANGE = 3;
    static final int INDEX_QUOTE_PERCENT_CHANGE = 4;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }


                final long identityToken = Binder.clearCallingIdentity();

                data = getContentResolver().query(
                        QuoteProvider.Quotes.CONTENT_URI,
                        QUOTE_COLUMNS,
                        null,
                        null,
                        null
                );
                Binder.restoreCallingIdentity(identityToken);

            }

            @Override
            public void onDestroy() {
                if (null != data) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_list_item_layout);

                String symbol = data.getString(INDEX_QUOTE_SYMBOL);
                String bidPrice = data.getString(INDEX_QUOTE_BIDPRICE);
                String change = data.getString(INDEX_QUOTE_CHANGE);
                String percentChange = data.getString(INDEX_QUOTE_PERCENT_CHANGE);


                views.setTextViewText(R.id.widget_stock_symbol, symbol);
                views.setContentDescription(R.id.widget_stock_symbol, symbol);

                views.setTextViewText(R.id.widget_bid_price, bidPrice);
                views.setContentDescription(R.id.widget_bid_price, bidPrice);

                if (Utils.showPercent) {
                    views.setTextViewText(R.id.widget_change, percentChange);
                    views.setContentDescription(R.id.widget_change, percentChange);
                } else {
                    views.setTextViewText(R.id.widget_change, change);
                    views.setContentDescription(R.id.widget_change, change);
                }


                final Intent intent = new Intent(StockWidgetProvider.STOCK_ACTION);
                intent.putExtra(getString(R.string.symbol_param), symbol);
                views.setOnClickFillInIntent(R.id.widget_item, intent);
                return views;
            }


            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item_layout);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position)) return data.getLong(INDEX_QUOTE_ID);
                return position;
            }


            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    public StockWidgetRemoteViewsService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
