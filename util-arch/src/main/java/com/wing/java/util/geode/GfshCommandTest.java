//package com.wing.java.geode.util.geode;
//
//import org.apache.geode.management.internal.cli.HeadlessGfsh;
//
//import java.io.IOException;
//
//public class GfshCommandTest {
//
//    public static void main(String[] args) {
//        try {
//            HeadlessGfsh gfsh = new HeadlessGfsh("gfsh", 30, "G:\\ogg");
//
////            boolean b1 = gfsh.executeCommand("connect --locator=192.168.1.110[30001] --user=admin --password=admin");
//            boolean b1 = gfsh.executeCommand("connect --locator=localhost[10334]");
//            System.out.println(b1);
//            System.out.println(gfsh.getResult());
//
//            boolean b2 = gfsh.executeCommand("execute --jar=G:\\ogg\\geode-common.jar");
//            System.out.println(b2);
//            System.out.println(gfsh.getResult());
//
//            boolean b3 = gfsh.executeCommand("quit");
//            System.out.println(gfsh.getResult());
//            System.out.println(b3);
//
//            gfsh.terminate();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
//
//}
