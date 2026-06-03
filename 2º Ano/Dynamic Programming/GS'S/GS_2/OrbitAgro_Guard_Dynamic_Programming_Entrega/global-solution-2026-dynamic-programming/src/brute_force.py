from __future__ import annotations

from dataclasses import dataclass
from math import inf
from typing import List, Tuple

import numpy as np

Cell = Tuple[int, int]


@dataclass
class BruteForceResult:
    cost: float
    path: List[Cell]
    calls: int
    paths_evaluated: int


def brute_force_min_path(cost: np.ndarray, destination: Cell | None = None) -> BruteForceResult:
    """Enumerate all valid right/down paths for small grids.

    Cells with value -1 are treated as blocked. This function is intentionally
    exponential and should only be used as a validation oracle for N,M <= 5.
    """
    grid = np.asarray(cost, dtype=float)
    n, m = grid.shape
    if destination is None:
        destination = (n - 1, m - 1)
    if n > 5 or m > 5:
        raise ValueError('Brute force is restricted to N,M <= 5 by design.')
    if grid[0, 0] == -1 or grid[destination] == -1:
        return BruteForceResult(inf, [], 0, 0)

    best_cost = inf
    best_path: List[Cell] = []
    calls = 0
    paths = 0

    def dfs(i: int, j: int, acc: float, path: List[Cell]) -> None:
        nonlocal best_cost, best_path, calls, paths
        calls += 1
        if i >= n or j >= m or grid[i, j] == -1:
            return
        new_cost = acc + float(grid[i, j])
        if new_cost >= best_cost:
            # branch-and-bound keeps the oracle exact, only pruning paths already worse
            return
        new_path = path + [(i, j)]
        if (i, j) == destination:
            paths += 1
            if new_cost < best_cost:
                best_cost = new_cost
                best_path = new_path
            return
        dfs(i + 1, j, new_cost, new_path)
        dfs(i, j + 1, new_cost, new_path)

    dfs(0, 0, 0.0, [])
    return BruteForceResult(best_cost, best_path, calls, paths)


def count_unblocked_paths(n: int, m: int) -> int:
    """Number of right/down paths in an obstacle-free n x m grid."""
    from math import comb
    return comb(n + m - 2, n - 1)
