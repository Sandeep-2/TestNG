package com.ultralesson.eventplanner;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTestNGListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getName() + " at " + getCurrentTime());
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName() + " at " + getCurrentTime());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName() + ", Exception: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}