# Dynamic Programming - Sprint 4

## CRM Hospital São Rafael

**Integrantes:**

- Diego Garcia Tosta — RM: 556724
- Joud Jihad Jaber — RM: 556482
- Lucca Pereira — RM: 560731

---

## 1. Objetivo

O objetivo desta Sprint 4 é modelar o fluxo do CRM Hospital São Rafael como um grafo direcionado e utilizar o algoritmo de Dijkstra para encontrar o melhor caminho entre a etapa de entrada de um **Lead** e a etapa de **Confirmação** de uma consulta.

A proposta está relacionada ao fluxo real de atendimento do CRM, onde um lead pode passar por etapas como primeiro atendimento, qualificação, cadastro, escolha de procedimento, agendamento e confirmação.

---

## 2. Representação do fluxo como grafo

O fluxo foi representado como um **grafo direcionado**, onde cada nó representa uma etapa do processo do CRM e cada aresta representa uma transição possível entre essas etapas.

Os custos das arestas representam uma estimativa de esforço ou tempo operacional. Quanto menor o custo, mais rápido e eficiente é o avanço entre as etapas.

### Principais nós do grafo

- Lead
- Primeiro Atendimento
- Recontato
- Aguardando Retorno
- Qualificação
- Cadastro do Paciente
- Escolha do Procedimento
- Orçamento
- Agendamento
- Reagendamento
- Confirmação
- Perdido
- Cancelado

### Exemplo de conexões

```text
Lead -> Primeiro Atendimento
Primeiro Atendimento -> Qualificação
Qualificação -> Cadastro do Paciente
Cadastro do Paciente -> Escolha do Procedimento
Escolha do Procedimento -> Agendamento
Agendamento -> Confirmação
```

Também foram adicionados caminhos alternativos, como recontato, aguardando retorno, orçamento, reagendamento, cancelamento e perda do lead, para deixar o fluxo mais próximo de uma situação real de CRM.

---

## 3. Algoritmo utilizado

O algoritmo escolhido foi o **Dijkstra**, pois ele permite encontrar o menor caminho entre dois pontos de um grafo com pesos positivos.

No projeto, ele foi utilizado para encontrar o caminho de menor custo entre:

```text
Lead -> Confirmação
```

O algoritmo percorre o grafo avaliando os custos acumulados e sempre priorizando o caminho mais barato até encontrar o destino.

---

## 4. Menor caminho encontrado

Ao executar o código, o menor caminho encontrado foi:

```text
Lead -> Primeiro Atendimento -> Qualificação -> Cadastro do Paciente -> Escolha do Procedimento -> Agendamento -> Confirmação
```

### Custo total

```text
10
```

---

## 5. Interpretação do resultado

O caminho encontrado é o mais eficiente porque representa o fluxo mais direto entre a entrada do lead e a confirmação da consulta.

Esse fluxo evita etapas que aumentam o tempo ou o esforço do atendimento, como:

- Recontato;
- Aguardando retorno;
- Orçamento antes do agendamento;
- Reagendamento;
- Cancelamento;
- Perda do lead.

Na prática, isso mostra que o CRM deve priorizar um atendimento rápido, qualificação objetiva, cadastro correto do paciente e agendamento direto. Dessa forma, o hospital reduz atrasos no atendimento, melhora a organização interna e aumenta a chance de converter leads em consultas confirmadas.

---

## 6. Como executar

Para executar o projeto, basta ter o Python instalado e rodar o arquivo principal:

```bash
python crm_dijkstra.py
```

O programa exibirá:

1. O grafo direcionado do fluxo do CRM;
2. O menor caminho entre Lead e Confirmação;
3. O custo total;
4. A interpretação do resultado.

---

## 7. Conclusão

A atividade demonstrou como grafos podem ser aplicados em um contexto de CRM hospitalar. Com o algoritmo de Dijkstra, foi possível identificar o caminho mais eficiente dentro do processo de atendimento, ajudando a compreender quais etapas tornam o fluxo mais rápido e quais etapas podem gerar atrasos.

Esse tipo de análise pode apoiar futuras melhorias do CRM, principalmente em automações, priorização de leads e otimização do atendimento ao paciente.
