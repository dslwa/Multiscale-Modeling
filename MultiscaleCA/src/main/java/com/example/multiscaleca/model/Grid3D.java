// --- Grid3D.java (update) ---
package com.example.multiscaleca.model;

import com.example.multiscaleca.model.util.BoundaryType;
import com.example.multiscaleca.neighborhood.Neighborhood3DStrategy;
import javafx.scene.paint.Color;

import java.util.*;

public class Grid3D {
    private final Cell[][][] grid;
    private final int width, height, depth;
    private BoundaryType boundaryType = BoundaryType.ABSORBING;

    public Grid3D(int width, int height, int depth) {
        this.width  = width;
        this.height = height;
        this.depth  = depth;
        this.grid   = new Cell[depth][height][width];
        initialize();
    }

    private void initialize() {
        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    grid[z][y][x] = new Cell(x, y);
                }
            }
        }
    }

    public void setBoundaryType(BoundaryType type) {
        this.boundaryType = type;
    }

    public Cell getCell(int x, int y, int z) {
        if (boundaryType == BoundaryType.PERIODIC) {
            int px = (x % width  + width)  % width;
            int py = (y % height + height) % height;
            int pz = (z % depth  + depth)  % depth;
            return grid[pz][py][px];
        }
        if (x < 0 || x >= width ||
                y < 0 || y >= height ||
                z < 0 || z >= depth) {
            return null;
        }
        return grid[z][y][x];
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public int getDepth()  { return depth; }

    public int getNextAvailableGrainId() {
        int max = 0;
        for (Cell[][] slice : grid)
            for (Cell[] row : slice)
                for (Cell c : row)
                    max = Math.max(max, c.getGrainId());
        return max + 1;
    }

   public Cell[][][] getGrid() {
        return grid;
    }

    public void growOneIteration(Neighborhood3DStrategy strat) {
        Cell[][][] newGrid = new Cell[depth][height][width];
        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Cell old = getCell(x, y, z);
                    newGrid[z][y][x] = new Cell(x, y);
                    if (!old.isEmpty()) {
                        // zachowaj istniejące ziarno
                        newGrid[z][y][x].setGrainId(old.getGrainId());
                        newGrid[z][y][x].setColor(old.getColor());
                    } else {
                        // pusta komórka — zbierz sąsiadów i wybierz najliczniejszy ID
                        List<Cell> nei = strat.getNeighbors(this, x, y, z);
                        if (!nei.isEmpty()) {
                            Map<Integer,Integer> freq = new HashMap<>();
                            for (Cell c : nei) {
                                freq.merge(c.getGrainId(), 1, Integer::sum);
                            }
                            int bestId = Collections.max(
                                    freq.entrySet(),
                                    Map.Entry.comparingByValue()
                            ).getKey();
                            newGrid[z][y][x].setGrainId(bestId);
                            // nadaj kolor od jednego ze zwycięskich sąsiadów
                            for (Cell c : nei) {
                                if (c.getGrainId() == bestId) {
                                    newGrid[z][y][x].setColor(c.getColor());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // skopiuj nową siatkę
        for (int z = 0; z < depth; z++)
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                    grid[z][y][x] = newGrid[z][y][x];
    }
}
