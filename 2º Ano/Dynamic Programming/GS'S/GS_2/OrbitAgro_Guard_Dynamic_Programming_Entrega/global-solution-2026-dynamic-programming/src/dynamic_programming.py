from __future__ import annotations

from dataclasses import dataclass
from functools import lru_cache
from math import inf
from typing import List, Tuple

import numpy as np

Cell = Tuple[int, int]


@dataclass
class DPResult:
    cost: float
    path: List[Cell]
    dp_table: np.ndarray
    iterations: int
    destination: Cell


def _candidate_destinations(n: int, m: int, destination: Cell | None, any_edge_destination: bool) -> list[Cell]:
    if destination is not None:
        return [destination]
    if any_edge_destination:
        # Any cell in last row or last column, as requested by the expanded formulation.
        cells = [(n - 1, j) for j in range(m)] + [(i, m - 1) for i in range(n)]
        return sorted(set(cells))
    return [(n - 1, m - 1)]


def _reconstruct_path(dp: np.ndarray, cost: np.ndarray, dest: Cell) -> List[Cell]:
    if not np.isfinite(dp[dest]):
        return []
    i, j = dest
    path: List[Cell] = [(i, j)]
    while (i, j) != (0, 0):
        candidates: list[tuple[float, Cell]] = []
        if i > 0:
            candidates.append((dp[i - 1, j], (i - 1, j)))
        if j > 0:
            candidates.append((dp[i, j - 1], (i, j - 1)))
        candidates = [(v, c) for v, c in candidates if np.isfinite(v)]
        if not candidates:
            return []
        _, (i, j) = min(candidates, key=lambda item: item[0])
        path.append((i, j))
    path.reverse()
    return path


def bottom_up_dp(cost: np.ndarray, destination: Cell | None = None,
                 any_edge_destination: bool = False) -> DPResult:
    """2D DP tabulation with blocked cells (-1)."""
    grid = np.asarray(cost, dtype=float)
    n, m = grid.shape
    dp = np.full((n, m), inf, dtype=float)
    iterations = 0

    for i in range(n):
        for j in range(m):
            iterations += 1
            if grid[i, j] == -1:
                continue
            if i == 0 and j == 0:
                dp[i, j] = grid[i, j]
            else:
                best_prev = inf
                if i > 0:
                    best_prev = min(best_prev, dp[i - 1, j])
                if j > 0:
                    best_prev = min(best_prev, dp[i, j - 1])
                if np.isfinite(best_prev):
                    dp[i, j] = grid[i, j] + best_prev

    candidates = _candidate_destinations(n, m, destination, any_edge_destination)
    dest = min(candidates, key=lambda c: dp[c])
    path = _reconstruct_path(dp, grid, dest)
    return DPResult(float(dp[dest]), path, dp, iterations, dest)


def top_down_dp(cost: np.ndarray, destination: Cell | None = None,
                any_edge_destination: bool = False) -> DPResult:
    """2D DP memoization with blocked cells (-1)."""
    grid = np.asarray(cost, dtype=float)
    n, m = grid.shape
    calls = 0

    @lru_cache(maxsize=None)
    def solve(i: int, j: int) -> float:
        nonlocal calls
        calls += 1
        if i < 0 or j < 0 or grid[i, j] == -1:
            return inf
        if i == 0 and j == 0:
            return float(grid[i, j])
        return float(grid[i, j]) + min(solve(i - 1, j), solve(i, j - 1))

    candidates = _candidate_destinations(n, m, destination, any_edge_destination)
    values = {c: solve(*c) for c in candidates}
    dest = min(candidates, key=lambda c: values[c])

    dp = np.full((n, m), inf, dtype=float)
    for i in range(n):
        for j in range(m):
            if grid[i, j] != -1:
                dp[i, j] = solve(i, j)

    path = _reconstruct_path(dp, grid, dest)
    return DPResult(float(dp[dest]), path, dp, calls, dest)
