package util;

import java.util.concurrent.TimeUnit;

public class Util {
    public static void delay(int miliSec){
        try {
            TimeUnit.MILLISECONDS.sleep(miliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
