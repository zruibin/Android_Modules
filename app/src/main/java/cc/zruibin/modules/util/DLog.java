package cc.zruibin.modules.util;

import android.util.Log;

/**
 * Created by ruibin.chow on 12/01/2018.
 */

public final class DLog extends Object {

    private final  static String TAG = "QuickTalk";
    private final static int originStackIndex = 5;

    private static String getFileName(Thread thread) {
        return thread.getStackTrace()[originStackIndex].getFileName();
    }

    private static String getLineNumber(Thread thread) {
        return String.valueOf(thread.getStackTrace()[originStackIndex].getLineNumber());
    }

    private static String getMethodName(Thread thread) {
        return thread.getStackTrace()[originStackIndex].getMethodName();
    }

    private static String getStackTrace(Thread thread) {
        String fileName = DLog.getFileName(thread);
//        String methodName = DLog.getMethodName(thread);
        String lineNumber = DLog.getLineNumber(thread);
        return fileName + " " + lineNumber + ": ";
    }

    public static void debug(String msg) {
        String message = DLog.getStackTrace(Thread.currentThread()) + msg;
        Log.d(TAG, message);
    }

    public static void info (String msg) {
        String message = DLog.getStackTrace(Thread.currentThread()) + msg;
        Log.i(TAG, message);
    }

    public static void  warn(String msg) {
        String message = DLog.getStackTrace(Thread.currentThread()) + msg;
        Log.w(TAG, message);
    }

    public static void error(String msg) {
        String message = DLog.getStackTrace(Thread.currentThread()) + msg;
        Log.e(TAG, message);
    }
}
