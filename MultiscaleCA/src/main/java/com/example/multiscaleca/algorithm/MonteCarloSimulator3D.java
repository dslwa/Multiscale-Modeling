package com.example.multiscaleca.algorithm;

import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.neighborhood.Neighborhood3DStrategy;
import com.example.multiscaleca.neighborhood.Neighborhood3DStrategy;

import java.util.*;

public class MonteCarloSimulator3D {
    private final Random rnd = new Random();
    private double kt = 0.1;
    private Neighborhood3DStrategy strategy;

    public void setNeighborhoodStrategy(Neighborhood3DStrategy strategy) {
        this.strategy = strategy;
    }

    public void setKt(double kt) {
        this.kt = kt;
    }

    public void fullIteration(Grid3D grid) {
        if (strategy == null) {
            throw new IllegalStateException("NeighborhoodStrategy3D not set");
        }
        List<int[]> borderCells = new ArrayList<>();
        int D = grid.getDepth();
        int H = grid.getHeight();
        int W = grid.getWidth();
        // garniczace zairna
        for (int z = 0; z < D; z++) {
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    Cell c = grid.getCell(x,y,z);
                    if (c == null || c.isEmpty()) continue;
                    List<Cell> neigh = strategy.getNeighbors(grid, x, y, z);
                    for (Cell n : neigh) {
                        if (n.getGrainId() != c.getGrainId()) {
                            borderCells.add(new int[]{x,y,z});
                            break;
                        }
                    }
                }
            }
        }
        Collections.shuffle(borderCells);
        // zmienieanie na granicach
        for (int[] coord : borderCells) {
            int x = coord[0], y = coord[1], z = coord[2];
            Cell cell = grid.getCell(x,y,z);
            List<Cell> neigh = strategy.getNeighbors(grid, x, y, z);
            if (neigh.isEmpty()) continue;
            Cell randomNeighbor = neigh.get(rnd.nextInt(neigh.size()));
            int oldId = cell.getGrainId();
            int newId = randomNeighbor.getGrainId();
            if (newId == oldId) continue;
            int eBefore = computeEnergy(grid, x, y, z, oldId);
            int eAfter  = computeEnergy(grid, x, y, z, newId);
            int deltaE  = eAfter - eBefore;
            if (deltaE <= 0 || Math.exp(-deltaE / kt) > rnd.nextDouble()) {
                cell.setGrainId(newId);
                cell.setColor(randomNeighbor.getColor());
            }
        }
    }

    private int computeEnergy(Grid3D grid, int x, int y, int z, int id) {
        int energy = 0;
        for (Cell n : strategy.getNeighbors(grid, x, y, z)) {
            if (n.getGrainId() != id) energy++;
        }
        return energy;
    }
}
