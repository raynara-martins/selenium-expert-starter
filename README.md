# Selenium Expert Starter (Java + Maven + TestNG)

![Java](https://img.shields.io/badge/java-17-blue)
![Maven](https://img.shields.io/badge/build-Maven-success)
![TestNG](https://img.shields.io/badge/tests-TestNG-green)
[![UI Tests](https://github.com/raynara-martins/selenium-expert-starter/actions/workflows/ui-tests.yml/badge.svg)](https://github.com/raynara-martins/selenium-expert-starter/actions/workflows/ui-tests.yml)

Projeto base para automação de testes de UI com **Selenium + Java + TestNG**, usando **Page Object Model**, ambientes configuráveis e integração com **GitHub Actions**.

Alvo inicial: https://www.saucedemo.com/
  
Credenciais de exemplo:  
- Usuário: `standard_user`  
- Senha: `secret_sauce`

---

## Como rodar localmente

```bash
# roda a suite local (sem paralelismo, navegador visível)
mvn clean test -DsuiteXml=testng-local.xml -Dheadless=false
```

Propriedades úteis (`-D`):
- `suiteXml` → `testng-local.xml` (default) ou `testng.xml` (CI)
- `baseUrl` → default: https://www.saucedemo.com
- `browser` → `chrome|firefox|edge` (default: chrome)
- `headless` → `true|false` (default: false)
- `env` → `qa|dev|prod` (default: qa)

Exemplo (rodar em dev, firefox, headless):
```bash
mvn test -Denv=dev -Dbrowser=firefox -Dheadless=true
```

Override temporário (sem alterar arquivos):
```bash
mvn test -DbaseUrl=http://localhost:3000 -Dbrowser=firefox
```

---

## Executar em CI (GitHub Actions)

Na pipeline o comando usado é:
```bash
mvn -q clean test -DsuiteXml=testng.xml -Dheadless=true
```

- Usa a suite **CI** (`testng.xml`) com paralelismo (`parallel="methods"`).
- Roda em **headless** (necessário no runner Linux).
- Publica relatórios (Surefire / Allure).

---

## Estrutura do projeto

- `drivers` — gerenciamento de WebDriver com **ThreadLocal** (execução paralela segura)
- `config` — `Config.java`, leitura de `System properties` ou `.properties` por ambiente
- `support` — helpers (esperas explícitas, utilitários)
- `pages` — Page Objects (LoginPage, InventoryPage, etc.)
- `tests` — cenários de teste curtos e legíveis
- `listeners` — captura de prints em falhas + anexos Allure
- `src/test/resources/environments` — `qa.properties`, `dev.properties`, `prod.properties`, `local.properties` (ignorado no git)

---

## Relatórios Allure

### Usando Allure CLI (instalado na máquina)
Após rodar os testes:
```bash
allure serve target/allure-results
```

Isso abrirá os relatórios em modo interativo no navegador.

### Usando Docker 
```bash
docker run --rm -p 5050:5050 \
  -v "$(pwd)/target/allure-results:/app/allure-results" \
  -v "$(pwd)/allure-reports:/app/allure-reports" \
  frankescobar/allure-docker-service
```
Acesse:
[http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html](http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html)
