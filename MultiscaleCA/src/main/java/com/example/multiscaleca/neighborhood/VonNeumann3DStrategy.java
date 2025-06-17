package com.example.multiscaleca.neighborhood;

import com.example.multiscaleca.model.Grid3D;
import com.example.multiscaleca.model.Cell;

import java.util.ArrayList;
import java.util.List;


public class VonNeumann3DStrategy implements Neighborhood3DStrategy {
    private static final int[][] OFFSETS = {
            { 1,  0,  0}, {-1,  0,  0},
            { 0,  1,  0}, { 0, -1,  0},
            { 0,  0,  1}, { 0,  0, -1}
    };

    @Override
    public List<Cell> getNeighbors(Grid3D grid, int x, int y, int z) {
        List<Cell> neighbors = new ArrayList<>();
        for (int[] o : OFFSETS) {
            Cell c = grid.getCell(x + o[0], y + o[1], z + o[2]);
            if (c != null && !c.isEmpty()) {
                neighbors.add(c);
            }
        }
        return neighbors;
    }
}
