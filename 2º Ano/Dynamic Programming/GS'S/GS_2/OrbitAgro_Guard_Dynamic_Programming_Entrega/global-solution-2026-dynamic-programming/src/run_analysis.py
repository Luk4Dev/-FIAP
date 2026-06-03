from __future__ import annotations

import json
from pathlib import Path

import numpy as np
import pandas as pd

from .brute_force import brute_force_min_path
from .dynamic_programming import bottom_up_dp, top_down_dp
from .monte_carlo import monte_carlo_cost_distribution, sensitivity_analysis
from .performance_monitor import run_performance_suite
from .scenario_generator import all_scenarios, effective_cost, save_scenario
from .visualizations import (
    save_bruteforce_growth,
    save_heatmap,
    save_mc_hist_box,
    save_scalability,
    save_scatter_tradeoff,
    save_sensitivity,
)


def main() -> None:
    root = Path(__file__).resolve().parents[1]
    data_dir = root / 'data' / 'processed'
    raw_dir = root / 'data' / 'raw'
    fig_dir = root / 'figures'
    res_dir = root / 'results'
    for d in [data_dir, raw_dir, fig_dir, res_dir]:
        d.mkdir(parents=True, exist_ok=True)

    scenarios = all_scenarios(50, 50)
    metadata = []
    scenario_summaries = {}

    for s in scenarios:
        files = save_scenario(s, data_dir)
        metadata.append({'key': s.key, 'name': s.name, 'files': files})

        eff = effective_cost(s.cost, s.risk, s.probability, s.blocked)
        dp_bu = bottom_up_dp(eff, any_edge_destination=True)
        dp_td = top_down_dp(eff, any_edge_destination=True)

        # Monte Carlo full requirement K >= 10000 on a 20x20 slice for runtime-safe stochastic analysis.
        sl = np.s_[:20, :20]
        mc = monte_carlo_cost_distribution(s.cost[sl], s.risk[sl], s.probability[sl], s.blocked[sl], k=10_000, any_edge_destination=True)

        scenario_summaries[s.key] = {
            'name': s.name,
            'shape': list(s.cost.shape),
            'bottom_up_cost': dp_bu.cost,
            'top_down_cost': dp_td.cost,
            'bottom_up_iterations': dp_bu.iterations,
            'top_down_calls': dp_td.iterations,
            'destination': dp_bu.destination,
            'path_length': len(dp_bu.path),
            'blocked_cells': int(s.blocked.sum()),
            'monte_carlo_20x20': mc.as_dict(),
        }

    selected = scenarios[0]
    eff = effective_cost(selected.cost, selected.risk, selected.probability, selected.blocked)
    dp_result = bottom_up_dp(eff, any_edge_destination=True)
    save_heatmap(dp_result.dp_table, dp_result.path, fig_dir / 'fig_03_heatmap_dp_path.png',
                 title='MATOPIBA: tabela DP com caminho otimo de resposta')

    # Brute force validation on small slice
    small = eff[:5, :5].copy()
    small[small == -1] = 99  # avoid accidental infeasibility in validation slice
    bf = brute_force_min_path(small)
    dp_small = bottom_up_dp(small)
    validation = {
        'brute_force_cost': bf.cost,
        'dp_cost': dp_small.cost,
        'same_cost': abs(bf.cost - dp_small.cost) < 1e-9,
        'brute_force_calls': bf.calls,
        'paths_evaluated': bf.paths_evaluated,
    }

    # Monte Carlo and sensitivity on 20x20 MATOPIBA slice with K >= 10000.
    sl = np.s_[:20, :20]
    mc = monte_carlo_cost_distribution(selected.cost[sl], selected.risk[sl], selected.probability[sl], selected.blocked[sl], k=10_000)
    save_mc_hist_box(mc.costs, fig_dir / 'fig_02_monte_carlo_hist_box.png')
    np.savetxt(res_dir / 'monte_carlo_costs.csv', mc.costs, delimiter=',', fmt='%.6f')

    sens = sensitivity_analysis(selected.cost[sl], selected.risk[sl], selected.probability[sl], selected.blocked[sl], k=10_000)
    sens_summary = {k: v.as_dict() for k, v in sens.items()}
    save_sensitivity(sens_summary, fig_dir / 'fig_05_sensitivity.png')

    records = run_performance_suite()
    perf_df = pd.DataFrame([r.as_dict() for r in records])
    perf_df.to_csv(res_dir / 'performance_metrics.csv', index=False)
    save_scatter_tradeoff(perf_df, fig_dir / 'fig_01_tradeoff_scatter.png')
    save_scalability(perf_df, fig_dir / 'fig_04_scalability.png')
    save_bruteforce_growth(fig_dir / 'fig_extra_bruteforce_growth.png')

    summary = {
        'team': [
            {'name': 'Diego Garcia Tosta', 'rm': '556724'},
            {'name': 'Joud Jihad Jaber', 'rm': '556482'},
            {'name': 'Lucca Pereira', 'rm': '560731'},
        ],
        'scenarios': scenario_summaries,
        'validation': validation,
        'monte_carlo_matopiba_20x20': mc.as_dict(),
        'sensitivity_matopiba_20x20': sens_summary,
    }
    (res_dir / 'analysis_summary.json').write_text(json.dumps(summary, ensure_ascii=False, indent=2), encoding='utf-8')
    (raw_dir / 'fonte_dados_sinteticos.txt').write_text(
        'Dados sinteticos justificados para prototipo academico. Variaveis baseadas no enunciado: custo logistico, risco agroclimatico, probabilidade historica e bloqueios. Substituiveis por NASA Earthdata, INPE, INMET, ANA, IBGE e ANATEL.\n',
        encoding='utf-8'
    )
    print(json.dumps(summary, ensure_ascii=False, indent=2))


if __name__ == '__main__':
    main()
