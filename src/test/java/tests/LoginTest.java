package tests;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {

    @Test(groups = "smoke")
    @Description("Login válido deve abrir a página de inventário")
    public void deveLogarComSucesso() {

        Allure.step("Abrir página de login", () -> {
            new LoginPage(driver).open();
        });

        Allure.step("Preencher credenciais válidas e submeter", () -> {
            new LoginPage(driver)
                    .fillUser("standard_user")
                    .fillPass("secret_sauce")
                    .submit();
        });

        Allure.step("Validar página de inventário", () -> {
            String title = new InventoryPage(driver).appTitle();
            assertThat(title).isEqualTo("Swag Labs");
        });
    }

    @Test
    @Description("Deve exibir mensagem de erro quando senha é inválida")
    public void naoDeveLogarComSenhaInvalida() {

        Allure.step("Abrir página de login", () -> {
            new LoginPage(driver).open();
        });

        Allure.step("Preencher usuário válido e senha inválida; submeter", () -> {
            new LoginPage(driver)
                    .fillUser("standard_user")
                    .fillPass("senha_errada")
                    .submit();
        });

        Allure.step("Validar mensagem de erro exibida", () -> {
            // SauceDemo costuma começar com "Epic sadface: ..."
            String msg = new LoginPage(driver).errorMessage();
            assertThat(msg).contains("Epic sadface");
        });
    }

    @Test(enabled = false)
    @Description("Forçar falha para validar screenshot e anexos no Allure")
    public void falhaProposital() {

        Allure.step("Abrir página de login", () -> {
            new LoginPage(driver).open();
        });

        Allure.step("Tentar ler mensagem de erro que NÃO existe após login válido", () -> {
            new LoginPage(driver)
                    .fillUser("standard_user")
                    .fillPass("secret_sauce")
                    .submit();

            // força falha: após login válido não há banner de erro
            String msg = new LoginPage(driver).errorMessage();
            assertThat(msg).contains("erro que não existe");
        });
    }
}
