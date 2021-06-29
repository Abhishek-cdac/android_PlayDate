//package com.playdate.app.temp;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class Times {
//
//    static void sTimer(Robot bot) {
//        int mask = InputEvent.BUTTON1_DOWN_MASK;
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        double width = screenSize.getWidth();
//        double height = screenSize.getHeight();
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                bot.mouseMove(100, 100);
//                bot.mousePress(mask);
//                bot.mouseRelease(mask);
//            }
//        }, 2000);
//    }
//}
