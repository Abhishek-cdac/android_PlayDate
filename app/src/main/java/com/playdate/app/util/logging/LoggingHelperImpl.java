package com.playdate.app.util.logging;

import android.util.Log;

public class LoggingHelperImpl implements LoggingHelper {

    private static volatile LoggingHelperImpl instance;

    private LoggingHelperImpl() {
    }

    public static synchronized LoggingHelperImpl getInstance() {
        if (instance == null) {
            instance = new LoggingHelperImpl();
        }

        return instance;
    }

    @Override
    public void debug(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void debug(String tag, String message, Throwable t) {
        Log.d(tag, message, t);
    }

    @Override
    public void info(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void info(String tag, String message, Throwable t) {
        Log.i(tag, message, t);
    }

    @Override
    public void warn(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void warn(String tag, String message, Throwable t) {
        Log.w(tag, message, t);
    }

    @Override
    public void error(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void error(String tag, String message, Throwable t) {
        Log.e(tag, message, t);
    }
}
