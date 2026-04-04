# ===============================
# DADOS DE EXEMPLO
# ===============================

leads = [
    {"nome": "Lucca", "cpf": "123", "email": "a@gmail.com"},
    {"nome": "João", "cpf": "456", "email": "b@gmail.com"},
    {"nome": "Maria", "cpf": "789", "email": "c@gmail.com"},
]

novo_lead = {"nome": "Lucca", "cpf": "123", "email": "a@gmail.com"}


# ===============================
# 1. RECURSÃO - VERIFICAR DUPLICIDADE
# ===============================

def verificar_duplicidade(lista, lead, index=0):
    if index >= len(lista):
        return False

    atual = lista[index]

    if (atual["cpf"] == lead["cpf"] or
        atual["email"] == lead["email"]):
        return True

    return verificar_duplicidade(lista, lead, index + 1)


print("Duplicado:", verificar_duplicidade(leads, novo_lead))


# ===============================
# 2. MEMOIZAÇÃO
# ===============================

memo = {}

def verificar_duplicidade_memo(lista, lead, index=0):
    chave = (index, lead["cpf"], lead["email"])

    if chave in memo:
        return memo[chave]

    if index >= len(lista):
        return False

    atual = lista[index]

    if (atual["cpf"] == lead["cpf"] or
        atual["email"] == lead["email"]):
        memo[chave] = True
        return True

    resultado = verificar_duplicidade_memo(lista, lead, index + 1)
    memo[chave] = resultado
    return resultado


print("Duplicado com memo:", verificar_duplicidade_memo(leads, novo_lead))


# ===============================
# 3. OTIMIZAÇÃO DE AGENDA (RECURSIVO)
# ===============================

# horários disponíveis (em minutos)
horarios = [30, 60, 90, 120]
tempo_consulta = 150

def melhor_encaixe(tempos, alvo, index=0):
    if alvo == 0:
        return True
    if alvo < 0 or index >= len(tempos):
        return False

    # escolhe ou não o horário
    usar = melhor_encaixe(tempos, alvo - tempos[index], index + 1)
    nao_usar = melhor_encaixe(tempos, alvo, index + 1)

    return usar or nao_usar


print("Consegue encaixar consulta?", melhor_encaixe(horarios, tempo_consulta))
