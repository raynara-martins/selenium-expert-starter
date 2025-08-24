package support;

import config.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    private final WebDriverWait wait;

    public Waits(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }

    public WebElement clickable(By locator) {

        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement visible(By locator) {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean urlContains(String fragment) {

        return wait.until(ExpectedConditions.urlContains(fragment));
    }
}
