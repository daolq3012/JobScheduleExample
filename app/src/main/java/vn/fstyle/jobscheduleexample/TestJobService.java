package vn.fstyle.jobscheduleexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

/**
 * Copyright © 2016 FStyleVN
 * Created by Sun on 26/11/2016.
 */

public class TestJobService extends JobService {

    private static final String TAG = TestJobService.class.getSimpleName();

    private JobAsyncTask mJobAsyncTask;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "DebugLog onStartJob ");
        Log.d(TAG, "DebugLog onStartJob "+jobParameters.getExtras().getString("abc"));
        mJobAsyncTask = new JobAsyncTask();
        mJobAsyncTask.execute(jobParameters);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "DebugLog onStopJob ");
        if (mJobAsyncTask != null) {
            if (mJobAsyncTask.isCancelled()) {
                return true;
            }
            mJobAsyncTask.cancel(true);
        }
        return false;
    }

    private class JobAsyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
        // JobParameters contains the parameters used to configure/identify the job.
        // You do not create this object yourself,
        // instead it is handed in to your application by the System.

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            //Wait for 2 seconds to finish dummy task
            SystemClock.sleep(2000);
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            Log.d(TAG, "DebugLog Task Finished ");
            jobFinished(jobParameters, false);
        }
    }
}
