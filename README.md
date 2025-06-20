# MultiScaleCA

MultiScaleCA is a Java-based application for simulating grain growth using Cellular Automata (CA) principles in both 2D and 3D. It supports various neighborhood strategies, seeding methods, and Monte Carlo simulations, with a JavaFX-based user interface for interactive exploration and visualization.

## Features

* **2D and 3D Grain Growth**: Grow grains in a grid or volume with customizable boundary conditions.
* **Neighborhood Strategies**: Von Neumann, Moore, Pentagonal, Hexagonal (random/flat), and custom 3D strategies.
* **Seeding Methods**: Random, Uniform, and Manual seeding for both 2D and 3D.
* **Monte Carlo Simulation**: Energy-based grain boundary migration with temperature parameter (kT).
* **Interactive UI**: JavaFX controls for seeding, growth iterations, Monte Carlo steps, and real-time statistics.
* **Export**: Export 3D voxel data to XYZ format for OVITO visualization.

## Requirements

* Java 11 or higher
* JavaFX SDK compatible with your Java version
* Maven (for building)

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/multiscaleca.git
   cd multiscaleca
   ```

2. **Build with Maven**:

   ```bash
   mvn clean package
   ```

3. **Run the application**:

   ```bash
   mvn javafx:run -DmainClass=com.example.multiscaleca.ui.MainApp
   ```

## Usage

### 2D Simulation

1. Launch the 2D app:

   ```bash
   java -cp target/multiscaleca.jar com.example.multiscaleca.ui.MainApp
   ```
2. Use the control panel to select:

   * Seeding mode (Random, Uniform, Manual)
   * Neighborhood (Von Neumann, Moore, Pentagonal, Hexagonal)
   * Boundary (Periodic, Absorbing)
   * Number of seeds and kT for Monte Carlo
3. Click **Start** to seed and render initial state.
4. Use **Iteracja** to grow grains by CA rules.
5. Use **MC Iteracja** to perform a Monte Carlo step.
6. View real-time statistics in the stats panel.

![obraz](https://github.com/user-attachments/assets/b57c2063-f2ed-478e-aae8-974194c306c5)

### 3D Simulation

1. Launch the 3D app:

   ```bash
   java -cp target/multiscaleca.jar com.example.multiscaleca.ui.MainApp3D
   ```
2. Configure seeding, neighborhood, boundary, and seeds in the control panel.
3. Click **Start** to initialize the volume.
4. Use **Iteracja**, **MC Iteracja**, and **Export** for growth, Monte Carlo, and XYZ export.
5. Use the **Pokaż 3D** button to open an interactive 3D viewer.


![obraz](https://github.com/user-attachments/assets/754761a4-54da-4778-8b93-931f473dce13)


## Project Structure

```text
src/main/java
├── com.example.multiscaleca.algorithm
│   ├── MonteCarloSimulator.java
│   └── MonteCarloSimulator3D.java
├── com.example.multiscaleca.model
│   ├── Cell.java
│   ├── Grid.java
│   ├── Grid3D.java
│   ├── GrainStats.java
│   └── GrainStats3D.java
├── com.example.multiscaleca.neighborhood
│   ├── NeighborhoodStrategy.java
│   ├── VonNeumannStrategy.java
│   ├── MooreStrategy.java
│   ├── PentagonalStrategy.java
│   ├── RandomHexagonalStrategy.java
│   └── (3D counterparts)
├── com.example.multiscaleca.seeding
│   ├── GrainSeeder.java
│   ├── RandomSeeder.java
│   ├── UniformSeeder.java
│   ├── ManualSeeder.java
│   └── (3D counterparts)
└── com.example.multiscaleca.ui
    ├── MainApp.java
    ├── MainApp3D.java
    ├── GridCanvas.java
    ├── GridCanvas3D.java
    ├── ControlPanel.java
    ├── StatsPanel.java
    ├── StatsPanel3D.java
    ├── Exporter3D.java
    └── event handlers
```

## Extending

* **Custom Neighborhood**: Implement `NeighborhoodStrategy` or `Neighborhood3DStrategy` and register in the UI.
* **New Seeding**: Implement `GrainSeeder` or `GrainSeeder3D` for new initialization algorithms.
* **Analysis**: Modify `GrainStats` for additional metrics.

