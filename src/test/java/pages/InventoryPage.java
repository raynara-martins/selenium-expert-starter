package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import support.Waits;

public class InventoryPage {
    private final WebDriver driver;
    private final Waits waits;
    private final By title = By.cssSelector(".app_logo"); // shows 'Swag Labs'

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    public String appTitle() {
        return waits.visible(title).getText();
    }
}
