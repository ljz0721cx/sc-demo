package com.thclouds.agent;

import com.thclouds.agent.demo.TestDomeToString;

public class TestMain {

    public static void main(String[] args) {
        TestDomeToString testDomeToString = new TestDomeToString();
        String s = testDomeToString.toString();
        System.out.println(s);
//        testDomeToString.test1();
    }
}
