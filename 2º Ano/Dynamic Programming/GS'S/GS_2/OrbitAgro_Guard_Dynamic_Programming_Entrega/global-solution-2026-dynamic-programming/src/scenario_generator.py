from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path
from typing import Dict

import numpy as np


@dataclass(frozen=True)
class Scenario:
    name: str
    key: str
    cost: np.ndarray
    risk: np.ndarray
    probability: np.ndarray
    blocked: np.ndarray
    description: str


def _smooth_gradient(n: int, m: int, seed: int) -> tuple[np.ndarray, np.ndarray, np.ndarray]:
    rng = np.random.default_rng(seed)
    y = np.linspace(0, 1, n)[:, None]
    x = np.linspace(0, 1, m)[None, :]
    ridge = np.exp(-((x - 0.65) ** 2 + (y - 0.35) ** 2) / 0.08)
    noise = rng.normal(0, 0.08, size=(n, m))
    return x, y, ridge + noise


def generate_matopiba_drought(n: int = 50, m: int = 50, seed: int = 42) -> Scenario:
    """Synthetic Brazilian scenario A: drought in MATOPIBA.

    Risk is higher in the north/east corridor and in a simulated drought ridge.
    Logistic cost is higher in isolated cells and lower near a central support axis.
    Some blocked cells model roads unavailable due to operational constraints.
    """
    rng = np.random.default_rng(seed)
    x, y, ridge = _smooth_gradient(n, m, seed)

    risk = 0.35 + 0.35 * x + 0.25 * (1 - y) + 0.22 * ridge
    risk = np.clip(risk, 0.05, 0.98)

    support_axis = np.abs(x - 0.25) + np.abs(y - 0.75)
    base_cost = 8 + 8 * support_axis + rng.gamma(2.0, 0.6, size=(n, m))
    cost = np.clip(base_cost, 4, 25)

    probability = np.clip(0.20 + 0.55 * risk + rng.normal(0, 0.05, (n, m)), 0.03, 0.95)

    blocked = rng.random((n, m)) < 0.045
    # guarantee a feasible corridor
    blocked[:, 0] = False
    blocked[-1, :] = False
    blocked[0, 0] = False
    blocked[-1, -1] = False

    return Scenario(
        name='Seca no Cerrado e Nordeste / MATOPIBA',
        key='matopiba_drought',
        cost=cost,
        risk=risk,
        probability=probability,
        blocked=blocked,
        description='Grade sintetica 50x50 para estiagem severa no MATOPIBA, usando variaveis de risco, custo logistico e probabilidade historica.'
    )


def generate_rural_connectivity(n: int = 50, m: int = 50, seed: int = 7) -> Scenario:
    """Synthetic Brazilian scenario D: satellite rural connectivity.

    Risk means service priority: cells with low connectivity and relevant population.
    Cost approximates investment difficulty for a LEO ground station.
    """
    rng = np.random.default_rng(seed)
    x, y, ridge = _smooth_gradient(n, m, seed + 99)

    underserved = np.clip(0.75 - 0.35 * x + 0.20 * y + 0.20 * ridge, 0.02, 0.98)
    population_pressure = np.clip(0.25 + 0.60 * np.exp(-((x - 0.55) ** 2 + (y - 0.55) ** 2) / 0.18), 0.05, 0.95)
    risk = np.clip(0.65 * underserved + 0.35 * population_pressure, 0.05, 0.98)

    remoteness = 0.35 + 0.45 * (1 - x) + 0.30 * y + rng.normal(0, 0.05, (n, m))
    cost = np.clip(7 + 14 * remoteness + rng.gamma(1.8, 0.7, (n, m)), 5, 30)

    probability = np.clip(0.15 + 0.65 * risk + rng.normal(0, 0.04, (n, m)), 0.03, 0.95)

    blocked = rng.random((n, m)) < 0.035
    # guarantee a feasible corridor along top/right boundaries
    blocked[0, :] = False
    blocked[:, -1] = False
    blocked[0, 0] = False
    blocked[-1, -1] = False

    return Scenario(
        name='Conectividade Rural via Satelite',
        key='rural_connectivity',
        cost=cost,
        risk=risk,
        probability=probability,
        blocked=blocked,
        description='Grade sintetica 50x50 para municipios/celulas rurais com baixa conectividade, priorizando expansao de infraestrutura via satelite.'
    )


def effective_cost(cost: np.ndarray, risk: np.ndarray | None = None, probability: np.ndarray | None = None,
                   blocked: np.ndarray | None = None, risk_weight: float = 0.45,
                   probability_weight: float = 0.35, priority_credit: float = 0.25) -> np.ndarray:
    """Compose the optimization cost used by the algorithms.

    The route should minimize logistics but prioritize high-risk/high-probability cells.
    We model this as:
        effective = c * (1 + risk_weight*r + probability_weight*p) * (1 - priority_credit*r*p)

    High risk increases service complexity, while the credit term rewards covering cells
    where both risk and probability are high. Blocked cells receive -1.
    """
    c = cost.astype(float).copy()
    r = np.zeros_like(c) if risk is None else risk.astype(float)
    p = np.zeros_like(c) if probability is None else probability.astype(float)
    eff = c * (1 + risk_weight * r + probability_weight * p) * (1 - priority_credit * r * p)
    if blocked is not None:
        eff = eff.copy()
        eff[blocked] = -1.0
    return eff


def save_scenario(scenario: Scenario, out_dir: str | Path) -> Dict[str, str]:
    out = Path(out_dir)
    out.mkdir(parents=True, exist_ok=True)
    files = {}
    for name, arr in [
        ('cost', scenario.cost),
        ('risk', scenario.risk),
        ('probability', scenario.probability),
        ('blocked', scenario.blocked.astype(int)),
        ('effective_cost', effective_cost(scenario.cost, scenario.risk, scenario.probability, scenario.blocked)),
    ]:
        path = out / f'{scenario.key}_{name}.csv'
        np.savetxt(path, arr, delimiter=',', fmt='%.6f')
        files[name] = str(path)
    meta = {
        'name': scenario.name,
        'key': scenario.key,
        'description': scenario.description,
        'shape': list(scenario.cost.shape),
        'synthetic_justification': 'Dados sinteticos compostos conforme variaveis indicadas no enunciado: custo logistico, risco, probabilidade historica e celulas bloqueadas. Estrutura pronta para substituicao por fontes NASA/INPE/INMET/ANA/ANATEL.'
    }
    meta_path = out / f'{scenario.key}_metadata.json'
    meta_path.write_text(__import__('json').dumps(meta, ensure_ascii=False, indent=2), encoding='utf-8')
    files['metadata'] = str(meta_path)
    return files


def all_scenarios(n: int = 50, m: int = 50) -> list[Scenario]:
    return [generate_matopiba_drought(n, m), generate_rural_connectivity(n, m)]
