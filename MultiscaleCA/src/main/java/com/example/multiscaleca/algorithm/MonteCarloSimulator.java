package com.example.multiscaleca.algorithm;

import com.example.multiscaleca.model.Cell;
import com.example.multiscaleca.model.Grid;

import java.util.*;

public class MonteCarloSimulator {
    private final Random rnd = new Random();
    private double kt = 0.1;

    public void setKt(double kt) {
        this.kt = kt;
    }

    public void fullIteration(Grid grid) {
        int width = grid.getWidth();
        int height = grid.getHeight();
        List<int[]> borderCells = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isOnBoundary(grid, x, y)) {
                    borderCells.add(new int[]{x, y});
                }
            }
        }

        Collections.shuffle(borderCells);

        for (int[] coord : borderCells) {
            int x = coord[0];
            int y = coord[1];
            Cell cell = grid.getCell(x, y);
            if (cell == null || cell.isEmpty()) continue;

            List<Cell> neighbors = getNonEmptyMooreNeighbors(grid, x, y);
            if (neighbors.isEmpty()) continue;

            Cell randomNeighbor = neighbors.get(rnd.nextInt(neighbors.size()));
            int oldId = cell.getGrainId();
            int newId = randomNeighbor.getGrainId();
            if (newId == oldId) continue;

            int energyBefore = computeEnergy(grid, x, y, oldId);
            int energyAfter = computeEnergy(grid, x, y, newId);
            int deltaE = energyAfter - energyBefore;

            if (deltaE <= 0 || Math.exp(-((double) deltaE) / kt) > rnd.nextDouble()) {
                cell.setGrainId(newId);
                cell.setColor(randomNeighbor.getColor());
            }
        }
    }

    private boolean isOnBoundary(Grid grid, int x, int y) {
        Cell center = grid.getCell(x, y);
        if (center == null || center.isEmpty()) return false;

        int[][] offsets = {
                {-1,-1},{0,-1},{1,-1},
                {-1, 0},       {1, 0},
                {-1, 1},{0, 1},{1, 1}
        };

        for (int[] o : offsets) {
            Cell neighbor = grid.getCell(x + o[0], y + o[1]);
            if (neighbor != null && neighbor.getGrainId() != center.getGrainId()) {
                return true;
            }
        }
        return false;
    }

    private int computeEnergy(Grid grid, int x, int y, int id) {
        int energy = 0;
        int[][] offsets = {
                {-1,-1},{0,-1},{1,-1},
                {-1, 0},       {1, 0},
                {-1, 1},{0, 1},{1, 1}
        };
        for (int[] o : offsets) {
            Cell n = grid.getCell(x + o[0], y + o[1]);
            if (n != null && n.getGrainId() != id) {
                energy++;
            }
        }
        return energy;
    }

    private List<Cell> getNonEmptyMooreNeighbors(Grid grid, int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] offsets = {
                {-1,-1},{0,-1},{1,-1},
                {-1, 0},       {1, 0},
                {-1, 1},{0, 1},{1, 1}
        };
        for (int[] o : offsets) {
            Cell n = grid.getCell(x + o[0], y + o[1]);
            if (n != null && !n.isEmpty()) {
                neighbors.add(n);
            }
        }
        return neighbors;
    }
}