package com.tatvasoftassignment.assignment_9.AsycTask;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.util.ArrayList;

public abstract class BackGroundTask {

    protected Handler localHandler;
    protected Thread localThread;

    public void execute(File file) {
        this.localHandler = new Handler(Looper.getMainLooper());
        this.onPreExecute();

        this.localThread = new Thread(() -> {
            ArrayList<File> arrayList = BackGroundTask.this.doInBackground(file);

            BackGroundTask.this.localHandler.post(() -> BackGroundTask.this.onPostExecute(arrayList));
        });
        this.localThread.start();
    }

    public void cancel() {
        if (this.localThread.isAlive())
            this.localThread.interrupt();
    }

    protected void onPreExecute() {}

    protected abstract ArrayList<File> doInBackground(File... files);

    protected void onPostExecute(ArrayList arrayList) {}



}
