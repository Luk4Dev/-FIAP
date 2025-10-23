# Nome: Joud
# RM: 556482

from data import dense_city, suburban
from greedy import greedy_place_stations
import pprint

def run_scenario(name, scenario_func):
    grid, stations, budget, radius = scenario_func()
    print(f"\n=== Cenário: {name} ===")
    print(f"Budget: {budget}, Coverage radius: {radius}")
    report = greedy_place_stations(grid, stations, budget, radius, verbose=True)
    print("\nRelatório final:")
    pprint.pprint(report, width=120)

def main():
    run_scenario('Dense City (núcleo urbano denso)', dense_city)
    run_scenario('Suburban (área suburbana)', suburban)

if __name__ == '__main__':
    main()
