from __future__ import annotations

import time
import tracemalloc
from dataclasses import dataclass
from typing import Callable, Dict, Iterable

import numpy as np

from .brute_force import brute_force_min_path
from .dynamic_programming import bottom_up_dp, top_down_dp
from .monte_carlo import monte_carlo_cost_distribution
from .scenario_generator import effective_cost, generate_matopiba_drought


@dataclass
class PerfRecord:
    algorithm: str
    n: int
    cells: int
    time_ms: float
    memory_mb: float
    operations: int
    cost: float

    def as_dict(self) -> dict:
        return self.__dict__.copy()


def _measure(name: str, n: int, func: Callable[[], tuple[float, int]]) -> PerfRecord:
    tracemalloc.start()
    start = time.perf_counter()
    cost, operations = func()
    elapsed = (time.perf_counter() - start) * 1000
    _, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()
    return PerfRecord(name, n, n * n, elapsed, peak / (1024 * 1024), operations, float(cost))


def run_performance_suite(sizes: Iterable[int] = (3, 5, 10, 20, 50, 100), seed: int = 99) -> list[PerfRecord]:
    records: list[PerfRecord] = []
    for n in sizes:
        scenario = generate_matopiba_drought(n, n, seed + n)
        eff = effective_cost(scenario.cost, scenario.risk, scenario.probability, scenario.blocked)

        if n <= 5:
            records.append(_measure('Forca Bruta', n, lambda eff=eff: (
                (lambda r: (r.cost, r.calls))(brute_force_min_path(eff))
            )))
        else:
            # Brute force is intentionally not executed at high N; record a modeled estimate.
            from math import comb
            estimated_paths = comb(2 * n - 2, n - 1)
            records.append(PerfRecord('Forca Bruta (estimada)', n, n * n, float('nan'), 0.0, int(min(estimated_paths, 10**12)), float('nan')))

        records.append(_measure('DP Bottom-Up', n, lambda eff=eff: (
            (lambda r: (r.cost, r.iterations))(bottom_up_dp(eff))
        )))
        records.append(_measure('DP Top-Down', n, lambda eff=eff: (
            (lambda r: (r.cost, r.iterations))(top_down_dp(eff))
        )))

        # Monte Carlo performance uses fewer repetitions for large N in benchmark graph to keep runtime practical.
        k = 1000 if n <= 20 else 200
        records.append(_measure('Monte Carlo + DP', n, lambda scenario=scenario, k=k: (
            (lambda r: (r.mean, r.scenarios * n * n))(monte_carlo_cost_distribution(
                scenario.cost, scenario.risk, scenario.probability, scenario.blocked, k=k, seed=seed + n))
        )))
    return records
