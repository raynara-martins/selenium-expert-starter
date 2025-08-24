# 📘 Guia de Estrutura e Boas Práticas — Selenium + Java

## Visão Geral
Quando você roda `mvn test`:

1. **TestNG** lê o `testng.xml`, descobre os testes.  
2. `BaseTest.setUp()` cria o **WebDriver** via `DriverManager` (um driver por thread).  
3. Os testes chamam **Page Objects (POM)** que encapsulam os seletores e ações.  
4. As ações usam **Waits** (esperas explícitas) → testes mais estáveis.  
5. Se falhar, o **ScreenshotListener** gera **screenshot** + evidência (Allure).  
6. `BaseTest.tearDown()` fecha o driver.  

---

## Estrutura de Pastas
```
src
 ├─ main/java
 │   ├─ config        → Configurações (URL, browser, timeout)
 │   ├─ drivers       → Criação e ciclo de vida do WebDriver
 │   └─ support       → Helpers (esperas, utilitários)
 └─ test/java
     ├─ base          → BaseTest (setup/teardown)
     ├─ listeners     → ScreenshotListener (prints em falha)
     ├─ pages         → Page Objects (LoginPage, InventoryPage…)
     └─ tests         → Cenários de teste (LoginTest, etc.)
```

---

## Arquivos Principais

### `pom.xml` — dependências
- **selenium-java** → automação do navegador  
- **testng** → runner de testes  
- **webdrivermanager** → baixa driver certo do navegador  
- **assertj-core** → asserts legíveis  
- **allure-testng** → relatórios bonitos  

Maven garante que tudo rode **igual em qualquer máquina/CI**.

---

### `testng.xml` — orquestração
```xml
<suite parallel="methods" thread-count="2">
  <classes>
    <class name="tests.LoginTest"/>
  </classes>
</suite>
```
- Define **paralelismo** (`methods`, 2 threads).  
- Agrupa testes por classe, suite, ou grupo (`@Test(groups="smoke")`).  

---

### `config/Config.java`
```java
System.getProperty("baseUrl", "https://www.saucedemo.com");
```
- Lê configs via **`-D` no Maven**.  
- Facilita trocar ambiente sem mudar código.  

---

### `drivers/DriverManager.java`
- Cria WebDriver (Chrome, Firefox, Edge).  
- Usa `ThreadLocal` → cada teste tem **driver isolado**.  
- Evita conflitos em paralelismo.  

---

### `support/Waits.java`
- Wrappers para `WebDriverWait`:  
  - `visible(locator)`  
  - `clickable(locator)`  
- Sincronização estável, sem `Thread.sleep()`.

---

### `base/BaseTest.java`
- `@BeforeMethod` → cria driver, abre `baseUrl`.  
- `@AfterMethod(alwaysRun=true)` → fecha driver.  
- Garante **setup/teardown centralizados**.  

---

### `listeners/ScreenshotListener.java`
- Em falha → tira screenshot + log.  
- Anexa ao Allure.  
- **Por quê?** Evidência imediata em CI/CD.  

---

### `pages/` — Page Object Model (POM)
Exemplo: **LoginPage**
```java
public LoginPage fillUser(String u) {
  waits.visible(user).sendKeys(u);
  return this;
}
```
- Encapsula seletores e ações.  
- **Testes ficam legíveis**:  
  ```java
  new LoginPage(driver).open()
      .fillUser("standard_user")
      .fillPass("secret_sauce")
      .submit();
  ```

---

### `tests/LoginTest.java`
- **Cenário 1:** login com sucesso → valida título.  
- **Cenário 2:** senha inválida → valida mensagem de erro.  

Testes curtos, claros e com asserts legíveis (`assertThat(...)`).

---

## Fluxo de Execução
1. TestNG roda `LoginTest`.  
2. `BaseTest.setUp()` cria driver.  
3. Teste chama `LoginPage` → ações → `InventoryPage`.  
4. Validações com AssertJ.  
5. Se falha → screenshot (Allure).  
6. `tearDown()` fecha driver.  

---

## Pipeline (CI/CD)
`.github/workflows/ui-tests.yml`
```yaml
mvn -q -DbaseUrl=https://www.saucedemo.com        -Dbrowser=chrome -Dheadless=true test
```
- Roda testes headless em GitHub Actions.  
- Faz cache do Maven (execução mais rápida).  
- Publica relatórios (Surefire/Allure).  

---

## Decisões Técnicas (por quê?)
- **Waits explícitas** > implícitas → mais controle, menos flakiness.  
- **ThreadLocal drivers** → paralelismo seguro.  
- **WebDriverManager** → elimina dor de “versão do chromedriver”.  
- **POM** → manutenção fácil quando UI muda.  
- **TestNG** → mais flexível em paralelismo/listeners que JUnit.  

---

## Próximos Passos
1. Criar **Page Components** (ex.: `MenuComponent` para Logout).  
2. Adicionar novos testes (`LogoutTest`).  
3. Integrar **Allure Reports** (`allure serve target/allure-results`).  
4. Expandir para **pipelines complexos** (matriz com browsers, smoke vs regression).  

---