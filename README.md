# Selenium Expert Starter (Java + Maven + TestNG)
![Java](https://img.shields.io/badge/java-17-blue)
![Maven](https://img.shields.io/badge/build-Maven-success)
![TestNG](https://img.shields.io/badge/tests-TestNG-green)

Alvo: https://www.saucedemo.com/

## Rodar local
```bash
mvn -q -DbaseUrl=https://www.saucedemo.com -Dbrowser=chrome -Dheadless=true test
```

Propriedades úteis (`-D`):
- `baseUrl`  (default: https://www.saucedemo.com)
- `browser`  (chrome|firefox|edge) default: chrome
- `headless` (true|false) default: false

Credenciais padrão do SauceDemo
- Usuário: `standard_user`
- Senha: `secret_sauce`

## Estrutura
- `drivers` — gerenciamento de WebDriver com ThreadLocal (execução paralela segura)
- `config`  — configuração via System properties
- `support` — helpers (esperas, utilitários)
- `pages`   — Page Objects (POM)
- `tests`   — cenários curtos, legíveis
- `listeners` — prints em falhas + anexos Allure

## Executar em diferentes ambientes
- QA (default)
- mvn test -Denv=qa

- DEV
- mvn test -Denv=dev

# Override temporário (sem alterar arquivo)
mvn test -DbaseUrl=http://localhost:3000 -Dbrowser=firefox


Relatórios Allure (opcional):
```bash
# após rodar os testes
allure serve target/allure-results
```
