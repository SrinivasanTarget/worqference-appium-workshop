package com.example.gestures;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import static java.time.Duration.*;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
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
        wait = new WebDriverWait(driver, ofSeconds(30));
    }

    @Test
    public void dragAndDropTest() {
        wait.until(elementToBeClickable(AppiumBy.accessibilityId("login"))).click();
        wait.until(elementToBeClickable(AppiumBy.accessibilityId("dragAndDrop"))).click();
        final WebElement dragMe = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dragMe")));
        final WebElement dropzone = wait.until(elementToBeClickable(AppiumBy.accessibilityId("dropzone")));

        final Point point = dragMe.getRect().getPoint();
        final Rectangle rect = dropzone.getRect();
        final int targetX = rect.getX() + rect.getWidth() / 2;
        final int targetY = rect.getY() + rect.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragAndDrop = new Sequence(finger, 1);
        // pointerMove, pointerDown, pause, pointerMove, pointerUp
        dragAndDrop.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
                point.getX(), point.getY()));
        dragAndDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragAndDrop.addAction(new Pause(finger, Duration.ofMillis(600)));
        dragAndDrop.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
                targetX, targetY));
        dragAndDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(singletonList(dragAndDrop));
    }

    @Test
    public void dragAndDropUsingAppiumPluginTest() {
        wait.until(elementToBeClickable(AppiumBy.accessibilityId("login"))).click();
        wait.until(elementToBeClickable(AppiumBy.accessibilityId("dragAndDrop"))).click();
        final WebElement dragMe = wait.until(presenceOfElementLocated(AppiumBy.accessibilityId("dragMe")));
        final WebElement dropzone = wait.until(elementToBeClickable(AppiumBy.accessibilityId("dropzone")));

        driver.addCommand(HttpMethod.POST, String.format("/session/%s/plugin/actions/dragAndDrop",
                driver.getSessionId()), "dragAndDrop");

        driver.execute("dragAndDrop", ImmutableMap.of("sourceId", ((RemoteWebElement) dragMe).getId(),
                "destinationId", ((RemoteWebElement) dropzone).getId()));
    }
}
