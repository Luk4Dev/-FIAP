# OrbitAgro Guard API — Global Solution FIAP 2026-1

Backend desenvolvido para a disciplina **Domain Driven Design (DDD)** da Global Solution FIAP 2026-1.

## Integrantes

| Nome | RM |
|---|---:|
| Diego Garcia Tosta | 556724 |
| Joud Jihad Jaber | 556482 |
| Lucca Pereira | 560731 |

## URLs obrigatórias

- **URL do vídeo técnico:** `INSERIR_LINK_YOUTUBE_NAO_LISTADO_AQUI`
- **URL do vídeo pitch:** `https://youtu.be/dNRnWeJZ_xo`

## Problema de negócio

Pequenos produtores em regiões rurais com baixa conectividade sofrem com eventos agroclimáticos extremos, como seca, geada, queimadas e chuva intensa. Em muitos casos, a resposta ocorre tarde porque as informações climáticas e orbitais não chegam de forma organizada às cooperativas, escolas rurais, técnicos agrícolas e equipes de apoio.

## Objetivo da solução

O **OrbitAgro Guard** é uma API backend no contexto aeroespacial que utiliza dados orbitais e indicadores agroclimáticos para gerar alertas, classificar criticidade, alocar equipes de resposta e planejar rotas de atendimento para comunidades rurais vulneráveis.

A solução representa uma aplicação da economia espacial na Terra, combinando dados de observação orbital, conectividade remota e regras de negócio para apoiar agricultura familiar e tomada de decisão em regiões isoladas.

## Domínio modelado

O domínio principal é o **monitoramento e resposta a riscos agroclimáticos em regiões rurais com baixa conectividade**.

Entidades centrais:

- `RegiaoRural`
- `Produtor`
- `Cooperativa`
- `DadoOrbital`
- `AlertaAgroclimatico`
- `EquipeResposta`
- `RotaAtendimento`

Objetos de valor com `@Embeddable`:

- `Endereco`
- `CoordenadaGeografica`
- `AreaMonitorada`

## Regras de negócio implementadas

1. Um alerta só pode ser aberto se o risco calculado for pelo menos `MEDIO`.
2. Não é permitido abrir alerta duplicado para a mesma região e tipo de risco nas últimas 24 horas.
3. O nível do alerta é calculado por estratégia de risco, usando dados orbitais.
4. Uma equipe só pode ser alocada se estiver disponível.
5. Ao alocar uma equipe, o alerta muda para `EM_ATENDIMENTO`.
6. Alertas `RESOLVIDO` ou `CANCELADO` não recebem novas equipes.
7. Um alerta só pode ser resolvido se tiver equipe responsável.
8. Ao resolver ou cancelar um alerta, as equipes são liberadas.
9. Uma rota só pode ser planejada para alerta `EM_ATENDIMENTO`.
10. Uma rota só pode ser planejada para equipe já alocada ao alerta.
11. O custo operacional da rota é calculado automaticamente com base na distância.

## Decisões arquiteturais

A aplicação foi organizada em camadas:

```text
controller  -> camada REST
dto         -> objetos de entrada e saída da API
service     -> casos de uso, transações e regras de negócio
domain      -> entidades, enums, value objects e estratégias
repository  -> persistência com Spring Data JPA
exception   -> tratamento global de exceções
config      -> inicialização de dados de teste
```

Os controllers são enxutos e delegam regras para os serviços. A API não expõe entidades JPA diretamente, usando DTOs para entrada e saída.

## Decisões técnicas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate
- H2 Database em memória para execução local
- Maven
- Bean Validation
- REST com JSON
- Paginação com `Pageable`
- Tratamento global de exceções com `@RestControllerAdvice`
- Controle transacional com `@Transactional`

## Relacionamentos ORM

- `RegiaoRural` 1:N `Produtor`
- `RegiaoRural` 1:N `DadoOrbital`
- `RegiaoRural` 1:N `AlertaAgroclimatico`
- `Cooperativa` 1:N `Produtor`
- `AlertaAgroclimatico` N:1 `DadoOrbital`
- `AlertaAgroclimatico` N:N `EquipeResposta`
- `AlertaAgroclimatico` 1:N `RotaAtendimento`
- `RotaAtendimento` N:1 `EquipeResposta`

## Design Patterns utilizados

### Strategy

O cálculo de criticidade utiliza o padrão **Strategy**. Cada tipo de risco possui uma estratégia de cálculo:

- `SecaStrategy`
- `GeadaStrategy`
- `QueimadaStrategy`
- `ChuvaExtremaStrategy`

Isso permite adicionar novos tipos de risco sem alterar o fluxo principal de geração de alertas.

### DTO Pattern

A aplicação utiliza DTOs para separar contrato de API das entidades JPA.

### Service Layer

Regras de negócio, transações e operações compostas ficam nos serviços, evitando regra de negócio nos controllers.

## Como executar

Pré-requisitos:

- Java 21+
- Maven 3.9+

Execute:

```bash
mvn spring-boot:run
```

A aplicação sobe em:

```text
http://localhost:8080
```

Console H2:

```text
http://localhost:8080/h2-console
```

Dados do H2:

```text
JDBC URL: jdbc:h2:mem:orbitagrodb
User: sa
Password: vazio
```

## Endpoints principais

### Regiões rurais

```http
POST /api/regioes
GET  /api/regioes?page=0&size=10
GET  /api/regioes/{id}
```

### Cooperativas

```http
POST /api/cooperativas
GET  /api/cooperativas?page=0&size=10
```

### Produtores

```http
POST /api/produtores
GET  /api/produtores?page=0&size=10
GET  /api/produtores?regiaoId=1&page=0&size=10
```

### Dados orbitais

```http
POST /api/dados-orbitais
GET  /api/dados-orbitais?page=0&size=10
GET  /api/dados-orbitais?regiaoId=1&page=0&size=10
```

### Equipes

```http
POST /api/equipes
GET  /api/equipes?page=0&size=10
GET  /api/equipes/disponiveis?page=0&size=10
```

### Alertas

```http
POST  /api/alertas/gerar
GET   /api/alertas?page=0&size=10
GET   /api/alertas?status=ABERTO&page=0&size=10
GET   /api/alertas?nivel=CRITICO&page=0&size=10
GET   /api/alertas/{id}
PATCH /api/alertas/{alertaId}/equipes/{equipeId}
PATCH /api/alertas/{alertaId}/status
```

### Rotas

```http
POST /api/rotas
GET  /api/rotas?page=0&size=10
GET  /api/rotas?alertaId=1&page=0&size=10
```

## Exemplos de requisição

### Gerar alerta a partir de dado orbital

```bash
curl -X POST http://localhost:8080/api/alertas/gerar \
  -H "Content-Type: application/json" \
  -d '{
    "dadoOrbitalId": 1,
    "tipoRisco": "SECA",
    "descricao": "Região com baixa umidade do solo, alto índice de seca e queda no NDVI."
  }'
```

### Alocar equipe ao alerta

```bash
curl -X PATCH http://localhost:8080/api/alertas/1/equipes/1
```

### Planejar rota de atendimento

```bash
curl -X POST http://localhost:8080/api/rotas \
  -H "Content-Type: application/json" \
  -d '{
    "alertaId": 1,
    "equipeId": 1,
    "origem": "Escola Rural Conectada OrbitAgro",
    "destino": "Comunidade Boa Esperança",
    "coordenadaDestino": { "latitude": -7.51, "longitude": -46.02 },
    "distanciaKm": 38.5,
    "tempoEstimadoMinutos": 62
  }'
```

### Resolver alerta

```bash
curl -X PATCH http://localhost:8080/api/alertas/1/status \
  -H "Content-Type: application/json" \
  -d '{ "status": "RESOLVIDO" }'
```

## Participação dos membros

| Integrante | Participação |
|---|---|
| Diego Garcia Tosta | Modelagem de domínio, entidades, documentação e testes dos endpoints |
| Joud Jihad Jaber | Serviços de negócio, regras operacionais, DTOs e validações |
| Lucca Pereira | Arquitetura do projeto, API REST, persistência JPA e integração da solução OrbitAgro Guard |

## Como subir para o GitHub

```bash
git init
git add .
git commit -m "feat: entrega DDD OrbitAgro Guard API"
git branch -M main
git remote add origin URL_DO_REPOSITORIO
git push -u origin main
```

> A entrega final da disciplina deve ser o link do repositório GitHub, não o arquivo ZIP.
