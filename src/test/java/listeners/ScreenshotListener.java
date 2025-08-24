package listeners;

import drivers.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            var driver = DriverManager.get();

            // Screenshot em falha
            if (driver instanceof TakesScreenshot ts) {
                byte[] png = ts.getScreenshotAs(OutputType.BYTES);
                // Anexa como imagem PNG no Allure
                Allure.getLifecycle().addAttachment("screenshot", "image/png", "png", png);
            }

            // Detalhes textuais da falha (nome do teste + mensagem da exceção)
            String details = result.getName() + " failed: "
                    + (result.getThrowable() != null ? result.getThrowable().getMessage() : "");
            Allure.addAttachment("failure-details", details);

        } catch (Exception ignored) {
            // Evita que falhas no listener quebrem a suite
        }
    }
}
