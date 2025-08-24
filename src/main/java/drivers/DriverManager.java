package drivers;

import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static WebDriver get() {
        return TL.get();
    }

    public static WebDriver create() {
        String browser = Config.browser().toLowerCase();
        boolean headless = Config.headless();
        WebDriver driver;
        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) fo.addArguments("-headless");
                driver = new FirefoxDriver(fo);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions eo = new EdgeOptions();
                if (headless) eo.addArguments("--headless=new");
                driver = new EdgeDriver(eo);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                co.addArguments("--start-maximized");
                if (headless) co.addArguments("--headless=new");
                driver = new ChromeDriver(co);
            }
        }
        TL.set(driver);
        return driver;
    }

    public static void quit() {
        WebDriver d = get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }
}
