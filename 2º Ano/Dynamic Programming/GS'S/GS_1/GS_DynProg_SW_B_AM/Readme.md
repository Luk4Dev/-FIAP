## 📘 SmartMatch — Sistema de Recomendação (GS)
# Integrantes

Diego Garcia Tosta — RM: 556724

Joud Jihad Jaber — RM: 556482

Lucca Pereira — RM: 560731

## 📝 Sobre o Projeto

Esse projeto é a nossa GS de Engenharia de Software.
A ideia foi criar um sistema de recomendação simples chamado SmartMatch, que indica cursos para os usuários com base no que eles já compraram e nos interesses deles.

A gente usou várias coisas que vimos na matéria, como:

dicionários, listas e grafos

algoritmo guloso

recursão

dividir e conquistar

memoization

Tentamos deixar tudo o mais organizado possível pra ficar fácil de entender.

## 📌 1. Modelagem dos Dados

Primeiro, montamos os dados:

Criamos 15 cursos, cada um com tags e uma nota

Criamos 10 usuários, cada um com seus interesses e compras

Depois disso, montamos um grafo conectando os cursos que compartilham tags.
Esse grafo é usado depois para encontrar cursos parecidos.

## 📌 2. Recomendação (Algoritmo Guloso)

Aqui foi onde fizemos o sistema de recomendação.

O algoritmo olha os cursos que o usuário já comprou, vê quais cursos são vizinhos no grafo e calcula um score de similaridade, usando:

tags em comum + 0.1 * nota do curso


Usamos memoization pra não recalcular score repetido.
No fim, ele pega os 3 cursos com maior score e recomenda pro usuário.

## 📌 3. Agrupamento dos Usuários (Recursão)

Também fizemos um agrupamento usando o método de Dividir e Conquistar.

Criamos uma função de distância entre dois usuários baseada nos interesses deles.
Depois, dividimos a lista de usuários recursivamente para formar clusters menores.

Por fim, dentro de cada cluster, pegamos os cursos mais comprados e recomendamos para aquele grupo.

## 📊 O que o código mostra quando roda

O grafo completo de cursos

A recomendação personalizada de cada usuário

Os clusters formados

E as recomendações para cada cluster

Tudo o que foi pedido no PDF está funcionando certinho.

## ✔️ Conclusão

O projeto cobre toda a GS: modelagem, grafo, guloso, memoization, recursão e agrupamento.

A ideia foi mostrar como tudo isso se conecta num sistema de recomendação simples, mas funcional.
