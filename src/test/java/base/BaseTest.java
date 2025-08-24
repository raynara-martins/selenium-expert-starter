package base;

import config.Config;
import drivers.DriverManager;
import listeners.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ScreenshotListener.class})
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.create();
        driver.get(Config.baseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quit();
    }
}
