# Hamburgueria BDD - Peppa Lanches

Projeto Maven (Java 21) com Cucumber 7 e JUnit 5 para testes BDD dos pedidos da hamburgueria Peppa Lanches.

## Requisitos
- JDK 21 (ex.: Temurin 21)
- Maven ou Maven Daemon (`mvnd`) no `PATH`

## Como rodar os testes
```bash
export JAVA_HOME="/caminho/para/jdk-21"
export PATH="/caminho/para/maven/bin:$JAVA_HOME/bin:$PATH"

cd Hamburgueria-BDD
mvn test
# ou, se usar mvnd
mvnd test
```

## Estrutura principal
- `pom.xml`: dependências Cucumber/JUnit e plugins.
- `src/main/java/peppa/hamburgueria/`
  - `CardapioService.java`
  - `PedidoService.java`
- `src/test/resources/features/pedidos.feature`: cenários em Gherkin (PT-BR).
- `src/test/java/runner/RunnerTest.java`: runner JUnit Platform + Cucumber.
- `src/test/java/steps/PedidoSteps.java`: steps em PT-BR usando `io.cucumber.java.pt.*`.

## Notas dos testes
- Cenários cobrem: item existente, item inexistente, quantidade inválida, desconto de 10%, tempo estimado.
- Mensagens esperadas de erro:
  - `Item indisponível no cardápio`
  - `Quantidade inválida`

## Git ignore
- `target/` (build Maven)
- `.idea/`, `*.iml` (configuração de IDE)***
