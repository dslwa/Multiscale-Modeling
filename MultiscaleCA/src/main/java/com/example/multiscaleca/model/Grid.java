package com.example.multiscaleca.model;

import com.example.multiscaleca.seeding.GrainSeeder;
import com.example.multiscaleca.neighborhood.NeighborhoodStrategy;
import com.example.multiscaleca.model.util.BoundaryType;
import java.util.*;

public class Grid {
    private final Cell[][] grid;
    private final int width;
    private final int height;
    private BoundaryType boundaryType = BoundaryType.ABSORBING;
    private final GrainSeeder seeder;
    private final NeighborhoodStrategy neighborhood;

    public Grid(int width, int height, GrainSeeder seeder, NeighborhoodStrategy neighborhood) {
        this.width = width;
        this.height = height;
        this.seeder = seeder;
        this.neighborhood = neighborhood;
        this.grid = new Cell[height][width];
        initialize();
    }
    private void initialize() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell(x, y);
            }
        }
    }
    public void setBoundaryType(BoundaryType type) {
        this.boundaryType = type;
    }
    public Cell getCell(int x, int y) {
        if (boundaryType == BoundaryType.PERIODIC) {
            int px = (x + width) % width;
            int py = (y + height) % height;
            return grid[py][px];
        }
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[y][x];
        }
        return null;
    }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Cell[][] getGrid() { return grid; }

    public void seed(int count) {
        seeder.seed(this, count);
    }
    public void growOneIteration() {
        Cell[][] newCells = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell current = grid[y][x];
                if (!current.isEmpty()) {
                    newCells[y][x] = new Cell(x, y);
                    newCells[y][x].setGrainId(current.getGrainId());
                    newCells[y][x].setColor(current.getColor());
                    continue;
                }
                List<Cell> neighbors = neighborhood.getNeighbors(this, x, y);
                if (!neighbors.isEmpty()) {
                    Map<Integer, Integer> freq = new HashMap<>();
                    for (Cell n : neighbors) freq.merge(n.getGrainId(), 1, Integer::sum);
                    int mostCommon = Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();
                    Cell sample = neighbors.stream().filter(n->n.getGrainId()==mostCommon).findFirst().orElse(null);
                    newCells[y][x] = new Cell(x, y);
                    newCells[y][x].setGrainId(mostCommon);
                    if (sample != null) newCells[y][x].setColor(sample.getColor());
                } else {
                    newCells[y][x] = new Cell(x, y);
                }
            }
        }
        for (int y = 0; y < height; y++) for (int x = 0; x < width; x++) grid[y][x] = newCells[y][x];
    }
    public int getNextAvailableGrainId() {
        int max = 0;
        for (Cell[] row : grid) for (Cell c : row) max = Math.max(max, c.getGrainId());
        return max + 1;
    }
}