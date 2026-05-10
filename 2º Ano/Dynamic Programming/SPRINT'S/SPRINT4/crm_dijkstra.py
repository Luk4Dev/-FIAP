"""
Dynamic Programming - Sprint 4
CRM Hospital Sao Rafael

Tema: Grafos e Dijkstra no fluxo do CRM

Objetivo:
Modelar o fluxo de atendimento do CRM como um grafo direcionado e
encontrar o menor caminho entre "Lead" e "Confirmacao" usando Dijkstra.

Integrantes:
- Diego Garcia Tosta - RM: 556724
- Joud Jihad Jaber - RM: 556482
- Lucca Pereira - RM: 560731
"""

import heapq


def dijkstra(grafo, origem, destino):
    """
    Implementa o algoritmo de Dijkstra para encontrar o menor caminho
    entre uma origem e um destino em um grafo direcionado com pesos positivos.
    """

    fila = [(0, origem, [origem])]
    visitados = set()

    while fila:
        custo_atual, no_atual, caminho = heapq.heappop(fila)

        if no_atual in visitados:
            continue

        visitados.add(no_atual)

        if no_atual == destino:
            return custo_atual, caminho

        for vizinho, custo in grafo.get(no_atual, {}).items():
            if vizinho not in visitados:
                novo_custo = custo_atual + custo
                novo_caminho = caminho + [vizinho]
                heapq.heappush(fila, (novo_custo, vizinho, novo_caminho))

    return None, []


def mostrar_grafo(grafo):
    print("Fluxo do CRM representado como grafo direcionado:\n")

    for etapa, proximas_etapas in grafo.items():
        for destino, custo in proximas_etapas.items():
            print(f"{etapa} -> {destino} | custo: {custo}")


def main():
    # Os custos representam uma estimativa de esforco/tempo operacional.
    # Quanto menor o custo, mais direto e eficiente e o fluxo.
    fluxo_crm = {
        "Lead": {
            "Primeiro Atendimento": 2,
            "Recontato": 5,
            "Aguardando Retorno": 7
        },
        "Primeiro Atendimento": {
            "Qualificacao": 2,
            "Recontato": 4
        },
        "Recontato": {
            "Qualificacao": 3,
            "Aguardando Retorno": 4
        },
        "Aguardando Retorno": {
            "Recontato": 2,
            "Perdido": 8
        },
        "Qualificacao": {
            "Cadastro do Paciente": 2,
            "Perdido": 6
        },
        "Cadastro do Paciente": {
            "Escolha do Procedimento": 1
        },
        "Escolha do Procedimento": {
            "Agendamento": 2,
            "Orcamento": 3
        },
        "Orcamento": {
            "Agendamento": 2,
            "Aguardando Retorno": 4
        },
        "Agendamento": {
            "Confirmacao": 1,
            "Reagendamento": 4,
            "Cancelado": 6
        },
        "Reagendamento": {
            "Confirmacao": 2
        },
        "Perdido": {},
        "Cancelado": {},
        "Confirmacao": {}
    }

    origem = "Lead"
    destino = "Confirmacao"

    mostrar_grafo(fluxo_crm)

    custo_total, menor_caminho = dijkstra(fluxo_crm, origem, destino)

    print("\nResultado do algoritmo de Dijkstra:")
    print("-" * 40)

    if menor_caminho:
        print(f"Origem: {origem}")
        print(f"Destino: {destino}")
        print(f"Menor caminho encontrado: {' -> '.join(menor_caminho)}")
        print(f"Custo total: {custo_total}")

        print("\nInterpretacao:")
        print(
            "O menor caminho representa o fluxo mais direto para transformar "
            "um lead em uma consulta confirmada. Esse caminho evita etapas "
            "mais demoradas, como recontato, aguardando retorno, reagendamento "
            "ou perda do lead. Por isso, ele e mais eficiente para o CRM."
        )
    else:
        print("Nao foi encontrado caminho entre a origem e o destino.")


if __name__ == "__main__":
    main()
