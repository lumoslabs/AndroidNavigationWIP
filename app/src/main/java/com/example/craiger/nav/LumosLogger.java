package com.example.craiger.nav;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

import android.util.Log;

/**
 * @author sasa
 *
 */
public class LumosLogger implements LLog.Logger {
    private static final String TAG = "LUMOS";

    protected final int mMinPriority;

    public LumosLogger() {
        this(Log.VERBOSE);
    }

    public LumosLogger(int minPriority) {
        mMinPriority = minPriority;
    }

    protected String getLogTag() {
        return TAG;
    }

    @Override
    public void log(int priority, String msg) {
        if (priority >= mMinPriority) {
            Log.println(priority, TAG, msg);
        }
    }

    @Override
    public void logException(Exception e) {
        Log.e(TAG, "Caught exception!", e);
    }

    @Override
    public void beginTransaction(String name) {
    }

    @Override
    public void endTransaction(String name) {
    }

    @Override
    public void failTransaction(String name) {
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    private String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

}
