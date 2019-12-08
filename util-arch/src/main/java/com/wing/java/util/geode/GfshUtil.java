//package com.wing.java.util.geode;
//
//import org.apache.geode.management.internal.cli.HeadlessGfsh;
//
///**
// * @author wing
// * @create 2019-08-12 15:36
// */
//public class GfshUtil {
//    public static void execute(String command) throws Exception {
//        HeadlessGfsh gfsh = new HeadlessGfsh("gfsh", 30, GenerateJavaConstant.gfshLogPath);
//
////            boolean b1 = gfsh.executeCommand("connect --locator=192.168.1.110[30001] --user=admin --password=admin");
//        boolean b1 = gfsh.executeCommand(GenerateJavaConstant.gfshConnCmd);
////        System.out.println(b1);
////        System.out.println(gfsh.getResult());
//
//        boolean b2 = gfsh.executeCommand(command);
////        System.out.println(b2);
////        System.out.println(gfsh.getResult());
//
//        boolean b3 = gfsh.executeCommand("quit");
////        System.out.println(gfsh.getResult());
////        System.out.println(b3);
//
//        gfsh.terminate();
//    }
//}
