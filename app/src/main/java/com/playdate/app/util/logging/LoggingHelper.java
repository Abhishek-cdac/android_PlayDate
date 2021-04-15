package com.playdate.app.util.logging;

public interface LoggingHelper {

    void debug(String tag, String message);

    void debug(String tag, String message, Throwable t);

    void info(String tag, String message);

    void info(String tag, String message, Throwable t);

    void warn(String tag, String message);

    void warn(String tag, String message, Throwable t);

    void error(String tag, String message);

    void error(String tag, String message, Throwable t);
}
