package test;

import java.util.Random;

public class test {

    static Random rnd = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(rnd.nextInt(12));
        }
    }
}
