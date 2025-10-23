# Nome: Diego
# RM: 556724

def dense_city():
    grid = [[0 for _ in range(20)] for _ in range(20)]
    for i in range(7,13):
        for j in range(7,13):
            grid[i][j] = 8
    grid[4][10] = 6
    grid[15][8] = 5
    stations = [
        {'pos': (9,9), 'cost': 120, 'is_renewable': True},
        {'pos': (8,11), 'cost': 100, 'is_renewable': False},
        {'pos': (10,7), 'cost': 110, 'is_renewable': False},
        {'pos': (12,12), 'cost': 140, 'is_renewable': True},
        {'pos': (5,10), 'cost': 80, 'is_renewable': False},
        {'pos': (15,8), 'cost': 90, 'is_renewable': False},
        {'pos': (2,2), 'cost': 60, 'is_renewable': True},
        {'pos': (17,17), 'cost': 70, 'is_renewable': False},
        {'pos': (11,10), 'cost': 95, 'is_renewable': True},
    ]
    budget = 400
    coverage_radius = 3.0
    return grid, stations, budget, coverage_radius

def suburban():
    grid = [[0 for _ in range(20)] for _ in range(20)]
    hotspots = [(3,3),(5,7),(10,15),(16,4),(14,14)]
    for (i,j) in hotspots:
        grid[i][j] = 6
    stations = [
        {'pos': (3,3), 'cost': 50, 'is_renewable': False},
        {'pos': (5,7), 'cost': 60, 'is_renewable': True},
        {'pos': (9,14), 'cost': 55, 'is_renewable': False},
        {'pos': (13,13), 'cost': 80, 'is_renewable': True},
        {'pos': (16,4), 'cost': 70, 'is_renewable': False},
        {'pos': (1,18), 'cost': 40, 'is_renewable': True},
        {'pos': (18,2), 'cost': 45, 'is_renewable': False},
    ]
    budget = 200
    coverage_radius = 4.0
    return grid, stations, budget, coverage_radius
