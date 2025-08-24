package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(groups = "smoke")
    public void deveLogarComSucesso() {
        LoginPage login = new LoginPage(driver).open()
                .fillUser("standard_user")
                .fillPass("secret_sauce");
        login.submit();

        InventoryPage inv = new InventoryPage(driver);
        Assertions.assertThat(inv.appTitle()).isEqualTo("Swag Labs");
    }

    @Test
    public void naoDeveLogarComSenhaInvalida() {
        LoginPage login = new LoginPage(driver).open()
                .fillUser("standard_user")
                .fillPass("senha_errada");
        login.submit();

        Assertions.assertThat(login.errorMessage())
                .contains("Username and password do not match");
    }
}
