# Grain Growth Cellular Automaton

A JavaFX application that simulates grain growth using Cellular Automaton (CA) techniques. Users can seed grains randomly, uniformly, or manually, choose neighborhood types (Von Neumann or random pentagonal), and switch between periodic and absorbing boundary conditions.

---

## Features

- **Seeding Modes**
  - **Random**: Seeds a specified number of grains at random positions.
  - **Uniform**: Distributes grains evenly in a grid pattern.
  - **Manual**: Allows clicking on the canvas to place grains one by one.

- **Neighborhood Types**
  - **Von Neumann**: 4 orthogonal neighbors.
  - **Random Pentagonal**: Randomly selects one of five pentagonal configurations.

- **Boundary Conditions**
  - **Periodic**: Edges wrap around (toroidal grid).
  - **Absorbing**: Edges are fixed; cells outside the grid are ignored.

- **Interactive Controls** via JavaFX UI components.
- **Dark-themed CSS** for a modern look.
