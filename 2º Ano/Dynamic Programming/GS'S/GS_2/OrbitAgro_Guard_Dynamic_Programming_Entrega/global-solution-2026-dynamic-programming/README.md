# OrbitAgro Guard - Dynamic Programming

Entrega da Global Solution 2026 - Dynamic Programming / FIAP.

## Grupo

| Integrante | RM |
|---|---:|
| Diego Garcia Tosta | 556724 |
| Joud Jihad Jaber | 556482 |
| Lucca Pereira | 560731 |

## Tema

**OrbitAgro Guard** e uma plataforma de economia espacial aplicada a agricultura familiar. O modulo desta entrega otimiza rotas de resposta a riscos agroclimaticos usando grade geoespacial, custos logisticos, indice de risco e probabilidade historica de ocorrencia.

A solucao foi instanciada em dois cenarios brasileiros exigidos pelo enunciado:

1. **Cenario A - Seca no Cerrado e Nordeste / MATOPIBA**: grade 50 x 50 representando celulas rurais com risco de estiagem severa.
2. **Cenario D - Conectividade Rural via Satelite**: grade 50 x 50 representando municipios/celulas com baixa conectividade, priorizando implantacao de estacoes terrestres LEO.

Os dados deste prototipo sao **sinteticos justificados**. Eles foram compostos a partir das variaveis indicadas no enunciado: risco `r[i][j]`, custo logistico `c[i][j]`, probabilidade historica `p[i][j]` e celulas bloqueadas. O codigo esta preparado para trocar os CSVs por dados reais de fontes como NASA Earthdata, INPE, INMET, ANA, IBGE e ANATEL.

## Estrutura

```text
global-solution-2026-dynamic-programming/
├── README.md
├── requirements.txt
├── data/
│   ├── raw/                 # descricao e metadados dos cenarios
│   └── processed/           # grades CSV de custo, risco, probabilidade e bloqueios
├── src/
│   ├── brute_force.py        # baseline para N,M <= 5
│   ├── dynamic_programming.py# bottom-up, top-down e reconstrucao do caminho
│   ├── monte_carlo.py        # simulacao K >= 10000 com distribuicao Beta
│   ├── performance_monitor.py# tempo, memoria, chamadas/iteracoes e escalabilidade
│   ├── scenario_generator.py # geracao dos cenarios brasileiros
│   ├── visualizations.py     # figuras obrigatorias
│   └── run_analysis.py       # executa a analise completa
├── notebooks/
│   └── analise_resultados.ipynb
├── tests/
│   └── test_algorithms.py
├── figures/                 # graficos obrigatorios
├── results/                 # resultados em JSON/CSV
└── report/
    └── relatorio_final.pdf  # relatorio tecnico ate 4 paginas
```

## Como executar

### 1. Criar ambiente

```bash
python -m venv .venv
# Windows
.venv\Scripts\activate
# Linux/Mac
source .venv/bin/activate
pip install -r requirements.txt
```

### 2. Rodar testes

```bash
pytest -q
```

### 3. Gerar dados, resultados e graficos

```bash
python -m src.run_analysis
```

O comando gera:

- grades dos cenarios em `data/processed/`;
- metricas em `results/`;
- figuras obrigatorias em `figures/`.

## Algoritmos implementados

### Forca Bruta

Enumera recursivamente todos os caminhos validos da origem ate o destino para instancias pequenas. Serve como oraculo para validar a programacao dinamica.

### Programacao Dinamica 2D

Implementa:

- tabulacao bottom-up;
- memoizacao top-down;
- tratamento de celulas bloqueadas;
- peso de risco e probabilidade no custo efetivo;
- destino flexivel: canto inferior direito ou qualquer celula da ultima linha/coluna;
- reconstrucao do caminho otimo.

### Monte Carlo

Executa `K >= 10000` cenarios, amostrando `p'[i][j]` por distribuicao Beta calibrada a partir de `p[i][j]`. Para cada cenario, roda DP e registra o custo otimo, media, mediana, desvio padrao e intervalo de confianca de 95%.

## Figuras obrigatorias geradas

1. Dispersao: custo otimo x tempo computacional.
2. Histograma + boxplot da distribuicao de custo otimo por Monte Carlo.
3. Heatmap da tabela DP com caminho otimo destacado.
4. Curva de escalabilidade: tempo de execucao x N.
5. Analise de sensibilidade com `p[i][j] -20%`, baseline e `p[i][j] +20%`.

## Referencias

- NASA Earthdata / MODIS NDVI / SRTM: https://earthdata.nasa.gov
- INPE PRODES/DETER: https://terrabrasilis.dpi.inpe.br
- INMET BDMEP: https://bdmep.inmet.gov.br
- ANA HidroWeb: https://www.snirh.gov.br/hidroweb
- ANATEL Mapa de Conectividade: https://mapa.anatel.gov.br
- Bellman, R. (1957). Dynamic Programming. Princeton University Press.
- Cormen et al. (2022). Introduction to Algorithms, 4th Ed. MIT Press.
- Kroese et al. (2011). Handbook of Monte Carlo Methods. Wiley.
