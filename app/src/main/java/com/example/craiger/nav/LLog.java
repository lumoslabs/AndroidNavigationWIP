package com.example.craiger.nav;

import java.util.Locale;

import android.util.Log;

public class LLog {

    public interface Logger {
        void log(int priority, String msg);
        void logException(Exception e);
        void beginTransaction(String name);
        void endTransaction(String name);
        void failTransaction(String name);
    }

    private static Logger sLogger = new LumosLogger();

    private LLog() {
    }

    public static void overrideLogger(Logger logger) {
        sLogger = logger;
    }

    public static void v(String tag, String msg) {
        log(Log.VERBOSE, tag, msg);
    }

    public static void v(String tag, String format, Object... args) {
        log(Log.VERBOSE, tag, String.format(Locale.US, format, args));
    }

    public static void d(String tag, String msg) {
        log(Log.DEBUG, tag, msg);
    }

    public static void d(String tag, String format, Object... args) {
        log(Log.DEBUG, tag, String.format(Locale.US, format, args));
    }

    public static void i(String tag, String msg) {
        log(Log.INFO, tag, msg);
    }

    public static void i(String tag, String format, Object... args) {
        log(Log.INFO, tag, String.format(Locale.US, format, args));
    }

    public static void w(String tag, String msg) {
        log(Log.WARN, tag, msg);
    }

    public static void w(String tag, String format, Object... args) {
        log(Log.WARN, tag, String.format(Locale.US, format, args));
    }

    public static void e(String tag, String msg) {
        log(Log.ERROR, tag, msg);
    }

    public static void e(String tag, String format, Object... args) {
        log(Log.ERROR, tag, String.format(Locale.US, format, args));
    }

    public static void logHandledException(Exception exception) {
        sLogger.logException(exception);
    }


    /**
     * Begin a transaction with Crittercism.
     *
     * Crittercism uses transactions allow us to track key interactions or user flows in our app such as login,
     * account registration, in app purchase, and anything else we want to define as a key user flow, perhaps loading of games
     *
     * To set set up a transaction use beginTransaction and to show an expected successful completed outcome use endTransaction
     *
     * @param name
     */
    public static void beginLoggingTransaction(String name) {
        sLogger.beginTransaction(name);
    }

    /**
     * End a transaction with Crittercism
     *
     * @param name
     */
    public static void endLoggingTransaction(String name) {
        sLogger.endTransaction(name);
    }

    /**
     * Mark a transaction as failed with Crittercism
     *
     * @param name
     */
    public static void failLoggingTransaction(String name) {
        sLogger.failTransaction(name);
    }

    /**
     * Used to place native log output into our logger so it can be passed to Crashlytics. (Cocos2dx
     * logs directly to native code, so to get logs for native crashes we have to pass log
     * statements up to java.)
     *
     * @param msg
     */
    public static void javaLog(String msg) {
        sLogger.log(Log.DEBUG, getLogMessage("NATIVE", msg));
    }

    private static void log(int priority, String tag, String msg) {
        sLogger.log(priority, getLogMessage(tag, msg));
    }

    private static String getLogMessage(String tag, String msg) {
        return String.format(Locale.US, "%s: %s - %s", tag, getMethodName(), msg);
    }

    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[6].getMethodName() + "(*)";
    }

}
