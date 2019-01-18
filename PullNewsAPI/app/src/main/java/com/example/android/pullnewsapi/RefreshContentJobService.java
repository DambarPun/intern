package com.example.android.pullnewsapi;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.res.Resources;
import android.util.Log;

public class RefreshContentJobService extends JobService {
    private static final String TAG = "RefreshContentJobServic";
    private boolean mJobCancelled = false;


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: Job Started");
        doBackgroundWork(params);
        return false;

        //on long running service
        //return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if (mJobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: ", e);
                    }
                }
                Log.d(TAG, "run: Job Finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: Job cancelled before cancelled");
        mJobCancelled = true;
        //return true if you want to start job service later
        return true;
        //return false if you don't want to start job service later
        //return false;
    }
}
