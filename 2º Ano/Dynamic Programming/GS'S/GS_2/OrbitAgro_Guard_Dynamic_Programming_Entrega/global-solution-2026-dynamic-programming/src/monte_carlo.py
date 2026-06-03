from __future__ import annotations

from dataclasses import dataclass
from math import inf
from typing import Dict

import numpy as np

from .scenario_generator import effective_cost


@dataclass
class MonteCarloResult:
    costs: np.ndarray
    mean: float
    median: float
    std: float
    ci95_low: float
    ci95_high: float
    scenarios: int

    def as_dict(self) -> Dict[str, float]:
        return {
            'mean': self.mean,
            'median': self.median,
            'std': self.std,
            'ci95_low': self.ci95_low,
            'ci95_high': self.ci95_high,
            'scenarios': self.scenarios,
        }


def beta_parameters(p: np.ndarray, concentration: float = 28.0) -> tuple[np.ndarray, np.ndarray]:
    p = np.clip(np.asarray(p, dtype=float), 0.02, 0.98)
    alpha = p * concentration
    beta_param = (1.0 - p) * concentration
    return alpha, beta_param


def _vectorized_dp_costs(eff: np.ndarray, any_edge_destination: bool = True) -> np.ndarray:
    """Compute DP costs for K scenarios at once.

    eff shape: (K, N, M). Blocked cells are represented by -1.
    This preserves the same recurrence as bottom_up_dp, but vectorizes across K.
    """
    k, n, m = eff.shape
    dp = np.full((k, n, m), inf, dtype=float)
    blocked = eff == -1
    for i in range(n):
        for j in range(m):
            valid = ~blocked[:, i, j]
            if i == 0 and j == 0:
                dp[valid, i, j] = eff[valid, i, j]
                continue
            best_prev = np.full(k, inf, dtype=float)
            if i > 0:
                best_prev = np.minimum(best_prev, dp[:, i - 1, j])
            if j > 0:
                best_prev = np.minimum(best_prev, dp[:, i, j - 1])
            reachable = valid & np.isfinite(best_prev)
            dp[reachable, i, j] = eff[reachable, i, j] + best_prev[reachable]
    if any_edge_destination:
        edge = np.concatenate([dp[:, -1, :], dp[:, :, -1]], axis=1)
        return np.nanmin(edge, axis=1)
    return dp[:, -1, -1]


def monte_carlo_cost_distribution(cost: np.ndarray, risk: np.ndarray, probability: np.ndarray,
                                  blocked: np.ndarray, k: int = 10_000,
                                  seed: int = 2026,
                                  probability_multiplier: float = 1.0,
                                  any_edge_destination: bool = True) -> MonteCarloResult:
    rng = np.random.default_rng(seed)
    adjusted_p = np.clip(probability * probability_multiplier, 0.02, 0.98)
    alpha, beta_param = beta_parameters(adjusted_p)

    sampled_p = rng.beta(alpha, beta_param, size=(k,) + adjusted_p.shape)
    base_cost = cost.astype(float)[None, :, :]
    risk3 = risk.astype(float)[None, :, :]
    blocked3 = blocked.astype(bool)[None, :, :]
    eff = base_cost * (1 + 0.45 * risk3 + 0.35 * sampled_p) * (1 - 0.25 * risk3 * sampled_p)
    eff = eff.astype(float)
    eff[np.broadcast_to(blocked3, eff.shape)] = -1.0

    costs = _vectorized_dp_costs(eff, any_edge_destination=any_edge_destination)
    finite = costs[np.isfinite(costs)]
    if finite.size == 0:
        raise RuntimeError('All Monte Carlo scenarios became infeasible.')
    mean = float(np.mean(finite))
    median = float(np.median(finite))
    std = float(np.std(finite, ddof=1))
    ci = 1.96 * std / np.sqrt(finite.size)
    return MonteCarloResult(
        costs=finite,
        mean=mean,
        median=median,
        std=std,
        ci95_low=float(mean - ci),
        ci95_high=float(mean + ci),
        scenarios=int(finite.size),
    )


def sensitivity_analysis(cost: np.ndarray, risk: np.ndarray, probability: np.ndarray, blocked: np.ndarray,
                         k: int = 10_000, seed: int = 2026) -> dict[str, MonteCarloResult]:
    return {
        '-20%': monte_carlo_cost_distribution(cost, risk, probability, blocked, k=k, seed=seed, probability_multiplier=0.8),
        'baseline': monte_carlo_cost_distribution(cost, risk, probability, blocked, k=k, seed=seed + 1, probability_multiplier=1.0),
        '+20%': monte_carlo_cost_distribution(cost, risk, probability, blocked, k=k, seed=seed + 2, probability_multiplier=1.2),
    }
