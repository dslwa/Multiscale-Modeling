# MonteCA Grain‑Growth Simulator

A multiscale 2D grain growth simulator that combines Cellular Automata with a Metropolis Monte Carlo algorithm. Designed for materials science research and educational purposes, MonteCA lets you observe and influence the coarsening of microstructures via an intuitive JavaFX interface.

---

## 🔍 Features

* **Dual-Scale Modeling**: Cellular Automaton for deterministic grain expansion and Metropolis Monte Carlo for stochastic boundary smoothing.
* **Flexible Neighborhoods**: Von Neumann, Moore, Pentagonal (random), Hexagonal (random).
* **Seeding Modes**: Random, Uniform grid, Manual placement.
* **Boundary Types**: Periodic (wrap-around) or Absorbing (fixed edge).
* **Interactive UI**: Real-time rendering and statistics panel showing grain count, size distribution, and density.
* **Export**: OVITO-compatible ASCII export (`output_ovito.txt`) for 3D rendering and analysis.

---

## 🛠️ Prerequisites

* Java 11 or higher
* Maven 3.6+

---

## 🚀 Installation

```bash
# Clone repository
git clone https://github.com/your-org/monteca-grain-growth.git
cd monteca-grain-growth

# Build with Maven
mvn clean package
```

---

## ▶️ Running the Application

```bash
java -jar target/monteca-grain-growth-1.0.0.jar
```

Alternatively, run from your IDE by launching `MainApp` in `com.example.multiscaleca.ui`.

---

## ⚙️ Configuration & Controls

| Control               | Description                                                    |
| --------------------- | -------------------------------------------------------------- |
| **# of Grains**       | Number of seeds for Random/Uniform seeding.                    |
| **Seeding Mode**      | Choose Random, Uniform, or Manual placement.                   |
| **Neighborhood**      | Select the neighbor rule: Von Neumann, Moore, Pentagonal, Hex. |
| **Boundary Type**     | Periodic (wrap) or Absorbing (fixed).                          |
| **Add / Remove Mode** | Toggle manual add/remove of grains by clicking canvas.         |
| **Max Density (%)**   | Prevent overgrowth in CA iterations.                           |
| **kt (Temperature)**  | Monte Carlo parameter regulating boundary fluctuations.        |
| **Depth (Z)**         | Depth for OVITO export (number of layers).                     |
| **Iteration Buttons** | `Iteracja` for CA, `MC Iteracja` for one Monte Carlo step.     |
| **Export**            | Save current state to `output_ovito.txt` for OVITO.            |

---

## 📈 Real-Time Statistics

The stats panel displays:

* **Grain Count**: Total distinct grain IDs.
* **Average Size**: Mean cell count per grain.
* **Min / Max Size**: Extremal grain sizes.
* **Density**: Percentage of filled cells.

---

## 🏗️ Architecture Overview

* **Model**: `Cell`, `Grid`, `GrainStats` (size/density analysis).
* **Algorithms**:

  * **Cellular Automaton** (`Grid.growOneIteration`): Zapełnia puste komórki na podstawie najczęstszej etykiety sąsiadów.
  * **Monte Carlo** (`MonteCarloSimulator.fullIteration`): Metropolis–Monte Carlo na granicach ziaren.
* **Neighborhood Strategies**: Implement `NeighborhoodStrategy` for pluggable neighbor rules.
* **Seeding**: `GrainSeeder` interface with Random, Uniform, Manual implementations.
* **UI**: JavaFX `GridCanvas`, `ControlPanel`, `StatsPanel`, and `CanvasClickHandler`.

---
