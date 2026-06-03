from __future__ import annotations

from pathlib import Path
from typing import Iterable

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd


def save_heatmap(dp_table: np.ndarray, path_cells: list[tuple[int, int]], out_path: str | Path,
                 title: str = 'Tabela DP com caminho otimo') -> None:
    out = Path(out_path)
    arr = np.array(dp_table, dtype=float)
    arr[~np.isfinite(arr)] = np.nan
    plt.figure(figsize=(8, 6))
    plt.imshow(arr, aspect='auto')
    if path_cells:
        y = [p[0] for p in path_cells]
        x = [p[1] for p in path_cells]
        plt.plot(x, y, linewidth=2.2, marker='o', markersize=2.8, label='Caminho otimo')
        plt.legend(loc='upper right')
    plt.title(title)
    plt.xlabel('Coluna da grade')
    plt.ylabel('Linha da grade')
    plt.colorbar(label='Custo minimo acumulado')
    plt.tight_layout()
    plt.savefig(out, dpi=180)
    plt.close()


def save_mc_hist_box(costs: np.ndarray, out_path: str | Path) -> None:
    out = Path(out_path)
    fig = plt.figure(figsize=(8, 6))
    gs = fig.add_gridspec(2, 1, height_ratios=[3, 1])
    ax1 = fig.add_subplot(gs[0])
    ax2 = fig.add_subplot(gs[1])
    ax1.hist(costs, bins=40)
    ax1.set_title('Monte Carlo: distribuicao do custo otimo simulado')
    ax1.set_xlabel('Custo otimo')
    ax1.set_ylabel('Frequencia')
    ax2.boxplot(costs, vert=False)
    ax2.set_xlabel('Custo otimo')
    fig.tight_layout()
    plt.savefig(out, dpi=180)
    plt.close()


def save_scatter_tradeoff(records: pd.DataFrame, out_path: str | Path) -> None:
    out = Path(out_path)
    df = records.copy()
    df = df[np.isfinite(df['cost']) & np.isfinite(df['time_ms'])]
    plt.figure(figsize=(8, 5.5))
    for alg, grp in df.groupby('algorithm'):
        plt.scatter(grp['time_ms'], grp['cost'], label=alg, s=55)
    plt.title('Trade-off: custo otimo x tempo computacional')
    plt.xlabel('Tempo de execucao (ms)')
    plt.ylabel('Custo otimo / medio')
    plt.legend(fontsize=8)
    plt.tight_layout()
    plt.savefig(out, dpi=180)
    plt.close()


def save_scalability(records: pd.DataFrame, out_path: str | Path) -> None:
    out = Path(out_path)
    df = records.copy()
    df = df[np.isfinite(df['time_ms'])]
    plt.figure(figsize=(8, 5.5))
    for alg, grp in df.groupby('algorithm'):
        grp = grp.sort_values('n')
        plt.plot(grp['n'], grp['time_ms'], marker='o', label=alg)
    plt.title('Escalabilidade empirica: tempo de execucao x N')
    plt.xlabel('N em grade N x N')
    plt.ylabel('Tempo de execucao (ms)')
    plt.yscale('log')
    plt.legend(fontsize=8)
    plt.tight_layout()
    plt.savefig(out, dpi=180)
    plt.close()


def save_sensitivity(summary: dict[str, dict], out_path: str | Path) -> None:
    out = Path(out_path)
    labels = list(summary.keys())
    means = [summary[k]['mean'] for k in labels]
    lows = [summary[k]['mean'] - summary[k]['ci95_low'] for k in labels]
    highs = [summary[k]['ci95_high'] - summary[k]['mean'] for k in labels]
    plt.figure(figsize=(8, 5.5))
    plt.errorbar(labels, means, yerr=[lows, highs], marker='o', capsize=5)
    plt.title('Analise de sensibilidade: impacto de p[i][j] ± 20%')
    plt.xlabel('Cenario de probabilidade')
    plt.ylabel('Custo otimo medio simulado')
    plt.tight_layout()
    plt.savefig(out, dpi=180)
    plt.close()


def save_bruteforce_growth(out_path: str | Path, max_n: int = 12) -> None:
    from math import comb
    ns = list(range(2, max_n + 1))
    paths = [comb(2 * n - 2, n - 1) for n in ns]
    plt.figure(figsize=(8, 5.5))
    plt.plot(ns, paths, marker='o')
    plt.yscale('log')
    plt.title('Crescimento exponencial do numero de caminhos - Forca Bruta')
    plt.xlabel('N em grade N x N')
    plt.ylabel('Quantidade de caminhos (escala log)')
    plt.tight_layout()
    plt.savefig(out_path, dpi=180)
    plt.close()
