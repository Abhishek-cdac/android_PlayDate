//package com.playdate.app.util.executor;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import java.util.List;
//import java.util.concurrent.AbstractExecutorService;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class ExecutorProviderImpl implements ExecutorProvider {
//
//    private static volatile ExecutorProviderImpl instance = new ExecutorProviderImpl();
//
//    private ExecutorService computationExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//    private ExecutorService ioExecutor = Executors.newCachedThreadPool();
//
//    private ExecutorService uiExecutor = new AbstractExecutorService() {
//        private Handler handler = new Handler(Looper.getMainLooper());
//        @Override public void execute(Runnable command) {
//            if (handler.getLooper() == Looper.myLooper()) {
//                command.run();
//            } else {
//                handler.post(command);
//            }
//        }
//        @Override public void shutdown() { throw new UnsupportedOperationException(); }
//        @Override public List<Runnable> shutdownNow() { throw new UnsupportedOperationException(); }
//        @Override public boolean isShutdown() { return false; }
//        @Override public boolean isTerminated() { return false; }
//        @Override public boolean awaitTermination(long l, TimeUnit timeUnit) { throw new UnsupportedOperationException(); }
//    };
//
//    private ExecutorProviderImpl() {
//    }
//
//    public static ExecutorProviderImpl getInstance() {
//        return instance;
//    }
//
//    @Override
//    public ExecutorService computation() {
//        return computationExecutor;
//    }
//
//    @Override
//    public ExecutorService io() {
//        return ioExecutor;
//    }
//
//    @Override
//    public ExecutorService ui() {
//        return uiExecutor;
//    }
//}
