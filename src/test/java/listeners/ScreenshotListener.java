package listeners;

import drivers.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.charset.StandardCharsets;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            var driver = DriverManager.get();
            if (driver instanceof TakesScreenshot ts) {
                byte[] png = ts.getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment("screenshot", "image/png", "png", png);
            }
            String name = result.getName() + " failed: " + result.getThrowable();
            Allure.addAttachment("failure", "text/plain",
                    name, StandardCharsets.UTF_8.getName());
        } catch (Exception ignored) {}
    }
}
