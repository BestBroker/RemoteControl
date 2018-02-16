package com.example.felix.remotecontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RemoteViews;

import static android.content.ContentValues.TAG;

/**
 * Created by Felix on 16.02.2018.
 */

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {

    ConsumerIrManager mCIR;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        mCIR = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
        if (!mCIR.hasIrEmitter()) {
            Log.e(TAG, "No IR Emitter found!\n");
            return;
        }

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, AppWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            views.setOnClickPendingIntent(R.id.send_codes, pendingIntent);
        }




    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void send_codes (){
        int [] Array;
        Array = new int[2];
        Array[0] =(2);
        Array[1]=(3);
        mCIR.transmit(22,Array);
    }


}
