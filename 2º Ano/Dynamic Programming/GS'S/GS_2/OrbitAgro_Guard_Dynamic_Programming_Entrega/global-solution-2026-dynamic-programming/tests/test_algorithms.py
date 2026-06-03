import numpy as np

from src.brute_force import brute_force_min_path
from src.dynamic_programming import bottom_up_dp, top_down_dp
from src.scenario_generator import effective_cost


def test_brute_force_matches_dp_on_small_grid():
    grid = np.array([
        [1, 3, 1, 2],
        [2, 1, 2, 3],
        [4, 2, 1, 1],
        [3, 1, 2, 1],
    ], dtype=float)
    bf = brute_force_min_path(grid)
    dp = bottom_up_dp(grid)
    assert abs(bf.cost - dp.cost) < 1e-9
    assert len(bf.path) == len(dp.path)


def test_blocked_cells_are_intransponiveis():
    grid = np.array([
        [1, -1, 1],
        [2, 2, 1],
        [9, 1, 1],
    ], dtype=float)
    result = bottom_up_dp(grid)
    assert result.cost == 7
    assert (0, 1) not in result.path


def test_bottom_up_and_top_down_match():
    grid = np.array([
        [2, 4, 1, 3, 1],
        [1, -1, 2, 1, 2],
        [2, 2, 1, -1, 3],
        [3, 1, 2, 2, 1],
        [2, 1, 1, 3, 1],
    ], dtype=float)
    bottom = bottom_up_dp(grid)
    top = top_down_dp(grid)
    assert abs(bottom.cost - top.cost) < 1e-9
    assert bottom.destination == top.destination


def test_effective_cost_keeps_blocked_cells():
    cost = np.ones((3, 3))
    risk = np.full((3, 3), 0.5)
    probability = np.full((3, 3), 0.4)
    blocked = np.zeros((3, 3), dtype=bool)
    blocked[1, 1] = True
    eff = effective_cost(cost, risk, probability, blocked)
    assert eff[1, 1] == -1
    assert eff[0, 0] > 0


def test_any_edge_destination():
    grid = np.array([
        [1, 1, 1],
        [9, 9, 1],
        [9, 9, 9],
    ], dtype=float)
    result = bottom_up_dp(grid, any_edge_destination=True)
    assert result.destination == (0, 2) or result.destination == (1, 2)
    assert result.cost <= bottom_up_dp(grid).cost
