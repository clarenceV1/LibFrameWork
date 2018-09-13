package com.cai.framework.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * 单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
     */
    private static CrashHandler instance;

    public CrashHandler() {

    }

    /**
     * 同步方法，以免单例多线程环境下出现异常
     *
     * @return
     */
    public synchronized static CrashHandler getInstance() {

        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化，把当前对象设置成UncaughtExceptionHandler处理器
     */
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) { // 当有未处理的异常发生时，就会来到这里。。
        final String throwables = Log.getStackTraceString(ex);
        Log.e("logs", throwables);
//        if (!Conta.IS_TEST_VERSION) {
//
//            if (!TextUtils.isEmpty(throwables) && throwables.length() > 9) {
////			String exceptionName = throwables.substring(0, throwables.indexOf("Exception") + 9);
////			HttpRequests httpRequestsMethod = new HttpRequests();
////			httpRequestsMethod.clientSendExceptionInfo(ErrorCode.clientOperateError, exceptionName, throwables);
//            }
//        }
        writeFileSdcard(throwables);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
//        reStartApp();

    }

    /**
     * 功能描述：<重启App>
     *
     * @author chengaobin create 2015/02/02 V.2.0
     */
    private void reStartApp() {
//		Intent intent = new Intent(GodBaseApplication.getAppContext(),MainActivity.class);
//		PendingIntent restartIntent = PendingIntent.getActivity(GodBaseApplication.getAppContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//		// 退出程序
//		AlarmManager mgr = (AlarmManager) GodBaseApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
//		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,restartIntent); // 1秒钟后重启应用
//        GodBaseApplication.getAppContext().finishActivity(true,null);
    }


    private void writeFileSdcard(String message) {

        try {
            String fileName = Environment.getExternalStorageDirectory().toString() + File.separator + "exception.txt";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte[] bytes = message.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
