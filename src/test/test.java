package test;

import java.util.Random;

public class test {

    static Random rnd;

    public static void main(String[] args) {
        rnd = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(rnd.nextInt(1-0+1)+0);
        }
    }
}
