package com.electric.suppliers.util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Helper {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isElementPresent(WebElement webElement) {
        try{
            return webElement.isDisplayed();
        }catch (NoSuchElementException ignored) { }
        return false;
    }
}
