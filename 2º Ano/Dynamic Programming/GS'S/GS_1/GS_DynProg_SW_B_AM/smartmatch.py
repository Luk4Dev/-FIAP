# ---------------------------------------------
# SMARTMATCH - Global Solution (DP - FIAP)
# Sistema de recomendação usando:
# • Grafos
# • Guloso + Memoization
# • Dividir e Conquistar + Recursão
# ---------------------------------------------

from collections import defaultdict, Counter
import json

# -------------------------------------------------
# 1. BASE DE DADOS (CURSOS E USUÁRIOS)
# -------------------------------------------------

cursos = {
    "Python Básico": {"tags": ["Python", "Lógica"], "nota": 4.6},
    "Machine Learning": {"tags": ["IA", "Matemática", "Python"], "nota": 4.9},
    "IA Ética": {"tags": ["IA", "Ética", "Sociedade"], "nota": 4.8},
    "Visualização de Dados": {"tags": ["BI", "Design", "Python"], "nota": 4.7},
    "AWS Intro": {"tags": ["Nuvem", "Infraestrutura"], "nota": 4.5},
    "Segurança em Nuvem": {"tags": ["Segurança", "Nuvem"], "nota": 4.3},
    "Banco de Dados SQL": {"tags": ["SQL", "Dados"], "nota": 4.4},
    "Big Data": {"tags": ["Dados", "Escalabilidade"], "nota": 4.6},
    "Deep Learning": {"tags": ["IA", "Python", "Redes Neurais"], "nota": 4.9},
    "API Design": {"tags": ["APIs", "Back-End", "Python"], "nota": 4.5},
    "Microsserviços": {"tags": ["Back-End", "Nuvem"], "nota": 4.4},
    "Kubernetes": {"tags": ["DevOps", "Nuvem", "Contêineres"], "nota": 4.7},
    "Docker Essentials": {"tags": ["DevOps", "Contêineres"], "nota": 4.6},
    "Data Storytelling": {"tags": ["Comunicação", "BI"], "nota": 4.2},
    "Power BI": {"tags": ["BI", "Dados"], "nota": 4.5}
}

usuarios = {
    "u1": {"interesses": ["Python", "IA", "Dados"], "compras": ["Python Básico", "Machine Learning"]},
    "u2": {"interesses": ["Nuvem", "Segurança", "Infraestrutura"], "compras": ["AWS Intro"]},
    "u3": {"interesses": ["BI", "Design", "Python"], "compras": ["Visualização de Dados", "Power BI"]},
    "u4": {"interesses": ["Back-End", "APIs", "Python"], "compras": ["API Design"]},
    "u5": {"interesses": ["DevOps", "Nuvem"], "compras": ["Docker Essentials", "Kubernetes"]},
    "u6": {"interesses": ["Dados", "SQL"], "compras": ["Banco de Dados SQL", "Big Data"]},
    "u7": {"interesses": ["Comunicação", "BI", "Design"], "compras": ["Data Storytelling"]},
    "u8": {"interesses": ["IA", "Python", "Redes Neurais"], "compras": ["Deep Learning"]},
    "u9": {"interesses": ["Segurança", "Infraestrutura"], "compras": ["Segurança em Nuvem"]},
    "u10": {"interesses": ["Python", "Back-End"], "compras": ["Microsserviços"]},
}

# -------------------------------------------------
# 2. GRAFO – CONECTA CURSOS QUE COMPARTILHAM TAGS
# -------------------------------------------------

def build_graph(cursos):
    grafo = defaultdict(list)
    cursos_lista = list(cursos.keys())

    for i, a in enumerate(cursos_lista):
        tags_a = set(cursos[a]["tags"])

        for j in range(i + 1, len(cursos_lista)):
            b = cursos_lista[j]
            tags_b = set(cursos[b]["tags"])

            if tags_a & tags_b:
                grafo[a].append(b)
                grafo[b].append(a)

    for c in cursos_lista:
        grafo.setdefault(c, [])
    return dict(grafo)

grafo = build_graph(cursos)

# -------------------------------------------------
# 3. RECOMENDAÇÃO GULOSA + MEMOIZATION
# -------------------------------------------------

memo_scores = {}

def similarity_score(c1, c2):
    key = (c1, c2)
    if key in memo_scores:
        return memo_scores[key]

    tags1 = set(cursos[c1]["tags"])
    tags2 = set(cursos[c2]["tags"])
    shared = len(tags1 & tags2)

    score = shared + 0.1 * cursos[c2]["nota"]
    memo_scores[key] = score
    return score

def recomendar_guloso(usuario_id, top_k=3):
    compras = set(usuarios[usuario_id]["compras"])
    scores = defaultdict(float)

    for comprado in compras:
        for viz in grafo[comprado]:
            if viz not in compras:
                scores[viz] = max(scores[viz], similarity_score(comprado, viz))

    if len(scores) < top_k:
        for curso in cursos:
            if curso not in compras and curso not in scores:
                base = [similarity_score(c, curso) for c in compras]
                scores[curso] = max(base)

    recomendados = sorted(scores.items(), key=lambda x: -x[1])
    return [c for c, _ in recomendados[:top_k]]

# -------------------------------------------------
# 4. CLUSTER DE USUÁRIOS (DIVIDIR E CONQUISTAR)
# -------------------------------------------------

memo_dist = {}

def distancia(u1, u2):
    key = tuple(sorted((u1, u2)))
    if key in memo_dist:
        return memo_dist[key]

    s1 = set(usuarios[u1]["interesses"])
    s2 = set(usuarios[u2]["interesses"])
    inter = len(s1 & s2)
    uni = len(s1 | s2)

    dist = 1 - (inter / uni)
    memo_dist[key] = dist
    return dist

def avg_dist(u, grupo):
    if len(grupo) <= 1:
        return 0
    return sum(distancia(u, x) for x in grupo if x != u) / (len(grupo) - 1)

def cluster_usuarios(lista):
    if len(lista) <= 2:
        return [lista.copy()]

    ordenado = sorted(lista, key=lambda u: avg_dist(u, lista))
    meio = len(ordenado) // 2

    esquerda = cluster_usuarios(ordenado[:meio])
    direita = cluster_usuarios(ordenado[meio:])

    return esquerda + direita

def recomendar_cluster(cluster, top_k=2):
    counter = Counter()
    for u in cluster:
        counter.update(usuarios[u]["compras"])

    ordenado = sorted(counter.items(), key=lambda x: (-x[1], -cursos[x[0]]["nota"]))
    return [c for c, _ in ordenado[:top_k]]

# -------------------------------------------------
# 5. EXECUÇÃO E SAÍDAS
# -------------------------------------------------

print("\n===== GRAFO DE CURSOS =====")
for c, viz in grafo.items():
    print(f"{c}: {viz}")

print("\n===== RECOMENDAÇÕES POR USUÁRIO =====")
for u in usuarios:
    print(f"{u}: {recomendar_guloso(u)}")

print("\n===== CLUSTERIZAÇÃO =====")
clusters = cluster_usuarios(list(usuarios.keys()))
for i, c in enumerate(clusters, start=1):
    print(f"\nCluster {i}: {c}")
    print("Recomendações:", recomendar_cluster(c))
