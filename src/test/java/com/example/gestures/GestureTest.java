package com.example.gestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GestureTest {

    public AppiumDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void beforeClass() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setApp(System.getProperty("user.dir") + "/apps/VodQA.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void sampleTest() {
        wait.until(ExpectedConditions.
                elementToBeClickable(AppiumBy.accessibilityId("login"))).click();
    }
}
