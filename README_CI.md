# Guia de Integração Contínua (GitHub Actions)

Este guia descreve como configurar a integração contínua (CI) para o projeto **selenium-expert-starter** usando **GitHub Actions**.

---

## 1. Configuração no `pom.xml`

No plugin `maven-surefire-plugin`, já está configurado para gerar os resultados do Allure:

```xml
<systemPropertyVariables>
    <allure.results.directory>${project.build.directory}/allure-results</allure.results.directory>
</systemPropertyVariables>
```

Isso garante que os relatórios Allure sejam gerados a cada execução.

---

## 2. Workflow GitHub Actions

Arquivo: `.github/workflows/ui-tests.yml`

```yaml
name: ui-tests
on: [push, pull_request]
jobs:
  run:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - name: Cache Maven
        uses: actions/cache@v4
        with:
          path: ~/.m2/repository
          key: m2-${{ hashFiles('**/pom.xml') }}
      - name: Run tests headless
        run: mvn -q -DbaseUrl=https://www.saucedemo.com -Dbrowser=chrome -Dheadless=true test
      - name: Upload reports
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: test-reports
          path: |
            target/surefire-reports/**
            target/allure-results/**
```

---

## 3. Como funciona

- A cada **push** ou **pull request**, os testes são executados automaticamente.
- Se algum teste falhar, o workflow **quebra** (fica vermelho).
- Relatórios (`surefire-reports` e `allure-results`) ficam disponíveis como artefatos.

---

## 4. Badge de Status

No `README.md` principal:

```markdown
[![UI Tests](https://github.com/SEU_USUARIO/selenium-expert-starter/actions/workflows/ui-tests.yml/badge.svg)](https://github.com/SEU_USUARIO/selenium-expert-starter/actions/workflows/ui-tests.yml)
```

---

## 5. Troubleshooting

- Se os testes não abrirem navegador → confira se `headless=true` está sendo passado no CI.
- Se os relatórios Allure não aparecerem → verifique se a pasta `target/allure-results` está sendo gerada corretamente.
- Caso precise rodar em outros navegadores, use uma **matriz** de jobs.

Exemplo:

```yaml
strategy:
  matrix:
    browser: [chrome, firefox]
steps:
  - name: Run tests
    run: mvn test -Dbrowser=${{ matrix.browser }} -Dheadless=true
```

---

## 6. Fluxo detalhado da Integração Contínua (GitHub Actions)

O workflow está em **`.github/workflows/ui-tests.yml`** e **roda automaticamente** em **push** e **pull request**.

**O que ele faz:**
1. Faz checkout do repositório e prepara Java 17 (com cache do Maven).
2. Executa `mvn -q clean test -DsuiteXml=testng.xml -Dheadless=true`.
    - Usa a suíte **CI** (`testng.xml`) com **paralelismo** configurado.
    - Roda **headless** (necessário no runner Linux).
3. **Falhas quebram o job** automaticamente (o `mvn test` retorna exit code ≠ 0).
    - Em PR: o _check_ fica vermelho e o PR não passa.
    - Em push direto na branch: o status do workflow fica `failed`.
4. **Artefatos de relatório** são enviados sempre (`if: always()`):
    - `target/surefire-reports/**`
    - `target/allure-results/**`

**Onde ver/baixar relatórios do CI:**
- Abra o workflow da execução → **Artifacts** → baixe **test-reports** para inspecionar localmente.
- Para visualizar o Allure a partir dos `allure-results` baixados, rode:
  ```bash
  allure serve path/para/os/allure-results
  ```


## 7. Resumo do Fluxo

- **Execução automática no push/PR**
- **Falhas quebram o pipeline**
- **Relatórios disponíveis como artefatos**
