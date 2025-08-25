package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import support.Waits;

public class LoginPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By user = By.id("user-name");
    private final By pass = By.id("password");
    private final By btnLogin = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    public LoginPage open() {
        driver.get("https://www.saucedemo.com/");
        return this;
    }

    public LoginPage fillUser(String u) {
        waits.visible(user).clear();
        driver.findElement(user).sendKeys(u);
        return this;
    }

    public LoginPage fillPass(String p) {
        waits.visible(pass).clear();
        driver.findElement(pass).sendKeys(p);
        return this;
    }

    public void submit() {
        waits.clickable(btnLogin).click();
    }

    public String errorMessage() {

        return driver.findElement(error).getText();
    }
}
