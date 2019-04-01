package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private double sum;
    private double dif;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        sum = firstNumber + secondNumber;
        dif = firstNumber - secondNumber;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessageSum();
            sleep();
            sendMessageDif();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessageSum() {
        Intent intent = new Intent();
        intent.setAction("sum");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + sum);
        context.sendBroadcast(intent);
    }

    private void sendMessageDif() {
        Intent intent = new Intent();
        intent.setAction("dif");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + dif);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
