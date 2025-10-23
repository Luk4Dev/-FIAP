# Nome: Lucca
# RM: 560731

import math

def euclidean(a,b):
    return math.hypot(a[0]-b[0], a[1]-b[1])

def coverage_of_station(grid, station_pos, coverage_radius):
    rows = len(grid)
    cols = len(grid[0]) if rows>0 else 0
    covered = []
    for i in range(rows):
        for j in range(cols):
            if grid[i][j] > 0 and euclidean((i,j), station_pos) <= coverage_radius:
                covered.append((i,j))
    return covered

def compute_score(grid, selected_stations, total_cost, weight_cov=3.0, weight_green=2.0):
    covered_cells = set()
    green_count = 0
    for s in selected_stations:
        for (i,j) in s.get('_covered_cells', []):
            covered_cells.add((i,j))
        if s.get('is_renewable'):
            green_count += 1
    coverage_value = sum(grid[i][j] for (i,j) in covered_cells)
    score = weight_cov * coverage_value + weight_green * green_count - (total_cost / 10.0)
    return score, coverage_value, green_count

def greedy_place_stations(grid, stations, budget, coverage_radius, verbose=False):
    for s in stations:
        s['_covered_cells'] = coverage_of_station(grid, s['pos'], coverage_radius)
        s['_coverage_value'] = sum(grid[i][j] for (i,j) in s['_covered_cells'])
        s['_heuristic'] = s['_coverage_value'] + (2 if s['is_renewable'] else 0)
        s['_installed'] = False

    remaining_budget = budget
    selected = []
    total_cost = 0.0

    while True:
        candidates = [s for s in stations if not s['_installed'] and s['cost'] <= remaining_budget]
        if not candidates:
            break
        for s in candidates:
            s['_ratio'] = (s['_heuristic'] / s['cost']) if s['cost']>0 else float('inf')
        candidates.sort(key=lambda x: x['_ratio'], reverse=True)
        choice = candidates[0]
        choice['_installed'] = True
        selected.append(choice)
        remaining_budget -= choice['cost']
        total_cost += choice['cost']
        if verbose:
            print(f"Instalando {choice['pos']} cost={choice['cost']} cov={choice['_coverage_value']} green={choice['is_renewable']} ratio={choice['_ratio']:.2f} rem_budget={remaining_budget}")
    final_score, coverage_value, green_count = compute_score(grid, selected, total_cost)
    report = {
        'selected': [ {'pos': s['pos'], 'cost': s['cost'], 'is_renewable': s['is_renewable'], 'coverage_value': s['_coverage_value']} for s in selected ],
        'total_cost': total_cost,
        'remaining_budget': remaining_budget,
        'score': final_score,
        'coverage_value': coverage_value,
        'green_count': green_count
    }
    return report
