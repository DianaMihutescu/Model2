package ro.pub.cs.systems.eim.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServicePractic extends Service {
    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String sablon = intent.getStringExtra("val1");

        processingThread = new ProcessingThread(this, sablon);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
