README.md
# CP3 - Otimizador Verde de Grade de Carregamento (Greedy)

Integrantes simulados:
- Lucca → greedy.py  
- Diego → data.py  
- Joud → main.py  

## Resumo
Algoritmo guloso para posicionar estações de carregamento de veículos elétricos, equilibrando:
- Cobertura da demanda
- Uso de energia renovável
- Restrição de orçamento

## Função objetivo


score = 3 * cobertura + 2 * estações_renováveis - (custo_total / 10)


## Como executar
1. Coloque os 3 arquivos `.py` na mesma pasta.
2. No terminal (VSCode ou CMD), execute:


python main.py


## Observações
- O método é **guloso**: escolhe localmente a melhor opção, sem garantir o ótimo global.
- É idealizado para fins didáticos e não usa dados reais de cidade.