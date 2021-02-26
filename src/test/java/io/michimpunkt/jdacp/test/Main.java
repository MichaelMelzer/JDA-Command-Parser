package io.michimpunkt.jdacp.test;

public class Main {

    public static String[] args;

    public static void main(String[] args) {
        Main.args = args;
        try {
            // surrounding in try-catch, in order to make debugging easier
            new Bot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
