package ro.pub.cs.systems.eim.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class ProcessingThread extends Thread{

    private String sablon = null;
    private String[] bucatiSablon= null;
    private boolean isRunning = true;
    private Context context = null;
    private Random random = new Random();

    public ProcessingThread(Context context, String sablon) {
        this.context = context;
        this.sablon = sablon;

        int size = sablon.length() - sablon.replace(",", "").length();
        bucatiSablon = sablon.split(",");
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            for (int i = 1; i < bucatiSablon.length; i ++) {

                sendMessage(bucatiSablon[i]);
                sleep();
            }
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage(String deTrimis) {
        Intent intent = new Intent();

        intent.setAction("actionRandom");
        intent.putExtra("message", deTrimis);
        context.sendBroadcast(intent);

    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


    public void stopThread() {
        isRunning = false;
    }
}
